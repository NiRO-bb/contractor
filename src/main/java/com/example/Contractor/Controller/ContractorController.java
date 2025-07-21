package com.example.Contractor.Controller;

import com.example.Contractor.DTO.Contractor;
import com.example.Contractor.DTO.ContractorSearch;
import org.springframework.http.ResponseEntity;

/**
 * Provides interface for Contractor controller class.
 * Contains basic CRUD operations and search method with filtering and pagination.
 */
public interface ContractorController {

    /**
     * Adds and updates Contractor entities.
     *
     * @param contractor instance that must be saved
     * @return added/updated Contractor entity
     */
    ResponseEntity<?> save(Contractor contractor);

    /**
     * Retrieves Contractor entity by passed id value.
     *
     * @param id value of id field of Contractor entity that must be retrieved
     * @return matched Contractor entity
     */
    ResponseEntity<?> get(String id);

    /**
     * Deletes Contractor entity by passed id value.
     *
     * @param id value of id field of Contractor entity that must be deleted
     * @return NO_CONTENT response code if successful, NOT_FOUND else
     */
    ResponseEntity<?> delete(String id);

    /**
     * Retrieves Contactor entities by passed parameters.
     * Results will be partitioned as pages.
     *
     * @param search contains filtering fields
     * @param page number of returning page
     * @param size size (amount of Contractor entities) of page
     * @return matched Contactor entities
     */
    ResponseEntity<?> search(ContractorSearch search, int page, int size);

}
