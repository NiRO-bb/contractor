package com.example.Contractor.Controller;

import com.example.Contractor.DTO.Country;
import com.example.Contractor.Service.CountryService;
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
 * Handles incoming http-requests at the URL {@code /country}
 * <p>
 * Provides access to service layer with some CRUD-methods.
 */
@RestController
@RequestMapping("/country")
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    private final CountryService service;

    /**
     * Creates a class instance with initialized {@code CountryService}.
     *
     * @param service service that will handle further requests
     * @see CountryService
     */
    @Autowired
    public CountryController(CountryService service) {
        this.service = service;
    }

    /**
     * Responsible for providing active {@code Country} instances.
     * <p>
     * Can be called at URL {@code /country/all}.
     *
     * @return list of active (value of {@code is_active} field = true) {@code Country} instances
     */
    @GetMapping("/all")
    public ResponseEntity<?> get() {
        List<Country> list = service.get();
        if (!list.isEmpty()) {
            LOGGER.info("Country list obtained {}", String.format("{ \"count\":%d }", list.size()));
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            LOGGER.warn("Country list not obtained { \"count\":0 }");
            return new ResponseEntity<>("Country list is empty.", HttpStatus.OK);
        }
    }

    /**
     * Responsible for providing {@code Country} instance from database.
     * <p>
     * Can be called at URL {@code /country/{id}}.
     *
     * @param id value of {@code id} field of {@code Country} instance
     * @return {@code Country} instance
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        Optional<Country> optCountry = service.get(id);
        if (optCountry.isPresent()) {
            LOGGER.info("Country obtained {}", optCountry.get().desc());
            return new ResponseEntity<>(optCountry, HttpStatus.OK);
        } else {
            LOGGER.info("Country not obtained {}", String.format(" \"id\":\"%s\" ", id));
            return new ResponseEntity<>("There is no country with such id.", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Responsible for creating and updating {@code Country} instances.
     * <p>
     * Can be called at URL {@code /country/save}.
     *
     * @param country instance that must be added or updated in database
     * @return added or updated rows amount
     */
    @PutMapping("/save")
    public ResponseEntity<?> save(@RequestBody Country country) {
        Optional<Country> optCountry = service.save(country);
        if (optCountry.isPresent()) {
            LOGGER.info("Country added {}", country.desc());
            return new ResponseEntity<>(optCountry, HttpStatus.OK);
        } else {
            LOGGER.error("Country not added {}", country.desc());
            return new ResponseEntity<>("Country adding/updating was failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responsible for deleting {@code Country} instance from database.
     * <p>
     * Can be called at URL {@code /country/delete/{id}}
     *
     * @param id value of {@code id} field of {@code Country} instance
     * @return deleted rows amount
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        int result = service.delete(id);
        if (result > 0) {
            LOGGER.info("Country deleted {}", String.format(" \"id\":\"%s\" ", id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            LOGGER.warn("Country not deleted {}", String.format(" \"id\":\"%s\" ", id));
            return new ResponseEntity<>("Country deleting was failed. There is no country with such ID.", HttpStatus.NOT_FOUND);
        }
    }

}
