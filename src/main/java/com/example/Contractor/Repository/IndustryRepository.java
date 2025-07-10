package com.example.Contractor.Repository;

import com.example.Contractor.DTO.Industry;
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
public class IndustryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Creates instance with initialized {@code JdbcTemplate}.
     *
     * @param jdbcTemplate
     */
    @Autowired
    public IndustryRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves all active {@code Industry} entities from database.
     *
     * @return list of active (value of {@code is_active} field = true) {@code Industry} instances
     */
    public List<Industry> get() {
        List<Industry> list = jdbcTemplate.query("""
                SELECT *
                FROM industry
                WHERE is_active = true;
                """, (rs, rowNum) -> new Industry(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBoolean("is_active")));
        return list;
    }

    /**
     * Implements SQL INSERT (by primary key) query.
     * <p>
     * Searches {@link Industry} suitable instance in 'Industry' table.
     *
     * @param id value of {@code id} field of searched {@code Industry} entity
     * @return {@code Industry} instance - if successful, Optional.empty() - else
     */
    public Optional<Industry> get(int id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("""
                    SELECT *
                    FROM industry
                    WHERE id = :id
                    """, Collections.singletonMap("id", id), (rs, rowNum) -> new Industry(
                    rs.getInt("id"),
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
     * @param industry instance that must be added or updated
     * @return added/updated instance - if successful, Optional.empty() - else
     */
    public Optional<Industry> save(Industry industry) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(industry);
        jdbcTemplate.update("""
                INSERT INTO industry (id, name)
                VALUES (:id, :name)
                ON CONFLICT (id)
                DO UPDATE SET id = :id, name = :name;
                """, params);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("""
                    SELECT *
                    FROM industry
                    WHERE id = :id;
                    """, Collections.singletonMap("id", industry.getId()), (rs, rowNum) -> new Industry(
                    rs.getInt("id"),
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
     * @param id value of {@code id} field of {@link Industry} instance
     * @return updated rows amount
     */
    public int delete(int id) {
        return jdbcTemplate.update("""
                UPDATE industry
                SET is_active = false
                WHERE id = :id
                """, Collections.singletonMap("id", id));
    }

}
