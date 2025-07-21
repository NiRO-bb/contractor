package com.example.Contractor.Controller.UI;

import com.example.Contractor.Controller.IndustryController;
import com.example.Contractor.DTO.Industry;
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
 * Just checks user role, then calls IndustryController methods.
 */
@Hidden
@RestController
@RequestMapping("/ui/contractor/industry")
@RequiredArgsConstructor
public class IndustryUIController {

    private final IndustryController controller;

    /**
     * Retrieves all active Industry entities from database.
     * Requires USER role or higher for access.
     *
     * @return Industry list
     */
    @Secured("USER")
    @GetMapping("/all")
    public ResponseEntity<?> get() {
        return controller.get();
    }

    /**
     * Retrieves specified Industry entity by passed id value.
     * Requires USER role or higher for access.
     *
     * @param id value of id field of Industry entity that must be retrieved
     * @return matched Industry entity
     */
    @Secured("USER")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") int id) {
        return controller.get(id);
    }

    /**
     * Adds or updates Industry instance.
     * Requires CONTRACTOR_SUPERUSER role or higher for access.
     *
     * @param industry Industry instance that must be added or updated
     * @return added/updated Industry instance
     */
    @Secured("CONTRACTOR_SUPERUSER")
    @PutMapping("save")
    public ResponseEntity<?> save(@RequestBody Industry industry) {
        return controller.save(industry);
    }

    /**
     * Logically deletes Industry entity from database.
     * Requires CONTRACTOR_SUPERUSER role or higher for access.
     *
     * @param id value of id field of Industry entity that must be deleted
     * @return http NO_CONTENT status if successful, NOT_FOUND else
     */
    @Secured("CONTRACTOR_SUPERUSER")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        return controller.delete(id);
    }

}
