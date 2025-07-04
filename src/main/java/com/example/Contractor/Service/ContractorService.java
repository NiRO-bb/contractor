package com.example.Contractor.Service;

import com.example.Contractor.DTO.Contractor;
import com.example.Contractor.DTO.ContractorSearch;
import com.example.Contractor.Repository.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Couples controller-layer to repository-layer.
 */
@Service
public class ContractorService {

    private final ContractorRepository repository;

    /**
     * Creates instance with initialized {@code ContractorRepository}.
     *
     * @param repository repository that will handle requests
     * @see ContractorRepository
     */
    @Autowired
    public ContractorService(ContractorRepository repository) {
        this.repository = repository;
    }

    /**
     * Provides access to save method of repository-layer.
     *
     * @param contractor instance that must be added or updated
     * @return added or updated rows amount
     * @see Contractor
     */
    public int save(Contractor contractor) {
        return repository.save(contractor);
    }

    /**
     * Provides access to get method of repository-layer.
     *
     * @param id value of {@code id} field of {@link Contractor} instance
     * @return {@code Contractor} instance and related data
     */
    public List<Object> get(String id) {
        return repository.get(id);
    }

    /**
     * Provides access to delete method of repository-layer.
     *
     * @param id value of {@code id} field of {@link Contractor} instance
     * @return deleted rows count
     */
    public int delete(String id) {
        return repository.delete(id);
    }

    /**
     * Provides access to search method of repository-layer.
     *
     * @param contractorSearch sorting fields
     * @param page number of result page that will be returned
     * @return {@link Contractor} instances that match the given condition (in {@code contactorSearch} param);
     * returned instances count will be no more than page size
     */
    public List<Contractor> search(ContractorSearch contractorSearch, int page) {
        return repository.search(contractorSearch, page);
    }

}
