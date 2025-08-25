package com.example.Contractor.Service;

import com.example.Contractor.DTO.Country;
import com.example.Contractor.Repository.CountryRepository;
import com.example.Contractor.Service.Cached.CountryCachedService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Couples controller-layer to repository-layer.
 */
@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryCachedService cachedService;

    private final CountryRepository repository;

    /**
     * Returns list of all active Country entities from cache.
     *
     * @return list of active (value of {@code is_active} field = true) {@code Country} instances
     */
    public List<Country> get() {
        return cachedService.get();
    }

    /**
     * Returns Country entity from cache with passed 'id' value.
     *
     * @param id value of {@code id} field of {@link Country} instance
     * @return found {@code Country} instance or Optional.empty()
     */
    public Optional<Country> get(String id) {
        return cachedService.get()
                .stream()
                .filter(country -> country.getId().equals(id))
                .findAny();
    }

    /**
     * Saves Country instance in repository.
     * Also makes cache (with key - countries::all) outdated.
     *
     * @param country instance that must be added or updated
     * @return added/updated Country instance or Optional.empty()
     */
    @CacheEvict(value = "countries", key = "'all'")
    public Optional<Country> save(Country country) {
        return repository.save(country);
    }

    /**
     * Logically deletes Country entity in repository.
     * Also makes cache (with key - countries::all) outdated.
     *
     * @param id value of {@code id} field of {@link Country} instance
     * @return deleted rows amount
     */
    @CacheEvict(value = "countries", key = "'all'")
    public int delete(String id) {
        return repository.delete(id);
    }

}
