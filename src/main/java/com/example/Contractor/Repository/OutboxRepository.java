package com.example.Contractor.Repository;

import com.example.Contractor.DTO.OutboxMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

/**
 * Provides access to database.
 * Contains CRUD-methods for data process.
 */
@Repository
public class OutboxRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Saves passed OutboxMessage instance to 'outbox' database table.
     *
     * @param message message must be saved
     * @return OutboxMessage if successful, otherwise - Optional.empty()
     */
    public Optional<OutboxMessage> save(OutboxMessage message) {
        String sql = """
                INSERT INTO outbox
                SELECT :id, :payload
                ON CONFLICT (id)
                DO UPDATE SET id = :id, payload = :payload;
                """;
        SqlParameterSource params = new BeanPropertySqlParameterSource(message);
        jdbcTemplate.update(sql, params);
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("""
                            SELECT *
                            FROM outbox
                            WHERE id = :id
                            """,
                            Collections.singletonMap("id", message.getId()),
                            (rs, rowNum) -> new OutboxMessage(
                                    rs.getString("id"),
                                    rs.getString("payload")
                            )));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves first OutboxMessage entity from 'outbox' table.
     *
     * @return OutboxMessage if successful, otherwise - Optional.empty()
     */
    public Optional<OutboxMessage> getFirst() {
        String sql = """
                SELECT *
                FROM outbox
                LIMIT 1
                """;
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            sql, new HashMap<>(),
                            (rs, num) -> new OutboxMessage(
                                    rs.getString("id"),
                                    rs.getString("payload")
                            )));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    /**
     * Deletes OutboxMessage entity by passed id value.
     *
     * @param id value of id field of OutboxMessage must be deleted
     * @return deleted entities amount
     */
    public int deleteById(String id) {
        String sql = """
                DELETE
                FROM outbox
                WHERE id = :id;
                """;
        return jdbcTemplate.update(sql, Collections.singletonMap("id", id));
    }

}
