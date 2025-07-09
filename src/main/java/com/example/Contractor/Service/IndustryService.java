package com.example.Contractor.Service;

import com.example.Contractor.DTO.Industry;
import com.example.Contractor.Repository.IndustryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Couples controller-layer to repository-layer.
 */
@Service
public class IndustryService {

    private final IndustryRepository repository;

    /**
     * Creates instance with initialized {@code IndustryRepository}.
     *
     * @param repository repository that will handle requests
     * @see IndustryRepository
     */
    @Autowired
    public IndustryService(IndustryRepository repository) {
        this.repository = repository;
    }

    /**
     * Provides access to get-all method of repository-layer.
     *
     * @return list of active (value of {@code is_active} field = true) {@code Industry} instances
     */
    public List<Industry> get() {
        return repository.get();
    }

    /**
     * Provides access to get method of repository-layer.
     *
     * @param id value of {@code id} field of {@link Industry} instance
     * @return found {@code Industry} instance or Optional.empty()
     */
    public Optional<Industry> get(int id) {
        return repository.get(id);
    }

    /**
     * Provides access to save method of repository-layer.
     *
     * @param industry instance that must be added or updated
     * @return added/updated Industry instance or Optional.empty()
     */
    public Optional<Industry> save(Industry industry) {
        return repository.save(industry);
    }

    /**
     * Provides access to delete method of repository-layer.
     *
     * @param id value of {@code id} field of {@link Industry} instance
     * @return deleted rows amount
     */
    public int delete(int id) {
        return repository.delete(id);
    }

}
