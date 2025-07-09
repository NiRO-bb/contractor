package com.example.Contractor.Repository;

import com.example.Contractor.DTO.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Provides access to database.
 * <p>
 * Contains CRUD-methods for data process.
 */
@Repository
public class CountryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Creates instance with initialized {@code JdbcTemplate}.
     *
     * @param jdbcTemplate
     */
    @Autowired
    public CountryRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves all active {@code Country} entities from database.
     *
     * @return list of active (value of {@code is_active} field = true) {@code Country} instances
     */
    public List<Country> get() {
        List<Country> list = jdbcTemplate.query("""
                SELECT *
                FROM country
                WHERE is_active = true;
                """, (rs, rowNum) -> new Country(
                rs.getString("id"),
                rs.getString("name"),
                rs.getBoolean("is_active")));
        return list;
    }

    /**
     * Implements SQL INSERT (by primary key) query.
     * <p>
     * Searches {@link Country} suitable instance in 'country' table.
     *
     * @param id value of {@code id} field of searched {@code Country} entity
     * @return {@code Country} instance - if successful, Optional.empty() - else
     */
    public Optional<Country> get(String id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("""
                    SELECT *
                    FROM country
                    WHERE id = :id
                    """, Collections.singletonMap("id", id), (rs, rowNum) -> new Country(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getBoolean("is_active"))));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    /**
     * Implements SQL INSERT query.
     * <p>
     * At first tries to insert new row into database.
     * If failed, updates the existing row.
     *
     * @param country instance that must be added or updated
     * @return added/updated instance - if successful, Optional.empty() - else
     */
    public Optional<Country> save(Country country) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(country);
        jdbcTemplate.update("""
                INSERT INTO country (id, name)
                VALUES (:id, :name)
                ON CONFLICT (id)
                DO UPDATE SET id = :id, name = :name;
                """, params);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("""
                    SELECT *
                    FROM country
                    WHERE id = :id;
                    """, Collections.singletonMap("id", country.getId()), (rs, rowNum) -> new Country(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getBoolean("is_active")
            )));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    /**
     * Implements logical removing from database.
     * <p>
     * Update {@code is_active} field to 'false' value instead of executing the DELETE query.
     *
     * @param id value of {@code id} field of {@link Country} instance
     * @return updated rows amount
     */
    public int delete(String id) {
        return jdbcTemplate.update("""
                UPDATE country
                SET is_active = false
                WHERE id = :id
                """, Collections.singletonMap("id", id));
    }

}
