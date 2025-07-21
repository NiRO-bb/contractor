package com.example.Contractor.Controller.UI;

import com.example.Contractor.Controller.ContractorController;
import com.example.Contractor.DTO.Contractor;
import com.example.Contractor.DTO.ContractorSearch;
import com.example.Contractor.Utils.RoleAccess;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Responsible for request that require authentication.
 * Just checks user role, then calls ContractorController methods.
 */
@Hidden
@RestController
@RequestMapping("/ui/contractor")
@RequiredArgsConstructor
public class ContractorUIController {

    private final ContractorController controller;

    /**
     * Retrieves contractor entity from database.
     * Requires USER role or higher for access.
     *
     * @param id value of {@code id} field of {@link Contractor} instance
     * @return found {@code Contractor} instance and related data
     */
    @Secured("USER")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        return controller.get(id);
    }

    /**
     * Adds or update Contractor instance in database.
     * Requires CONTRACTOR_SUPERUSER role or higher for access.
     *
     * @param contractor instance that must be added or updated
     * @return added/updated entity
     */
    @Secured("CONTRACTOR_SUPERUSER")
    @PutMapping("/save")
    public ResponseEntity<?> save(@RequestBody Contractor contractor) {
        return controller.save(contractor);
    }

    /**
     * Logically deletes Contractor entity from database.
     * Requires CONTRACTOR_SUPERUSER role or higher for access.
     *
     * @param id value of {@code id} field of {@link Contractor} instance
     * @return some http status - NO_CONTENT (success), NOT_FOUND (failure) or INTERNAL_SERVER_ERROR
     */
    @Secured("CONTRACTOR_SUPERUSER")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return controller.delete(id);
    }

    /**
     * Search all Contractor entities that match passed parameters.
     * Requires CONTRACTOR_RUS role (with ContractorSearch.country = "RUS")
     * or CONTRACTOR_SUPERUSER and higher for access.
     *
     * @param search contains the filtering fields
     * @param page number of result page that will be returned
     * @param size amount of returned Contractor entities
     * @return Contractor instances that match the given condition
     * returned instances count will be no more than page size
     */
    @Secured("CONTRACTOR_RUS")
    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody ContractorSearch search,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        if (RoleAccess.hasAccess(search)) {
            return controller.search(search, page, size);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
