package com.example.Contractor.Service;

import com.example.Contractor.DTO.OutboxMessage;
import com.example.Contractor.Repository.OutboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Provides means to save and poll OutboxMessage instances.
 */
@Service
public class OutboxService {

    @Autowired
    private OutboxRepository repository;

    /**
     * Saves passed OutboxMessage instance.
     *
     * @param message message must be saved
     * @return result of saving
     */
    public Optional<OutboxMessage> save(OutboxMessage message) {
        return repository.save(message);
    }

    /**
     * Retrieves first OutboxMessage entity from database table and deletes it.
     *
     * @return retrieved OutboxMessage instance
     */
    public Optional<OutboxMessage> poll() {
        Optional<OutboxMessage> optMessage = getFirst();
        if (optMessage.isPresent()) {
            deleteById(optMessage.get().getId());
        }
        return optMessage;
    }

    /**
     * Retrieves first OutboxMessage entity from database table.
     *
     * @return
     */
    public Optional<OutboxMessage> getFirst() {
        return repository.getFirst();
    }

    /**
     * Deletes OutboxMessage entity with passed id value.
     *
     * @param id
     * @return deleted entities amount
     */
    public int deleteById(String id) {
        return repository.deleteById(id);
    }

}
