package com.example.Contractor.Controller;

import com.example.Contractor.DTO.Contractor;
import com.example.Contractor.DTO.ContractorSearch;
import com.example.Contractor.Service.ContractorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

/**
 * Handles incoming http-requests at the URL {@code /contractor}
 * <p>
 * Provides access to service layer with some CRUD-methods.
 */
@RestController
@RequestMapping("/contractor")
public class ContractorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractorController.class);

    private final ContractorService service;

    /**
     * Creates a class instance with initialized {@code ContractorService}.
     *
     * @param service service that will handle further requests
     * @see ContractorService
     */
    @Autowired
    public ContractorController(ContractorService service) {
        this.service = service;
    }

    /**
     * Responsible for creating and updating {@code Contractor} entities.
     * <p>
     * Can be called at URL {@code /contractor/save}.
     * Receives {@link Contractor} instance as request body.
     *
     * @param contractor instance that must be saved or updated (by {@code id} field) in database
     * @return added/updated {@code Contractor} instance
     */
    @PutMapping("/save")
    public ResponseEntity<?> save(@RequestBody Contractor contractor) {
        Optional<?> optContractor = service.save(contractor);
        if (optContractor.isPresent()) {
            LOGGER.info("Contractor added {}", contractor.desc());
            return new ResponseEntity<>(optContractor.get(), HttpStatus.OK);
        } else {
            LOGGER.error("Contractor not added {}", contractor.desc());
            return new ResponseEntity<>("Contractor adding/updating was failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responsible for providing {@code Contractor} instance from database.
     * <p>
     * Can be called at URL {@code /contractor/{id}}.
     * Provides all related data (from {@link com.example.Contractor.DTO.Country},
     * {@link com.example.Contractor.DTO.Industry} and {@link com.example.Contractor.DTO.OrgForm} also).
     *
     * @param id value of {@code id} field of {@link Contractor} instance
     * @return found {@code Contractor} instance and related data
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        Optional<?> contractorInfo = service.get(id);
        if (contractorInfo.isPresent()) {
            LOGGER.info("Contractor obtained {}", String.format("{ \"id\":\"%s\" }", id));
            return new ResponseEntity<>(contractorInfo.get(), HttpStatus.OK);
        } else {
            LOGGER.warn("Contractor not obtained {}", String.format("{ \"id\":\"%s\" }", id));
            return new ResponseEntity<>("There is no contractor with such ID.", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Responsible for deleting {@code Contractor} instance from database.
     * <p>
     * Can be called at URL {@code /contractor/delete/{id}}.
     *
     * @param id value of {@code id} field of {@link Contractor} instance
     * @return http status of request - if successful, error message additionally - else
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        int result = service.delete(id);
        if (result > 0) {
            LOGGER.info("Contractor deleted {}", String.format("{ \"id\":\"%s\" }", id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            LOGGER.error("Contractor not deleted {}", String.format("{ \"id\":\"%s\" }", id));
            return new ResponseEntity<>("Contractor deleting was failed. There is no contractor with such ID.", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Responsible for providing {@code Contractor} instances with sorting and paging.
     * <p>
     * Can be called at URL {@code /contractor/search}.
     * Receives {@link ContractorSearch} instance as request body.
     *
     * @param contractorSearch contains the sorting fields
     * @param page number of result page that will be returned
     * @param size amount of returned {@link Contractor} entities
     * @return {@code Contractor} instances that match the given condition (in {@code contactorSearch});
     * returned instances count will be no more than page size
     */
    @PostMapping("/search")
    public ResponseEntity<?> search(
            @RequestBody ContractorSearch contractorSearch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Contractor> contractors = service.search(contractorSearch, page, size);
        if (!contractors.isEmpty()) {
            LOGGER.info("Contractor list obtained {}", String.format("{ \"count\":%d }", contractors.size()));
            return new ResponseEntity<>(contractors, HttpStatus.OK);
        } else {
            LOGGER.info("Contractor list not obtained {\"count\":0}");
            return new ResponseEntity<>("Could not find any suitable contractor. ", HttpStatus.OK);
        }
    }

}
