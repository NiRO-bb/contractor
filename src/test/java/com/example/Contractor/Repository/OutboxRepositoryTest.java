package com.example.Contractor.Repository;

import com.example.Contractor.ContextSetup;
import com.example.Contractor.DTO.OutboxMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;
import java.util.Optional;

@SpringBootTest
@ExtendWith(ContextSetup.class)
public class OutboxRepositoryTest {

    @Autowired
    private OutboxRepository repository;

    @BeforeAll
    public static void setup(@Autowired NamedParameterJdbcTemplate jdbcTemplate) {
        String sql = """
                INSERT INTO outbox
                SELECT :id, 'test';
                """;
        jdbcTemplate.update(sql, Collections.singletonMap("id", "default"));
        jdbcTemplate.update(sql, Collections.singletonMap("id", "delete"));
    }

    @Test
    public void testSave() {
        OutboxMessage message = getTestMessage();
        Optional<OutboxMessage> optMessage =  repository.save(message);
        Assertions.assertEquals("test", optMessage.get().getId());
    }

    @Test
    public void testGetFirst() {
        Optional<OutboxMessage> optMessage = repository.getFirst();
        Assertions.assertEquals("test", optMessage.get().getPayload());
    }

    @Test
    public void testDeleteById() {
        int deleted = repository.deleteById("delete");
        Assertions.assertEquals(1, deleted);
    }

    private OutboxMessage getTestMessage() {
        return new OutboxMessage("test", "test");
    }

}
