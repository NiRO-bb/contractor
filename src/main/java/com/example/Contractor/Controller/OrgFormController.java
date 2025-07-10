package com.example.Contractor.Controller;

import com.example.Contractor.DTO.OrgForm;
import com.example.Contractor.Service.OrgFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Handles incoming http-requests at the URL {@code /org_form}
 * <p>
 * Provides access to service layer with some CRUD-methods.
 */
@RestController
@RequestMapping("/org_form")
public class OrgFormController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrgFormController.class);

    private final OrgFormService service;

    /**
     * Creates a class instance with initialized {@code OrgFormService}.
     *
     * @param service service that will handle further requests
     * @see OrgFormService
     */
    @Autowired
    public OrgFormController(OrgFormService service) {
        this.service = service;
    }

    /**
     * Responsible for providing active {@code OrgForm} instances.
     * <p>
     * Can be called at URL {@code /org_form/all}.
     *
     * @return list of active (value of {@code is_active} field = true) {@code OrgForm} instances
     */
    @GetMapping("/all")
    public ResponseEntity<?> get() {
        List<OrgForm> list = service.get();
        if (!list.isEmpty()) {
            LOGGER.info("OrgForm list obtained {}", String.format("{ \"count\":%d }", list.size()));
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            LOGGER.warn("OrgForm list not obtained { \"count\":0 }");
            return new ResponseEntity<>("OrgForm list is empty.", HttpStatus.OK);
        }
    }

    /**
     * Responsible for providing {@code OrgForm} instance from database.
     * <p>
     * Can be called at URL {@code /OrgForm/{id}}.
     *
     * @param id value of {@code id} field of {@code OrgForm} instance
     * @return {@code OrgForm} instance
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") int id) {
        Optional<OrgForm> optOrgForm = service.get(id);
        if (optOrgForm.isPresent()) {
            LOGGER.info("OrgForm obtained {}", optOrgForm.get().desc());
            return new ResponseEntity<>(optOrgForm, HttpStatus.OK);
        } else {
            LOGGER.info("OrgForm not obtained {}", String.format(" \"id\":\"%s\" ", id));
            return new ResponseEntity<>("There is no OrgForm with such id.", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Responsible for creating and updating {@code OrgForm} instances.
     * <p>
     * Can be called at URL {@code /OrgForm/save}.
     *
     * @param orgForm instance that must be added or updated in database
     * @return added or updated rows amount
     */
    @PutMapping("/save")
    public ResponseEntity<?> save(@RequestBody OrgForm orgForm) {
        Optional<OrgForm> optOrgForm = service.save(orgForm);
        if (optOrgForm.isPresent()) {
            LOGGER.info("OrgForm added {}", orgForm.desc());
            return new ResponseEntity<>(optOrgForm, HttpStatus.OK);
        } else {
            LOGGER.error("OrgForm not added {}", orgForm.desc());
            return new ResponseEntity<>("OrgForm adding/updating was failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responsible for deleting {@code OrgForm} instance from database.
     * <p>
     * Can be called at URL {@code /OrgForm/delete/{id}}
     *
     * @param id value of {@code id} field of {@code OrgForm} instance
     * @return deleted rows amount
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        int result = service.delete(id);
        if (result > 0) {
            LOGGER.info("OrgForm deleted {}", String.format(" \"id\":\"%s\" ", id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            LOGGER.warn("OrgForm not deleted {}", String.format(" \"id\":\"%s\" ", id));
            return new ResponseEntity<>("OrgForm deleting was failed. There is no OrgForm with such ID.", HttpStatus.NOT_FOUND);
        }
    }

}
