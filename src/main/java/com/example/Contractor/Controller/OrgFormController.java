package com.example.Contractor.Controller;

import com.example.Contractor.DTO.OrgForm;
import org.springframework.http.ResponseEntity;

/**
 * Provides interface for OrgForm controller class.
 * Contains basic CRUD methods.
 */
public interface OrgFormController {

    /**
     * Retrieves all active OrgForm entities.
     *
     * @return matched OrgForm entities
     */
    ResponseEntity<?> get();

    /**
     * Retrieves OrgForm entity by passed id value.
     *
     * @param id value of id field of OrgForm entity that must be retrieved
     * @return matched OrgForm entity
     */
    ResponseEntity<?> get(int id);

    /**
     * Adds and updates OrgForm entity.
     *
     * @param orgForm instance that must be saved
     * @return added/updated OrgForm entity
     */
    ResponseEntity<?> save(OrgForm orgForm);

    /**
     * Logically deletes OrgForm entity by passed id value.
     *
     * @param id value of id field of OrgForm entity that must be deleted
     * @return NO_CONTENT response code if successful, NOT_FOUND else
     */
    ResponseEntity<?> delete(int id);

}
