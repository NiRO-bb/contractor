package com.example.Contractor;

import com.example.Contractor.DTO.Contractor;
import com.example.Contractor.DTO.ContractorSearch;
import com.example.Contractor.DTO.Country;
import com.example.Contractor.DTO.Industry;
import com.example.Contractor.DTO.OrgForm;
import com.example.Contractor.Repository.ContractorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootTest
@ExtendWith(DatabaseSetup.class)
public class ContractorRepositoryTest {

    @Autowired
    private ContractorRepository repository;

    @BeforeAll
    public static void setup(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("""
        INSERT INTO contractor
        (id, parent_id, name, name_full, inn, ogrn, country, industry, org_form)
        VALUES ('0', null, 'baseName', 'baseNameFull', 'baseInn', 'baseOgrn', 'ABH', 1, 1)
        """);
    }

    @Test
    public void testSave(@Autowired JdbcTemplate jdbcTemplate) {
        Assertions.assertEquals(1,
                jdbcTemplate.queryForObject("SELECT COUNT(*) FROM contractor", Integer.class));

        Contractor c = new Contractor();
        c.setId("1");
        c.setName("testName");
        c.setCountry("ABH");
        c.setIndustry(1);
        c.setOrgForm(1);
        repository.save(c);
        Assertions.assertEquals(2,
                jdbcTemplate.queryForObject("SELECT COUNT(*) FROM contractor", Integer.class));
        c = jdbcTemplate.queryForObject("SELECT * FROM contractor offset 1", (rs, rowNum) -> {
            Contractor c2 = new Contractor();
            c2.setId(rs.getString("id"));
            c2.setName(rs.getString("name"));
            c2.setCountry(rs.getString("country"));
            c2.setIndustry(rs.getInt("industry"));
            c2.setOrgForm(rs.getInt("org_form"));
            return c2;
        });
        Assertions.assertEquals("testName", c.getName());

        c.setName("testName2");
        repository.save(c);
        Assertions.assertEquals(2,
                jdbcTemplate.queryForObject("SELECT COUNT(*) FROM contractor", Integer.class));
        c = jdbcTemplate.queryForObject("SELECT * FROM contractor offset 1", (rs, rowNum) -> {
            Contractor c2 = new Contractor();
            c2.setName(rs.getString("name"));
            return c2;
        });
        Assertions.assertEquals("testName2", c.getName());

        jdbcTemplate.update("DELETE FROM contractor WHERE id = '1'");
    }

    @Test
    public void testGet() {
        List<Object> list = repository.get("0").get();
        Contractor contractor = (Contractor) list.get(0);
        Country country = (Country) list.get(1);
        Industry industry = (Industry) list.get(2);
        OrgForm orgForm  = (OrgForm) list.get(3);
        Assertions.assertEquals("baseName", contractor.getName());
        Assertions.assertEquals("Абхазия", country.getName());
        Assertions.assertEquals("Авиастроение", industry.getName());
        Assertions.assertEquals("-", orgForm.getName());
        Assertions.assertTrue(repository.get("invalid").isEmpty());
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
                "0", null, null, null, null, null), 0, 1).getFirst();
        Assertions.assertEquals("0", contractor.getId());

        contractor = repository.search(new ContractorSearch(
                null, null, "base", null, null, null), 0, 1).getFirst();
        Assertions.assertEquals("baseName", contractor.getName());

        contractor = repository.search(new ContractorSearch(
                null, null, null, "абхазия", null, null), 0, 1).getFirst();
        Assertions.assertEquals("ABH", contractor.getCountry());

        contractor = repository.search(new ContractorSearch(
                null, null, null, null, 1, null), 0, 1).getFirst();
        Assertions.assertEquals(1, contractor.getIndustry());

        contractor = repository.search(new ContractorSearch(
                null, null, null, null, null, "-"), 0, 1).getFirst();
        Assertions.assertEquals(1, contractor.getOrgForm());

        List<Contractor> list = repository.search(new ContractorSearch(
                "invalid", null, null, null, null, null), 0, 1);
        Assertions.assertTrue(list.isEmpty());
    }

}
