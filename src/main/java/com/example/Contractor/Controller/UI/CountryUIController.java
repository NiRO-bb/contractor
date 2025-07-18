package com.example.Contractor.Controller.UI;

import com.example.Contractor.Controller.CountryController;
import com.example.Contractor.DTO.Country;
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
 * Just checks user role, then calls CountryController methods.
 */
@RestController
@RequestMapping("/ui/contractor/country")
@RequiredArgsConstructor
public class CountryUIController {

    private final CountryController controller;

    /**
     * Retrieves all active Country entities from database.
     * Requires USER role or higher for access.
     *
     * @return Country list
     */
    @Secured("USER")
    @GetMapping("/all")
    public ResponseEntity<?> get() {
        return controller.get();
    }

    /**
     * Retrieves specified Country entity by passed id value.
     * Requires USER role or higher for access.
     *
     * @param id value of id field of Country entity that must be retrieved
     * @return matched Country entity
     */
    @Secured("USER")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        return controller.get(id);
    }

    /**
     * Adds or updates Country instance.
     * Requires CONTRACTOR_SUPERUSER role or higher for access.
     *
     * @param country Country instance that must be added or updated
     * @return added/updated Country instance
     */
    @Secured("CONTRACTOR_SUPERUSER")
    @PutMapping("/save")
    public ResponseEntity<?> save(@RequestBody Country country) {
        return controller.save(country);
    }

    /**
     * Logically deletes Country entity from database.
     * Requires CONTRACTOR_SUPERUSER role or higher for access.
     *
     * @param id value of id field of Country entity that must be deleted
     * @return http NO_CONTENT status if successful, NOT_FOUND else
     */
    @Secured("CONTRACTOR_SUPERUSER")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return controller.delete(id);
    }

}
