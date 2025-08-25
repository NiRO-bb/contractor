package com.example.Contractor.Service.Cached;

import com.example.Contractor.DTO.Country;
import com.example.Contractor.Repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caches results of calling all methods.
 */
@Service
@RequiredArgsConstructor
public class CountryCachedService {

    private final CountryRepository repository;

    /**
     * Provides list of all active Country entities.
     * Tries to find countries in cache with key - countries::all.
     * Gets countries from repository if cache don't store value with required key.
     *
     * @return list of active countries
     */
    @Cacheable(value = "countries", key = "'all'")
    public List<Country> get() {
        return repository.get();
    }

}
