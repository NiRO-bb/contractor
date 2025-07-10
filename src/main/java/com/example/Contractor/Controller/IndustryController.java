package com.example.Contractor.Controller;

import com.example.Contractor.DTO.Industry;
import com.example.Contractor.Service.IndustryService;
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
 * Handles incoming http-requests at the URL {@code /industry}
 * <p>
 * Provides access to service layer with some CRUD-methods.
 */
@RestController
@RequestMapping("/industry")
public class IndustryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndustryController.class);

    private final IndustryService service;

    /**
     * Creates a class instance with initialized {@code IndustryService}.
     *
     * @param service service that will handle further requests
     * @see IndustryService
     */
    @Autowired
    public IndustryController(IndustryService service) {
        this.service = service;
    }

    /**
     * Responsible for providing active {@code Industry} instances.
     * <p>
     * Can be called at URL {@code /industry/all}.
     *
     * @return list of active (value of {@code is_active} field = true) {@code Industry} instances
     */
    @GetMapping("/all")
    public ResponseEntity<?> get() {
        List<Industry> list = service.get();
        if (!list.isEmpty()) {
            LOGGER.info("Industry list obtained {}", String.format("{ \"count\":%d }", list.size()));
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            LOGGER.warn("Industry list not obtained { \"count\":0 }");
            return new ResponseEntity<>("Industry list is empty.", HttpStatus.OK);
        }
    }

    /**
     * Responsible for providing {@code Industry} instance from database.
     * <p>
     * Can be called at URL {@code /Industry/{id}}.
     *
     * @param id value of {@code id} field of {@code Industry} instance
     * @return {@code Industry} instance
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") int id) {
        Optional<Industry> optIndustry = service.get(id);
        if (optIndustry.isPresent()) {
            LOGGER.info("Industry obtained {}", optIndustry.get().desc());
            return new ResponseEntity<>(optIndustry, HttpStatus.OK);
        } else {
            LOGGER.info("Industry not obtained {}", String.format(" \"id\":\"%s\" ", id));
            return new ResponseEntity<>("There is no Industry with such id.", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Responsible for creating and updating {@code Industry} instances.
     * <p>
     * Can be called at URL {@code /Industry/save}.
     *
     * @param industry instance that must be added or updated in database
     * @return added or updated rows amount
     */
    @PutMapping("/save")
    public ResponseEntity<?> save(@RequestBody Industry industry) {
        Optional<Industry> optIndustry = service.save(industry);
        if (optIndustry.isPresent()) {
            LOGGER.info("Industry added {}", industry.desc());
            return new ResponseEntity<>(optIndustry, HttpStatus.OK);
        } else {
            LOGGER.error("Industry not added {}", industry.desc());
            return new ResponseEntity<>("Industry adding/updating was failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responsible for deleting {@code Industry} instance from database.
     * <p>
     * Can be called at URL {@code /Industry/delete/{id}}
     *
     * @param id value of {@code id} field of {@code Industry} instance
     * @return deleted rows amount
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        int result = service.delete(id);
        if (result > 0) {
            LOGGER.info("Industry deleted {}", String.format(" \"id\":\"%s\" ", id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            LOGGER.warn("Industry not deleted {}", String.format(" \"id\":\"%s\" ", id));
            return new ResponseEntity<>("Industry deleting was failed. There is no Industry with such ID.", HttpStatus.NOT_FOUND);
        }
    }

}
