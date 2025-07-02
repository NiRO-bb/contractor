package com.example.Contractor.Controller;

import com.example.Contractor.DTO.Contractor;
import com.example.Contractor.DTO.ContractorSearch;
import com.example.Contractor.Service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * Handles incoming http-requests at the URL {@code /contractor}
 * <p>
 * Provides access to service layer with some CRUD-methods.
 */
@RestController
@RequestMapping("/contractor")
public class ContractorController {

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
     * @return added or changed (if update) rows amount
     */
    @PutMapping("/save")
    public int save(@RequestBody Contractor contractor) {
        int result = service.save(contractor);
        return result;
    }

    /**
     * Responsible for providing {@code Contractor} instance from database.
     * <p>
     * Can be called at URL {@code /contractor/get/{id}}.
     * Provides all related data (from {@code Country}, {@code Industry} and {@code OrgForm} also).
     *
     * @param id value of {@code id} field of {@code Contractor} instance
     * @return {@code Contractor} instance and related data
     * @see Contractor
     * @see com.example.Contractor.DTO.Country
     * @see com.example.Contractor.DTO.Industry
     * @see com.example.Contractor.DTO.OrgForm
     */
    @GetMapping("/{id}")
    public List<Object> get(@PathVariable String id) {
        List<Object> contractorInfo = service.get(id);
        return contractorInfo;
    }

    /**
     * Responsible for deleting {@code Contractor} instance from database.
     * <p>
     * Can be called at URL {@code /contractor/delete/{id}}.
     *
     * @param id value of {@code id} field of {@link Contractor} instance
     * @return deleted rows amount
     */
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable String id) {
        int result = service.delete(id);
        return result;
    }

    /**
     * Responsible for providing {@code Contractor} instances with sorting and paging.
     * <p>
     * Can be called at URL {@code /contractor/search/{page}}.
     * Receives {@link ContractorSearch} instance as request body.
     *
     * @param contractorSearch contains the sorting fields
     * @param page number of result page that will be returned
     * @return {@link Contractor} instances that match the given condition (in {@code contactorSearch});
     * returned instances count will be no more than page size
     */
    @PostMapping("/search")
    public List<Contractor> search(
            @RequestBody ContractorSearch contractorSearch,
            @RequestParam(defaultValue = "0") int page) {
        List<Contractor> contractors = service.search(contractorSearch, page);
        return contractors;
    }

}
