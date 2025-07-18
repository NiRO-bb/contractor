package com.example.Contractor.Controller;

import com.example.Contractor.DTO.OrgForm;
import com.example.Contractor.Service.OrgFormService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Handles incoming http-requests related with OrgForm.
 * <p>
 * Provides access to service layer with some CRUD-methods.
 */
@RestController
@RequestMapping("/contractor/org_form")
@RequiredArgsConstructor
public class OrgFormController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrgFormController.class);

    private final OrgFormService service;

    /**
     * Responsible for providing active {@code OrgForm} instances.
     * <p>
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @return list of active (value of {@code is_active} field = true) {@code OrgForm} instances and OK status
     */
    @GetMapping("/all")
    public ResponseEntity<?> get() {
        try {
            List<OrgForm> list = service.get();
            if (!list.isEmpty()) {
                LOGGER.info("OrgForm list obtained {}", String.format("{ \"count\":%d }", list.size()));
                return new ResponseEntity<>(list, HttpStatus.OK);
            } else {
                LOGGER.warn("OrgForm list not obtained { \"count\":0 }");
                return new ResponseEntity<>("OrgForm list is empty.", HttpStatus.OK);
            }
        } catch (Exception exception) {
            LOGGER.error("OrgForm list obtaining was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("OrgForm list obtaining was failed.");
        }
    }

    /**
     * Responsible for providing {@code OrgForm} instance from database.
     * <p>
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @param id value of {@code id} field of {@code OrgForm} instance
     * @return {@code OrgForm} instance and OK status; NOT_FOUND if there is no matched OrgForm entities
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") int id) {
        try {
            Optional<OrgForm> optOrgForm = service.get(id);
            if (optOrgForm.isPresent()) {
                LOGGER.info("OrgForm obtained {}", optOrgForm.get().desc());
                return new ResponseEntity<>(optOrgForm, HttpStatus.OK);
            } else {
                LOGGER.info("OrgForm not obtained {}", String.format(" \"id\":\"%s\" ", id));
                return new ResponseEntity<>("There is no OrgForm with such id.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            LOGGER.error("OrgForm obtaining was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("OrgForm obtaining was failed.");
        }
    }

    /**
     * Responsible for creating and updating {@code OrgForm} instances.
     * <p>
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @param orgForm instance that must be added or updated in database
     * @return added/updated OrgForm entity and OK status; INTERNAL_SERVER_ERROR if something goes wrong
     */
    @PutMapping("/save")
    public ResponseEntity<?> save(@RequestBody OrgForm orgForm) {
        try {
            Optional<OrgForm> optOrgForm = service.save(orgForm);
            if (optOrgForm.isPresent()) {
                LOGGER.info("OrgForm added {}", orgForm.desc());
                return new ResponseEntity<>(optOrgForm, HttpStatus.OK);
            } else {
                LOGGER.error("OrgForm not added {}", orgForm.desc());
                return new ResponseEntity<>("OrgForm adding/updating was failed.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception exception) {
            LOGGER.error("OrgForm adding/updating was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("OrgForm adding/updating was failed.");
        }
    }

    /**
     * Responsible for deleting {@code OrgForm} instance from database.
     * <p>
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @param id value of {@code id} field of {@code OrgForm} instance
     * @return NO_CONTENT - success, NOT_FOUND - there is no matched OrgForm entities
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        try {
            if (service.delete(id) > 0) {
                LOGGER.info("OrgForm deleted {}", String.format(" \"id\":\"%s\" ", id));
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                LOGGER.warn("OrgForm not deleted {}", String.format(" \"id\":\"%s\" ", id));
                return new ResponseEntity<>("OrgForm deleting was failed. There is no OrgForm with such ID.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            LOGGER.error("OrgForm deleting was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("OrgForm deleting was failed.");
        }
    }

}
