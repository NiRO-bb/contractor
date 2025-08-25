package com.example.Contractor.Service;

import com.example.Contractor.DTO.Industry;
import com.example.Contractor.Repository.IndustryRepository;
import com.example.Contractor.Service.Cached.IndustryCachedService;
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
public class IndustryService {

    private final IndustryCachedService cachedService;

    private final IndustryRepository repository;

    /**
     * Returns list of all active Industry entities from cache.
     *
     * @return list of active (value of {@code is_active} field = true) {@code Industry} instances
     */
    public List<Industry> get() {
        return cachedService.get();
    }

    /**
     * Returns Industry entity from cache with passed 'id' value.
     *
     * @param id value of {@code id} field of {@link Industry} instance
     * @return found {@code Industry} instance or Optional.empty()
     */
    public Optional<Industry> get(int id) {
        return cachedService.get()
                .stream()
                .filter(industry -> industry.getId() == id)
                .findAny();
    }

    /**
     * Saves Industry instance in repository.
     * Also makes cache (with key - industries::all) outdated.
     *
     * @param industry instance that must be added or updated
     * @return added/updated Industry instance or Optional.empty()
     */
    @CacheEvict(value = "industries", key = "'all'")
    public Optional<Industry> save(Industry industry) {
        return repository.save(industry);
    }

    /**
     * Logically deletes Industry entity in repository.
     * Also makes cache (with key - industries::all) outdated.
     *
     * @param id value of {@code id} field of {@link Industry} instance
     * @return deleted rows amount
     */
    @CacheEvict(value = "industries", key = "'all'")
    public int delete(int id) {
        return repository.delete(id);
    }

}
