package com.example.Contractor.outbox;

import com.example.Contractor.AbstractContainer;
import com.example.Contractor.Exception.RabbitSendingException;
import com.example.Contractor.rabbitMQ.RabbitProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collections;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class ScheduleMessageSenderTest extends AbstractContainer {

    @Autowired
    private ScheduleMessageSender sender;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @MockitoBean
    private RabbitProducer producer;

    @BeforeAll
    public static void setup(@Autowired NamedParameterJdbcTemplate jdbcTemplate) {
        String sql = """
                INSERT INTO outbox
                SELECT :id, 'test';
                """;
        jdbcTemplate.update(sql, Collections.singletonMap("id", "default_1"));
        jdbcTemplate.update(sql, Collections.singletonMap("id", "default_2"));
    }

    @Test
    public void testTransactionalSuccess() throws Exception {
        Mockito.when(producer.send(anyString())).thenReturn(true);
        int beforeTransaction = outboxCount();
        sender.pollAndSend();
        Assertions.assertEquals(beforeTransaction - 1, outboxCount());
    }

    @Test
    public void testTransactionalFailure() throws Exception {
        Mockito.when(producer.send(anyString())).thenReturn(false);
        int beforeTransaction = outboxCount();
        Assertions.assertThrows(RabbitSendingException.class,
                () -> sender.pollAndSend());
        Assertions.assertEquals(beforeTransaction, outboxCount());
    }

    private int outboxCount() {
        String sql = """
                SELECT COUNT(*)
                FROM outbox;
                """;
        return jdbcTemplate.queryForObject(sql, new HashMap<>(), Integer.class);
    }

}
