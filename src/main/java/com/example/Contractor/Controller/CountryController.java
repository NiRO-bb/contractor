package com.example.Contractor.Controller;

import com.example.Contractor.DTO.Country;
import org.springframework.http.ResponseEntity;

/**
 * Provides interface for Country controller class.
 * Contains basic CRUD methods.
 */
public interface CountryController {

    /**
     * Retrieves all active Country entities.
     *
     * @return matched Country entities
     */
    ResponseEntity<?> get();

    /**
     * Retrieves Country entity by passed id value.
     *
     * @param id value of id field of Country entity that must be retrieved
     * @return matched Country entity
     */
    ResponseEntity<?> get(String id);

    /**
     * Adds and updates Country entity.
     *
     * @param country instance that must be saved
     * @return added/updated Country entity
     */
    ResponseEntity<?> save(Country country);

    /**
     * Logically deletes Country entity by passed id value.
     *
     * @param id value of id field of Country entity that must be deleted
     * @return NO_CONTENT response code if successful, NOT_FOUND else
     */
    ResponseEntity<?> delete(String id);

}
