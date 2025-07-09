 package com.example.Contractor.Repository;

import com.example.Contractor.DTO.OrgForm;
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
public class OrgFormRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Creates instance with initialized {@code JdbcTemplate}.
     *
     * @param jdbcTemplate
     */
    @Autowired
    public OrgFormRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves all active {@code OrgForm} entities from database.
     *
     * @return list of active (value of {@code is_active} field = true) {@code OrgForm} instances
     */
    public List<OrgForm> get() {
        List<OrgForm> list = jdbcTemplate.query("""
                SELECT *
                FROM org_form
                WHERE is_active = true;
                """, (rs, rowNum) -> new OrgForm(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBoolean("is_active")));
        return list;
    }

    /**
     * Implements SQL INSERT (by primary key) query.
     * <p>
     * Searches {@link OrgForm} suitable instance in 'OrgForm' table.
     *
     * @param id value of {@code id} field of searched {@code OrgForm} entity
     * @return {@code OrgForm} instance - if successful, Optional.empty() - else
     */
    public Optional<OrgForm> get(int id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("""
                    SELECT *
                    FROM org_form
                    WHERE id = :id
                    """, Collections.singletonMap("id", id), (rs, rowNum) -> new OrgForm(
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
     * @param orgForm instance that must be added or updated
     * @return added/updated instance - if successful, Optional.empty() - else
     */
    public Optional<OrgForm> save(OrgForm orgForm) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(orgForm);
        jdbcTemplate.update("""
                INSERT INTO org_form (id, name)
                VALUES (:id, :name)
                ON CONFLICT (id)
                DO UPDATE SET id = :id, name = :name;
                """, params);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("""
                    SELECT *
                    FROM org_form
                    WHERE id = :id;
                    """, Collections.singletonMap("id", orgForm.getId()), (rs, rowNum) -> new OrgForm(
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
     * @param id value of {@code id} field of {@link OrgForm} instance
     * @return updated rows amount
     */
    public int delete(int id) {
        return jdbcTemplate.update("""
                UPDATE org_form
                SET is_active = false
                WHERE id = :id
                """, Collections.singletonMap("id", id));
    }

}
