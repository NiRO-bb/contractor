package com.example.Contractor.Controller.UI;

import com.example.Contractor.Controller.OrgFormController;
import com.example.Contractor.DTO.OrgForm;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Responsible for request that require authentication.
 * Just checks user role, then calls OrgFormController methods.
 */
@Hidden
@RestController
@RequestMapping("/ui/contractor/org_form")
@RequiredArgsConstructor
public class OrgFormUIController {

    private final OrgFormController controller;

    /**
     * Retrieves all active OrgForm entities from database.
     * Requires USER role or higher for access.
     *
     * @return OrgForm list
     */
    @Secured("USER")
    @GetMapping("/all")
    public ResponseEntity<?> get() {
        return controller.get();
    }

    /**
     * Retrieves specified OrgForm entity by passed id value.
     * Requires USER role or higher for access.
     *
     * @param id value of id field of OrgForm entity that must be retrieved
     * @return matched OrgForm entity
     */
    @Secured("USER")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") int id) {
        return controller.get(id);
    }

    /**
     * Adds or updates OrgForm instance.
     * Requires CONTRACTOR_SUPERUSER role or higher for access.
     *
     * @param orgForm OrgForm instance that must be added or updated
     * @return added/updated OrgForm instance
     */
    @Secured("CONTRACTOR_SUPERUSER")
    @PutMapping("/save")
    public ResponseEntity<?> save(@RequestBody OrgForm orgForm) {
        return controller.save(orgForm);
    }

    /**
     * Logically deletes OrgForm entity from database.
     * Requires CONTRACTOR_SUPERUSER role or higher for access.
     *
     * @param id value of id field of OrgForm entity that must be deleted
     * @return http NO_CONTENT status if successful, NOT_FOUND else
     */
    @Secured("CONTRACTOR_SUPERUSER")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        return controller.delete(id);
    }

}
