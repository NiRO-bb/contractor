package com.example.Contractor.Service;

import com.example.Contractor.DTO.OrgForm;
import com.example.Contractor.Repository.OrgFormRepository;
import com.example.Contractor.Service.Cached.OrgFormCachedService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Couples controller-layer to repository-layer.
 */
@Service
@RequiredArgsConstructor
public class OrgFormService {

    private final OrgFormCachedService cachedService;

    private final OrgFormRepository repository;

    /**
     * Returns list of all active OrgForm entities from cache.
     *
     * @return list of active (value of {@code is_active} field = true) {@code OrgForm} instances
     */
    public List<OrgForm> get() {
        return cachedService.get();
    }

    /**
     * Returns OrgForm entity from cache with passed 'id' value.
     *
     * @param id value of {@code id} field of {@link OrgForm} instance
     * @return found {@code OrgForm} instance or Optional.empty()
     */
    public Optional<OrgForm> get(int id) {
        return cachedService.get()
                .stream()
                .filter(orgForm -> orgForm.getId() == id)
                .findAny();
    }

    /**
     * Saves OrgForm instance in repository.
     * Also makes cache (with key - org_forms::all) outdated.
     *
     * @param orgForm instance that must be added or updated
     * @return added/updated OrgForm instance or Optional.empty()
     */
    @CacheEvict(value = "org_forms", key = "'all'")
    public Optional<OrgForm> save(OrgForm orgForm) {
        return repository.save(orgForm);
    }

    /**
     * Logically deletes OrgForm entity in repository.
     * Also makes cache (with key - org_forms::all) outdated.
     *
     * @param id value of {@code id} field of {@link OrgForm} instance
     * @return deleted rows amount
     */
    @CacheEvict(value = "org_forms", key = "'all'")
    public int delete(int id) {
        return repository.delete(id);
    }

}
