package com.example.Contractor.Repository;

import com.example.Contractor.DTO.Industry;
import com.example.Contractor.DatabaseSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(DatabaseSetup.class)
public class IndustryRepositoryTest {

    @Autowired
    private IndustryRepository repository;

    @Test
    public void testGetAll() {
        List<Industry> list = repository.get();
        for (Industry c : list) {
            Assertions.assertTrue(c.isActive());
        }
    }

    @Test
    public void testById() {
        Assertions.assertEquals("Авиастроение", repository.get(1).get().getName());
        Assertions.assertEquals(Optional.empty(), repository.get(-1));
    }

    @Test
    public void testSave() {
        Optional<Industry> Industry = repository.save(new Industry(999, "TestIndustry", true));
        Assertions.assertEquals("TestIndustry", Industry.get().getName());
        Industry = repository.save(new Industry(999, "TestIndustryUpdated", false));
        Assertions.assertEquals("TestIndustryUpdated", Industry.get().getName());
    }

    @Test
    public void testDelete() {
        Assertions.assertEquals(1, repository.delete(1));
    }

}
