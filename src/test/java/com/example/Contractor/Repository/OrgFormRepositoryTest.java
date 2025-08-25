package com.example.Contractor.Repository;

import com.example.Contractor.DTO.OrgForm;
import com.example.Contractor.AbstractContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class OrgFormRepositoryTest extends AbstractContainer {

    @Autowired
    private OrgFormRepository repository;

    @Test
    public void testGetAll() {
        List<OrgForm> list = repository.get();
        for (OrgForm c : list) {
            Assertions.assertTrue(c.isActive());
        }
    }

    @Test
    public void testById() {
        Assertions.assertEquals("-", repository.get(1).get().getName());
        Assertions.assertEquals(Optional.empty(), repository.get(-1));
    }

    @Test
    public void testSave() {
        Optional<OrgForm> OrgForm = repository.save(new OrgForm(999, "TestOrgForm", true));
        Assertions.assertEquals("TestOrgForm", OrgForm.get().getName());
        OrgForm = repository.save(new OrgForm(999, "TestOrgFormUpdated", false));
        Assertions.assertEquals("TestOrgFormUpdated", OrgForm.get().getName());
    }

    @Test
    public void testDelete() {
        Assertions.assertEquals(1, repository.delete(1));
    }

}
