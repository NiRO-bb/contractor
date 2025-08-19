package com.example.Contractor.Service;

import com.example.Contractor.DTO.Contractor;
import com.example.Contractor.DTO.ContractorSearch;
import com.example.Contractor.DTO.OutboxMessage;
import com.example.Contractor.Exception.SerializingException;
import com.example.Contractor.Repository.ContractorRepository;
import com.example.Contractor.Utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Couples controller-layer to repository-layer.
 */
@Service
@RequiredArgsConstructor
public class ContractorService {

    private final Logger logger = LogManager.getLogger();

    private final ContractorRepository repository;

    private final OutboxService outboxService;

    /**
     * Saves Contractor instance in database.
     * Also saves message in outbox table to send to RabbitMQ.
     *
     * @param contractor instance that must be added or updated
     * @return added or updated {@link Contractor} instance
     */
    @Transactional
    public Optional<Contractor> save(Contractor contractor) {
        outboxService.save(createMessage(contractor));
        return repository.save(contractor);
    }

    /**
     * Provides access to get method of repository-layer.
     *
     * @param id value of {@code id} field of {@link Contractor} instance
     * @return {@code Contractor} instance and related data
     */
    public Optional<List<Object>> get(String id) {
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
     * @param size amount of returned {@link Contractor} entities
     * @return {@code Contractor} instances that match the given condition (in {@code contactorSearch} param);
     * returned instances count will be no more than page size
     */
    public List<Contractor> search(ContractorSearch contractorSearch, int page, int size) {
        return repository.search(contractorSearch, page, size);
    }

    /**
     * Creates OutboxMessage instance from passed Contractor instance.
     *
     * @param contractor instance must be cast to OutboxMessage
     * @return created instance
     */
    private OutboxMessage createMessage(Contractor contractor) {
        try {
            String payload = new String(JsonUtil.serialize(contractor));
            return new OutboxMessage(contractor.getId(), payload);
        } catch (JsonProcessingException exception) {
            logger.error("Contractor instance not serialized to JSON - {}",
                    exception.getMessage());
            throw new SerializingException(exception.getMessage());
        }
    }

}
