package com.example.Contractor.Repository;

import com.example.Contractor.DTO.Contractor;
import com.example.Contractor.DTO.ContractorSearch;
import com.example.Contractor.DTO.Country;
import com.example.Contractor.DTO.Industry;
import com.example.Contractor.DTO.OrgForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Provides access to database.
 * <p>
 * Contains CRUD-methods for data process.
 */
@Repository
public class ContractorRepository {

    private static final int PAGE_SIZE = 10;

    private JdbcTemplate jdbcTemplate;

    /**
     * Creates instance with initialized {@code JdbcTemplate}.
     * @param jdbcTemplate
     */
    @Autowired
    public ContractorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Implements SQL INSERT and UPDATE queries.
     * <p>
     * At first tries to insert new row into database.
     * If failed, updates the existing row.
     *
     * @param contractor instance that must be added or updated
     * @return added or updated rows amount
     */
    public int save(Contractor contractor) {
        String sql = """
                INSERT INTO contractor (id, parent_id, name, name_full, inn, ogrn, country, industry, org_form)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (id)
                DO UPDATE SET id = ?, parent_id = ?, name = ?, name_full = ?, inn = ?, ogrn = ?, country = ?, industry = ?, org_form = ?;
                """;
        return this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, contractor.getId());
            ps.setString(2, contractor.getParentId());
            ps.setString(3, contractor.getName());
            ps.setString(4, contractor.getNameFull());
            ps.setString(5, contractor.getInn());
            ps.setString(6, contractor.getOgrn());
            ps.setString(7, contractor.getCountry());
            ps.setInt(8, contractor.getIndustry());
            ps.setInt(9, contractor.getOrgForm());
            ps.setString(10, contractor.getId());
            ps.setString(11, contractor.getParentId());
            ps.setString(12, contractor.getName());
            ps.setString(13, contractor.getNameFull());
            ps.setString(14, contractor.getInn());
            ps.setString(15, contractor.getOgrn());
            ps.setString(16, contractor.getCountry());
            ps.setInt(17, contractor.getIndustry());
            ps.setInt(18, contractor.getOrgForm());
            return ps;
        });
    }

    /**
     * Implements SQL SELECT (by primary key) query.
     * <p>
     * Tries to find {@link Contractor} suitable instance in 'contractor' table
     * and all relevant data from related tables ('country', 'industry' and 'org_form').
     *
     * @param id value of {@code id} field of searched {@code Contractor} instance
     * @return coupled data - if request was successful, {@code null} - else
     */
    public List<Object> get(String id) {
        String sql = """
                SELECT contractor.*,
                country.id AS c_id, country.name AS c_name, country.is_active AS c_active,
                industry.id AS i_id, industry.name AS i_name, industry.is_active AS i_active,
                org_form.id AS of_id, org_form.name AS of_name, org_form.is_active AS of_active
                FROM contractor
                JOIN country ON contractor.country = country.id
                JOIN industry ON contractor.industry = industry.id
                JOIN org_form ON contractor.org_form = org_form.id
                WHERE contractor.id = ?;
               """;
        try {
            return this.jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
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
            }, id);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    /**
     * Implements logical removing from database.
     * <p>
     * UPDATE 'is_active' field to 'false' value instead of executing the DELETE query.
     *
     * @param id value of {@code id} field of {@link Contractor} instance
     * @return updated rows amount
     */
    public int delete(String id) {
        return this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("UPDATE contractor SET is_active=false WHERE id=?");
            ps.setString(1, id);
            return ps;
        });
    }

    /**
     * Implements SQL SELECT query with sorting and paging.
     * <p>
     * Finds suitable {@link Contractor} instances in database then returns some of them.
     * Use {@link ContractorSearch} instance as source of sorting fields.
     * Can`t return more instances then {@link #PAGE_SIZE}.
     *
     * @param contractorSearch sorting field
     * @param page number of result page that will be returned
     * @return {@code Contractor} instances that match the given condition (in {@code contactorSearch});
     * returned instances count will be no more than page size
     */
    public List<Contractor> search(ContractorSearch contractorSearch, int page) {
        String sql = """
                SELECT *
                FROM contractor
                WHERE is_active AND
                id ILIKE ? AND
                COALESCE(parent_id, '') ILIKE ? AND
                (name ILIKE ? OR name_full ILIKE ? OR inn ILIKE ? OR ogrn ILIKE ?) AND
                country = ANY (SELECT id FROM country WHERE name ILIKE ?) AND
                CAST(industry AS TEXT) ILIKE ? AND
                org_form = ANY (SELECT id FROM org_form WHERE name ILIKE ?)
                LIMIT ? OFFSET ?;
                """;
        List<Contractor> contractors = this.jdbcTemplate.query(sql, ps -> {
            ps.setString(1, contractorSearch.getContractorId().orElse("%"));
            ps.setString(2, contractorSearch.getParentId().orElse("%"));
            ps.setString(3, format(contractorSearch.getContractorSearch().orElse("%")));
            ps.setString(4, format(contractorSearch.getContractorSearch().orElse("%")));
            ps.setString(5, format(contractorSearch.getContractorSearch().orElse("%")));
            ps.setString(6, format(contractorSearch.getContractorSearch().orElse("%")));
            ps.setString(7, format(contractorSearch.getCountry().orElse("%")));
            ps.setString(8, contractorSearch.getIndustry().orElse("%"));
            ps.setString(9, format(contractorSearch.getOrgForm().orElse("%")));
            ps.setInt(10, PAGE_SIZE);
            ps.setInt(11, page * PAGE_SIZE);
        }, (rs, rowNum) -> new Contractor(
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

        return contractors;
    }

    private String format(String param) {
        return param.equals("%") ? param : "%" + param + "%";
    }

}
