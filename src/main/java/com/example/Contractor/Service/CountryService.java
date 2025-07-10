package com.example.Contractor.Service;

import com.example.Contractor.DTO.Country;
import com.example.Contractor.Repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Couples controller-layer to repository-layer.
 */
@Service
public class CountryService {

    private final CountryRepository repository;

    /**
     * Creates instance with initialized {@code CountryRepository}.
     *
     * @param repository repository that will handle requests
     * @see CountryRepository
     */
    @Autowired
    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    /**
     * Provides access to get-all method of repository-layer.
     *
     * @return list of active (value of {@code is_active} field = true) {@code Country} instances
     */
    public List<Country> get() {
        return repository.get();
    }

    /**
     * Provides access to get method of repository-layer.
     *
     * @param id value of {@code id} field of {@link Country} instance
     * @return found {@code Country} instance or Optional.empty()
     */
    public Optional<Country> get(String id) {
        return repository.get(id);
    }

    /**
     * Provides access to save method of repository-layer.
     *
     * @param country instance that must be added or updated
     * @return added/updated Country instance or Optional.empty()
     */
    public Optional<Country> save(Country country) {
        return repository.save(country);
    }

    /**
     * Provides access to delete method of repository-layer.
     *
     * @param id value of {@code id} field of {@link Country} instance
     * @return deleted rows amount
     */
    public int delete(String id) {
        return repository.delete(id);
    }

}
