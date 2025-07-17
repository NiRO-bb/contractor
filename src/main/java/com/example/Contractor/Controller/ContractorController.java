package com.example.Contractor.Controller;

import com.example.Contractor.DTO.Contractor;
import com.example.Contractor.DTO.ContractorSearch;
import com.example.Contractor.Service.ContractorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Handles incoming http-requests related with Contractor.
 * <p>
 * Provides access to service layer with some CRUD-methods.
 */
@RestController
@RequestMapping("/contractor")
@RequiredArgsConstructor
public class ContractorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractorController.class);

    private final ContractorService service;

    /**
     * Responsible for creating and updating {@code Contractor} entities.
     * <p>
     * Receives {@link Contractor} instance as request body.
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @param contractor instance that must be saved or updated (by {@code id} field) in database
     * @return added/updated {@code Contractor} instance and http OK status, or INTERNAL_SERVER_ERROR
     */
    @PutMapping("/save")
    public ResponseEntity<?> save(@RequestBody Contractor contractor) {
        try {
            Optional<?> optContractor = service.save(contractor);
            if (optContractor.isPresent()) {
                LOGGER.info("Contractor added {}", contractor.desc());
                return new ResponseEntity<>(optContractor.get(), HttpStatus.OK);
            } else {
                LOGGER.error("Contractor not added {}", contractor.desc());
                return new ResponseEntity<>("Contractor adding/updating was failed.",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception exception) {
            LOGGER.error("Contractor adding/updating was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("Contractor adding/updating was failed.");
        }
    }

    /**
     * Responsible for providing {@code Contractor} instance from database.
     * <p>
     * Provides all related data (from {@link com.example.Contractor.DTO.Country},
     * {@link com.example.Contractor.DTO.Industry} and {@link com.example.Contractor.DTO.OrgForm} also).
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @param id value of {@code id} field of {@link Contractor} instance
     * @return found {@code Contractor} instance with related data and http OK status;
     * NOT_FOUND if there is no matched Contractor entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        try {
            Optional<?> contractorInfo = service.get(id);
            if (contractorInfo.isPresent()) {
                LOGGER.info("Contractor obtained {}", String.format("{ \"id\":\"%s\" }", id));
                return new ResponseEntity<>(contractorInfo.get(), HttpStatus.OK);
            } else {
                LOGGER.warn("Contractor not obtained {}; There is no contractor with such id",
                        String.format("{ \"id\":\"%s\" }", id));
                return new ResponseEntity<>("There is no contractor with such ID.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            LOGGER.error("Contractor obtaining was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("Contractor obtaining was failed.");
        }
    }

    /**
     * Responsible for deleting {@code Contractor} instance from database.
     * <p>
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @param id value of {@code id} field of {@link Contractor} instance
     * @return http NO_CONTENT status (success); NOT_FOUND (there is no matched Contractor entity)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            if (service.delete(id) > 0) {
                LOGGER.info("Contractor deleted {}", String.format("{ \"id\":\"%s\" }", id));
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                LOGGER.error("Contractor not deleted {}; There is no contractor with such id",
                        String.format("{ \"id\":\"%s\" }", id));
                return new ResponseEntity<>("Contractor deleting was failed. There is no contractor with such ID.",
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            LOGGER.error("Contractor deleting was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("Contractor deleting was failed.");
        }
    }

    /**
     * Responsible for providing {@code Contractor} instances with sorting and paging.
     * <p>
     * Receives {@link ContractorSearch} instance as request body.
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
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
        try {
            List<Contractor> contractors = service.search(contractorSearch, page, size);
            if (!contractors.isEmpty()) {
                LOGGER.info("Contractor list obtained {}", String.format("{ \"count\":%d }", contractors.size()));
                return new ResponseEntity<>(contractors, HttpStatus.OK);
            } else {
                LOGGER.info("Contractor list not obtained { \"count\":0 }");
                return new ResponseEntity<>("Could not find any suitable contractor. ", HttpStatus.OK);
            }
        } catch (Exception exception) {
            LOGGER.error("Contractor searching was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("Contractor searching was failed.");
        }
    }

}
