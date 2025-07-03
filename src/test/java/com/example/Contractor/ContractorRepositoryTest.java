package com.example.Contractor;

import com.example.Contractor.DTO.Contractor;
import com.example.Contractor.DTO.ContractorSearch;
import com.example.Contractor.DTO.Country;
import com.example.Contractor.DTO.Industry;
import com.example.Contractor.DTO.OrgForm;
import com.example.Contractor.Repository.ContractorRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootTest
public class ContractorRepositoryTest {

    @Autowired
    private ContractorRepository repository;

    @BeforeAll
    public static void setup(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("INSERT INTO country (id, name) VALUES ('TEST', 'TestCountry')");
        jdbcTemplate.update("INSERT INTO industry (name) VALUES ('TestIndustry')");
        jdbcTemplate.update("INSERT INTO org_form (name) VALUES ('TestOrgForm')");
        jdbcTemplate.update("""
        INSERT INTO contractor
        (id, parent_id, name, name_full, inn, ogrn, country, industry, org_form)
        VALUES ('0', null, 'baseName', 'baseNameFull', 'baseInn', 'baseOgrn', 'TEST', 1, 1)
        """);
    }

    @Test
    public void testSave(@Autowired JdbcTemplate jdbcTemplate) {
        Contractor c;

        Assertions.assertEquals(1,
                jdbcTemplate.queryForObject("SELECT COUNT(*) FROM contractor", Integer.class));

        repository.save(new Contractor(
                "1",
                "0",
                "testName",
                "TestNameFull",
                "testInn",
                "testOgrn",
                "TEST",
                1,
                1));
        Assertions.assertEquals(2,
                jdbcTemplate.queryForObject("SELECT COUNT(*) FROM contractor", Integer.class));
        c = jdbcTemplate.queryForObject("SELECT * FROM contractor offset 1", (rs, rowNum) -> new Contractor(
                rs.getString("id"),
                rs.getString("parent_id"),
                rs.getString("name"),
                rs.getString("name_full"),
                rs.getString("inn"),
                rs.getString("ogrn"),
                rs.getString("country"),
                rs.getInt("industry"),
                rs.getInt("org_form")
        ));
        Assertions.assertEquals("testName", c.getName());

        repository.save(new Contractor(
                "1",
                "0",
                "testName2",
                "fullNameFull2",
                "testInn2",
                "testOgrn2",
                "TEST",
                1,
                1));
        Assertions.assertEquals(2,
                jdbcTemplate.queryForObject("SELECT COUNT(*) FROM contractor", Integer.class));
        c = jdbcTemplate.queryForObject("SELECT * FROM contractor offset 1", (rs, rowNum) -> new Contractor(
                rs.getString("id"),
                rs.getString("parent_id"),
                rs.getString("name"),
                rs.getString("name_full"),
                rs.getString("inn"),
                rs.getString("ogrn"),
                rs.getString("country"),
                rs.getInt("industry"),
                rs.getInt("org_form")
        ));
        Assertions.assertEquals("testName2", c.getName());

        jdbcTemplate.update("DELETE FROM contractor WHERE id = '1'");
    }

    @Test
    public void testGet() {
        List<Object> list = repository.get("0");
        Contractor contractor = (Contractor) list.get(0);
        Country country = (Country) list.get(1);
        Industry industry = (Industry) list.get(2);
        OrgForm orgForm  = (OrgForm) list.get(3);
        Assertions.assertEquals("baseName", contractor.getName());
        Assertions.assertEquals("TestCountry", country.getName());
        Assertions.assertEquals("TestIndustry", industry.getName());
        Assertions.assertEquals("TestOrgForm", orgForm.getName());

        list = repository.get("invalid");
        Assertions.assertNull(list);
    }

    @Test
    public void testDelete(@Autowired JdbcTemplate jdbcTemplate) {
        Assertions.assertEquals(1, repository.delete("0"));
        Assertions.assertEquals(0, repository.delete("invalid"));
        jdbcTemplate.update("UPDATE contractor SET is_active = true WHERE id = '0'");
    }

    @Test
    public void testSearch() {
        Contractor contractor = repository.search(new ContractorSearch(
                "0", null, null, null, null, null), 0).getFirst();
        Assertions.assertEquals("0", contractor.getId());

        contractor = repository.search(new ContractorSearch(
                null, null, "base", null, null, null), 0).getFirst();
        Assertions.assertEquals("baseName", contractor.getName());

        contractor = repository.search(new ContractorSearch(
                null, null, null, "test", null, null), 0).getFirst();
        Assertions.assertEquals("TEST", contractor.getCountry());

        contractor = repository.search(new ContractorSearch(
                null, null, null, null, 1, null), 0).getFirst();
        Assertions.assertEquals(1, contractor.getIndustry());

        contractor = repository.search(new ContractorSearch(
                null, null, null, null, null, "test"), 0).getFirst();
        Assertions.assertEquals(1, contractor.getOrgForm());

        List<Contractor> list = repository.search(new ContractorSearch(
                "invalid", null, null, null, null, null), 0);
        Assertions.assertTrue(list.isEmpty());
    }

    @AfterAll
    public static void cleanUp(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("DROP SCHEMA public CASCADE");
        jdbcTemplate.execute("CREATE SCHEMA public");
    }
}
