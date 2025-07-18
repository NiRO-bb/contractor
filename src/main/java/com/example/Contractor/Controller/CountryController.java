package com.example.Contractor.Controller;

import com.example.Contractor.DTO.Country;
import com.example.Contractor.Service.CountryService;
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
 * Handles incoming http-requests related with Country.
 * <p>
 * Provides access to service layer with some CRUD-methods.
 */
@RestController
@RequestMapping("/contractor/country")
@RequiredArgsConstructor
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    private final CountryService service;

    /**
     * Responsible for providing active {@code Country} instances.
     * <p>
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @return list of active (value of {@code is_active} field = true) {@code Country} instances and http OK status
     */
    @GetMapping("/all")
    public ResponseEntity<?> get() {
        try {
            List<Country> countries = service.get();
            if (!countries.isEmpty()) {
                LOGGER.info("Country list obtained {}", String.format("{ \"count\":%d }", countries.size()));
                return new ResponseEntity<>(countries, HttpStatus.OK);
            } else {
                LOGGER.warn("Country list not obtained { \"count\":0 }");
                return new ResponseEntity<>("Country list is empty.", HttpStatus.OK);
            }
        } catch (Exception exception) {
            LOGGER.error("Country list obtaining was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("Country list obtaining was failed.");
        }
    }

    /**
     * Responsible for providing {@code Country} instance from database.
     * <p>
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @param id value of {@code id} field of {@code Country} instance
     * @return {@code Country} instance and http OK status; NOT_FOUND - there is no matched country
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        try {
            Optional<Country> optCountry = service.get(id);
            if (optCountry.isPresent()) {
                LOGGER.info("Country obtained {}", optCountry.get().desc());
                return new ResponseEntity<>(optCountry, HttpStatus.OK);
            } else {
                LOGGER.info("Country not obtained {}; There is no country with such id", String.format(" \"id\":\"%s\" ", id));
                return new ResponseEntity<>("There is no country with such id.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            LOGGER.error("Country obtaining was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("Country obtaining was failed.");
        }
    }

    /**
     * Responsible for creating and updating {@code Country} instances.
     * <p>
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @param country instance that must be added or updated in database
     * @return added/updated Country entity and http OK status; INTERNAL_SERVER_ERROR if something goes wrong
     */
    @PutMapping("/save")
    public ResponseEntity<?> save(@RequestBody Country country) {
        try {
            Optional<Country> optCountry = service.save(country);
            if (optCountry.isPresent()) {
                LOGGER.info("Country added {}", country.desc());
                return new ResponseEntity<>(optCountry, HttpStatus.OK);
            } else {
                LOGGER.error("Country not added {}", country.desc());
                return new ResponseEntity<>("Country adding/updating was failed.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception exception) {
            LOGGER.error("Country adding was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("Country adding/updating was failed.");
        }
    }

    /**
     * Responsible for deleting {@code Country} instance from database.
     * <p>
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @param id value of {@code id} field of {@code Country} instance
     * @return NO_CONTENT - success, NOT_FOUND - there is no matched Country entities
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            if (service.delete(id) > 0) {
                LOGGER.info("Country deleted {}", String.format(" \"id\":\"%s\" ", id));
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                LOGGER.warn("Country not deleted {}; There is no country with such id", String.format(" \"id\":\"%s\" ", id));
                return new ResponseEntity<>("Country deleting was failed. There is no country with such ID.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            LOGGER.error("Country deleting was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("Country deleting was failed.");
        }
    }

}
