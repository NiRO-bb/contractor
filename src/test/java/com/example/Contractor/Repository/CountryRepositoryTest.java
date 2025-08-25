package com.example.Contractor.Repository;

import com.example.Contractor.DTO.Country;
import com.example.Contractor.AbstractContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CountryRepositoryTest extends AbstractContainer {

    @Autowired
    private CountryRepository repository;

    @Test
    public void testGetAll() {
        List<Country> list = repository.get();
        for (Country c : list) {
            Assertions.assertTrue(c.isActive());
        }
    }

    @Test
    public void testById() {
        Assertions.assertEquals("Абхазия", repository.get("ABH").get().getName());
        Assertions.assertEquals(Optional.empty(), repository.get("invalid"));
    }

    @Test
    public void testSave() {
        Optional<Country> country = repository.save(new Country("TEST4", "TestCountry4", true));
        Assertions.assertEquals("TestCountry4", country.get().getName());
        country = repository.save(new Country("TEST4", "TestCountryUpdated", false));
        Assertions.assertEquals("TestCountryUpdated", country.get().getName());
    }

    @Test
    public void testDelete() {
        Assertions.assertEquals(1, repository.delete("ABH"));
    }

}
