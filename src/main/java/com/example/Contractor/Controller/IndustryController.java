package com.example.Contractor.Controller;

import com.example.Contractor.DTO.Industry;
import org.springframework.http.ResponseEntity;

/**
 * Provides interface for Industry controller class.
 * Contains basic CRUD methods.
 */
public interface IndustryController {

    /**
     * Retrieves all active Industry entities.
     *
     * @return matched Industry entities
     */
    ResponseEntity<?> get();

    /**
     * Retrieves Industry entity by passed id value.
     *
     * @param id value of id field of Industry entity that must be retrieved
     * @return matched Industry entity
     */
    ResponseEntity<?> get(int id);

    /**
     * Adds and updates Industry entity.
     *
     * @param industry instance that must be saved
     * @return added/updated Industry entity
     */
    ResponseEntity<?> save(Industry industry);

    /**
     * Logically deletes Industry entity by passed id value.
     *
     * @param id value of id field of Industry entity that must be deleted
     * @return NO_CONTENT response code if successful, NOT_FOUND else
     */
    ResponseEntity<?> delete(int id);

}
