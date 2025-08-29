package com.example.Contractor.Service.Cached;

import com.example.Contractor.DTO.Industry;
import com.example.Contractor.Repository.IndustryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caches results of calling all methods.
 */
@Service
@RequiredArgsConstructor
public class IndustryCachedService {

    private final IndustryRepository repository;

    /**
     * Provides list of all active Industry entities.
     * Tries to find industries in cache with key - industries::all.
     * Gets industries from repository if cache don't store value with required key.
     *
     * @return list of active industries
     */
    @Cacheable(value = "industries", key = "'all'")
    public List<Industry> get() {
        return repository.get();
    }

}
