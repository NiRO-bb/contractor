package com.example.Contractor.Repository;

import com.example.Contractor.DTO.Contractor;
import com.example.Contractor.DTO.ContractorSearch;
import com.example.Contractor.DTO.Country;
import com.example.Contractor.DTO.Industry;
import com.example.Contractor.DTO.OrgForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Provides access to database.
 * <p>
 * Contains CRUD-methods for data process.
 */
@Repository
public class ContractorRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Creates instance with initialized {@code NamedParameterJdbcTemplate}.
     *
     * @param jdbcTemplate
     */
    @Autowired
    public ContractorRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Implements SQL INSERT and UPDATE queries.
     * <p>
     * At first tries to insert new entity into database.
     * If failed, updates the existing entity.
     *
     * @param contractor instance that must be added or updated
     * @return added/updated instance - if successful; {@code Optional.empty()} - else
     */
    public Optional<Contractor> save(Contractor contractor) {
        String sql = """
                INSERT INTO contractor (id, parent_id, name, name_full, inn, ogrn, country, industry, org_form)
                VALUES (:id, :parentId, :name, :nameFull, :inn, :ogrn, :country, :industry, :orgForm)
                ON CONFLICT (id)
                DO UPDATE SET id = :id, parent_id = :parentId, name = :name, name_full = :nameFull, inn = :inn,
                ogrn = :ogrn, country = :country, industry = :industry, org_form = :orgForm;
                """;
        SqlParameterSource params = new BeanPropertySqlParameterSource(contractor);
        jdbcTemplate.update(sql, params);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("""
                SELECT *
                FROM contractor
                WHERE id = :id;
                """, Collections.singletonMap("id", contractor.getId()),
                    (rs, rowNum) -> new Contractor(
                            rs.getString("id"),
                            rs.getString("parent_id"),
                            rs.getString("name"),
                            rs.getString("name_full"),
                            rs.getString("inn"),
                            rs.getString("ogrn"),
                            rs.getString("country"),
                            rs.getInt("industry"),
                            rs.getInt("org_form"),
                            rs.getDate("create_date"),
                            rs.getDate("modify_date"),
                            rs.getString("create_user_id"),
                            rs.getString("modify_user_id"),
                            rs.getBoolean("is_active")
                    )));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    /**
     * Implements SQL SELECT (by primary key) query.
     * <p>
     * Tries to find {@link Contractor} suitable entity in 'contractor' table
     * and all relevant data from related tables ('country', 'industry' and 'org_form').
     *
     * @param id value of {@code id} field of searched {@code Contractor} entity
     * @return coupled data - if request was successful, {@code Optional.empty()} - else
     */
    public Optional<List<Object>> get(String id) {
        String sql = """
                SELECT contractor.*,
                country.id AS c_id, country.name AS c_name, country.is_active AS c_active,
                industry.id AS i_id, industry.name AS i_name, industry.is_active AS i_active,
                org_form.id AS of_id, org_form.name AS of_name, org_form.is_active AS of_active
                FROM contractor
                JOIN country ON contractor.country = country.id
                JOIN industry ON contractor.industry = industry.id
                JOIN org_form ON contractor.org_form = org_form.id
                WHERE contractor.id = :id;
               """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", id),
                    (rs, rowNum) -> {
                        Contractor contractor = new Contractor(
                                rs.getString("id"),
                                rs.getString("parent_id"),
                                rs.getString("name"),
                                rs.getString("name_full"),
                                rs.getString("inn"),
                                rs.getString("ogrn"),
                                rs.getString("country"),
                                rs.getInt("industry"),
                                rs.getInt("org_form"),
                                rs.getDate("create_date"),
                                rs.getDate("modify_date"),
                                rs.getString("create_user_id"),
                                rs.getString("modify_user_id"),
                                rs.getBoolean("is_active")
                        );
                        Country country = new Country(
                                rs.getString("c_id"),
                                rs.getString("c_name"),
                                rs.getBoolean("c_active")
                        );
                        Industry industry = new Industry(
                                rs.getInt("i_id"),
                                rs.getString("i_name"),
                                rs.getBoolean("i_active")
                        );
                        OrgForm orgForm = new OrgForm(
                                rs.getInt("of_id"),
                                rs.getString("of_name"),
                                rs.getBoolean("of_active")
                        );
                        return List.of(contractor, country, industry, orgForm);
                    }));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    /**
     * Implements logical removing from database.
     * <p>
     * Update 'is_active' field to 'false' value instead of executing the DELETE query.
     *
     * @param id value of {@code id} field of {@link Contractor} instance
     * @return updated rows amount
     */
    public int delete(String id) {
        return jdbcTemplate.update("""
                UPDATE contractor
                SET is_active = false
                WHERE id = :id;
                """, Collections.singletonMap("id", id));
    }

    /**
     * Implements SQL SELECT query with sorting and paging.
     * <p>
     * Finds suitable {@link Contractor} entities in database then returns some of them.
     * Uses {@link ContractorSearch} instance as source of sorting fields.
     * Can`t return more instances than {@code size}.
     *
     * @param contractorSearch sorting field
     * @param page number of result page that will be returned
     * @param size amount of returned entities
     * @return {@code Contractor} instances that match the given condition (in {@code contactorSearch})
     */
    public List<Contractor> search(ContractorSearch contractorSearch, int page, int size) {
        String sql = """
                SELECT *
                FROM contractor
                WHERE is_active AND
                id ILIKE :id AND
                COALESCE(parent_id, '') ILIKE :parentId AND
                (name ILIKE :name OR COALESCE(name_full, '') ILIKE :nameFull OR COALESCE(inn, '') ILIKE :inn
                 OR COALESCE(ogrn, '') ILIKE :ogrn) AND
                country = ANY (SELECT id FROM country WHERE name ILIKE :country) AND
                CAST(industry AS TEXT) ILIKE :industry AND
                org_form = ANY (SELECT id FROM org_form WHERE name ILIKE :orgForm)
                LIMIT :size OFFSET :page;
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("id", contractorSearch.getContractorId().orElse("%"));
        params.put("parentId", contractorSearch.getParentId().orElse("%"));
        params.put("name", format(contractorSearch.getContractorSearch().orElse("%")));
        params.put("nameFull", format(contractorSearch.getContractorSearch().orElse("%")));
        params.put("inn", format(contractorSearch.getContractorSearch().orElse("%")));
        params.put("ogrn", format(contractorSearch.getContractorSearch().orElse("%")));
        params.put("country", format(contractorSearch.getCountry().orElse("%")));
        params.put("industry", contractorSearch.getIndustry().orElse("%"));
        params.put("orgForm", format(contractorSearch.getOrgForm().orElse("%")));
        params.put("size", size);
        params.put("page", page * size);
        return jdbcTemplate.query(sql, params,
                (rs, rowNum) -> new Contractor(
                rs.getString("id"),
                rs.getString("parent_id"),
                rs.getString("name"),
                rs.getString("name_full"),
                rs.getString("inn"),
                rs.getString("ogrn"),
                rs.getString("country"),
                rs.getInt("industry"),
                rs.getInt("org_form"),
                rs.getDate("create_date"),
                rs.getDate("modify_date"),
                rs.getString("create_user_id"),
                rs.getString("modify_user_id"),
                rs.getBoolean("is_active")
        ));
    }

    private String format(String param) {
        return param.equals("%") ? param : "%" + param + "%";
    }

}
