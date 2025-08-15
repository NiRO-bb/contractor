package com.example.Contractor.service;

import com.example.Contractor.ContextSetup;
import com.example.Contractor.DTO.Contractor;
import com.example.Contractor.Repository.ContractorRepository;
import com.example.Contractor.Service.ContractorService;
import com.example.Contractor.Service.OutboxService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@SpringBootTest
@ExtendWith(ContextSetup.class)
public class ContractorServiceTest {

    @Autowired
    private ContractorService service;

    @Autowired
    private OutboxService outboxService;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    public void testTransactionalSuccess() {
        Contractor contractor = getTestContractor();
        int beforeTransaction = outboxCount();
        Optional<Contractor> optContractor = service.save(contractor);
        Assertions.assertEquals(contractor.getId(),
                optContractor.get().getId());
        Assertions.assertEquals(beforeTransaction + 1, outboxCount());
    }

    @Test
    public void testTransactionalFailure() {
        ContractorRepository mockRepo = Mockito.mock(ContractorRepository.class);
        service = new ContractorService(
                mockRepo,
                outboxService
        );
        Mockito.when(mockRepo.save(Mockito.any(Contractor.class)))
                .thenThrow(new DataAccessException("test") {});
        Contractor contractor = getTestContractor();
        int beforeTransaction = outboxCount();
        Assertions.assertThrows(DataAccessException.class,
                () -> service.save(contractor));
        Assertions.assertEquals(beforeTransaction, outboxCount());
    }

    private int outboxCount() {
        String sql = """
                SELECT COUNT(*)
                FROM outbox;
                """;
        return jdbcTemplate.queryForObject(sql, new HashMap<>(), Integer.class);
    }

    private Contractor getTestContractor() {
        return new Contractor(
                "test",
                null,
                "test",
                "test",
                "test",
                "test",
                "ABH",
                1,
                1,
                new Date(),
                new Date(),
                "test",
                "test",
                true
        );
    }

}
