package com.example.Contractor.Controller;

import com.example.Contractor.DTO.Country;
import com.example.Contractor.DTO.Industry;
import com.example.Contractor.Service.IndustryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
 * Handles incoming http-requests related with Industry.
 * <p>
 * Provides access to service layer with some CRUD-methods.
 */
@RestController
@RequestMapping("/contractor/industry")
@RequiredArgsConstructor
public class IndustryControllerImpl implements IndustryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndustryControllerImpl.class);

    private final IndustryService service;

    /**
     * Responsible for providing active {@code Industry} instances.
     * <p>
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @return list of active (value of {@code is_active} field = true) {@code Industry} instances and http OK status
     */
    @Operation(summary = "Retrieve all Industries", description = "Retrieves all active Industry entities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Industry list received",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Country.class)))),
            @ApiResponse(responseCode = "204", description = "Industry list not received - there are no matched entities"),
            @ApiResponse(responseCode = "500", description = "Industry list receiving was failing",
                    content = @Content(schema = @Schema(type = "string", example = "error message")))
    })
    @GetMapping("/all")
    @Override
    public ResponseEntity<?> get() {
        try {
            List<Industry> list = service.get();
            if (!list.isEmpty()) {
                LOGGER.info("Industry list obtained {}", String.format("{ \"count\":%d }", list.size()));
                return new ResponseEntity<>(list, HttpStatus.OK);
            } else {
                LOGGER.warn("Industry list not obtained { \"count\":0 }");
                return new ResponseEntity<>("Industry list is empty.", HttpStatus.NO_CONTENT);
            }
        } catch (Exception exception) {
            LOGGER.error("Industry list obtaining was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("Industry list obtaining was failed");
        }
    }

    /**
     * Responsible for providing {@code Industry} instance from database.
     * <p>
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @param id value of {@code id} field of {@code Industry} instance
     * @return {@code Industry} instance and OK status;
     * NOT_FOUND - there is no matched Industry entities
     */
    @Operation(summary = "Retrieve Industry", description = "Retrieves Industry by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Industry received",
                    content = @Content(schema = @Schema(implementation = Country.class))),
            @ApiResponse(responseCode = "404", description = "Industry not received - invalid data",
                    content = @Content(schema = @Schema(type = "string", example = "error message"))),
            @ApiResponse(responseCode = "500", description = "Industry receiving was failing",
                    content = @Content(schema = @Schema(type = "string", example = "error message")))
    })
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> get(@PathVariable("id") int id) {
        try {
            Optional<Industry> optIndustry = service.get(id);
            if (optIndustry.isPresent()) {
                LOGGER.info("Industry obtained {}", optIndustry.get().desc());
                return new ResponseEntity<>(optIndustry, HttpStatus.OK);
            } else {
                LOGGER.info("Industry not obtained {}; There is no Industry with such id", String.format(" \"id\":\"%s\" ", id));
                return new ResponseEntity<>("There is no Industry with such id.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            LOGGER.error("Industry obtaining was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("Industry obtaining was failed");
        }
    }

    /**
     * Responsible for creating and updating {@code Industry} instances.
     * <p>
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @param industry instance that must be added or updated in database
     * @return added/updated Industry entity and OK status; INTERNAL_SERVER_ERROR if something goes wrong
     */
    @Operation(summary = "Add/update Industry",
            description = "Adds or updates (depends on passed 'id' value) Industry entity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Industry saved",
                    content = @Content(schema = @Schema(implementation = Country.class))),
            @ApiResponse(responseCode = "500", description = "Industry not saved",
                    content = @Content(schema = @Schema(type = "string", example = "error message")))
    })
    @PutMapping("/save")
    @Override
    public ResponseEntity<?> save(@RequestBody Industry industry) {
        try {
            Optional<Industry> optIndustry = service.save(industry);
            if (optIndustry.isPresent()) {
                LOGGER.info("Industry added {}", industry.desc());
                return new ResponseEntity<>(optIndustry, HttpStatus.OK);
            } else {
                LOGGER.error("Industry not added {}", industry.desc());
                return new ResponseEntity<>("Industry adding/updating was failed.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception exception) {
            LOGGER.error("Industry adding was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("Industry adding/updating was failed");
        }
    }

    /**
     * Responsible for deleting {@code Industry} instance from database.
     * <p>
     * Throw RuntimeException if something goes wrong -
     * it is assumed that it will be catched by global exception handler.
     *
     * @param id value of {@code id} field of {@code Industry} instance
     * @return NO_CONTENT - success, NOT_FOUND - there is no matched Industry entities
     */
    @Operation(summary = "Delete Industry", description = "Logically deletes Industry entity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Industry deleted"),
            @ApiResponse(responseCode = "404", description = "Industry not deleted - invalid data",
                    content = @Content(schema = @Schema(type = "string", example = "error message"))),
            @ApiResponse(responseCode = "500", description = "Industry deleting was failed",
                    content = @Content(schema = @Schema(type = "string", example = "error message")))
    })
    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        try {
            if (service.delete(id) > 0) {
                LOGGER.info("Industry deleted {}", String.format(" \"id\":\"%s\" ", id));
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                LOGGER.warn("Industry not deleted {}; There is no Industry with such id", String.format(" \"id\":\"%s\" ", id));
                return new ResponseEntity<>("Industry deleting was failed. There is no Industry with such ID.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            LOGGER.error("Industry deleting was failed; Error occurred {}", exception.getMessage());
            throw new RuntimeException("Industry deleting was failed");
        }
    }

}
