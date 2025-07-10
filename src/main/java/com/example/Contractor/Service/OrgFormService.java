package com.example.Contractor.Service;

import com.example.Contractor.DTO.OrgForm;
import com.example.Contractor.Repository.OrgFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Couples controller-layer to repository-layer.
 */
@Service
public class OrgFormService {

    private final OrgFormRepository repository;

    /**
     * Creates instance with initialized {@code OrgFormRepository}.
     *
     * @param repository repository that will handle requests
     * @see OrgFormRepository
     */
    @Autowired
    public OrgFormService(OrgFormRepository repository) {
        this.repository = repository;
    }

    /**
     * Provides access to get-all method of repository-layer.
     *
     * @return list of active (value of {@code is_active} field = true) {@code OrgForm} instances
     */
    public List<OrgForm> get() {
        return repository.get();
    }

    /**
     * Provides access to get method of repository-layer.
     *
     * @param id value of {@code id} field of {@link OrgForm} instance
     * @return found {@code OrgForm} instance or Optional.empty()
     */
    public Optional<OrgForm> get(int id) {
        return repository.get(id);
    }

    /**
     * Provides access to save method of repository-layer.
     *
     * @param orgForm instance that must be added or updated
     * @return added/updated OrgForm instance or Optional.empty()
     */
    public Optional<OrgForm> save(OrgForm orgForm) {
        return repository.save(orgForm);
    }

    /**
     * Provides access to delete method of repository-layer.
     *
     * @param id value of {@code id} field of {@link OrgForm} instance
     * @return deleted rows amount
     */
    public int delete(int id) {
        return repository.delete(id);
    }

}
