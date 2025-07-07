package com.example.Contractor;

import com.example.Contractor.Controller.ContractorController;
import com.example.Contractor.DTO.Contractor;
import com.example.Contractor.DTO.ContractorSearch;
import com.example.Contractor.Service.ContractorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

//@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(MockitoExtension.class)
public class ContractorControllerTest {

    private ContractorService service = Mockito.mock(ContractorService.class);
    private ContractorController controller = new ContractorController(service);

    @Test
    public void testSaveSuccess() {
        Mockito.when(service.save(any(Contractor.class))).thenReturn(Optional.of(new Contractor()));
        ResponseEntity<?> response = controller.save(new Contractor());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSaveFailure() {
        Mockito.when(service.save(any(Contractor.class))).thenReturn(Optional.empty());
        ResponseEntity<?> response = controller.save(new Contractor());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetSuccess() {
        Mockito.when(service.get(any(String.class))).thenReturn(Optional.of(new ArrayList<>()));
        ResponseEntity<?> response = controller.get("test");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetFailure() {
        Mockito.when(service.get(any(String.class))).thenReturn(Optional.empty());
        ResponseEntity<?> response = controller.get("test");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteSuccess() {
        Mockito.when(service.delete(any(String.class))).thenReturn(1);
        ResponseEntity<?> response = controller.delete("test");
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteFailure() {
        Mockito.when(service.delete(any(String.class))).thenReturn(0);
        ResponseEntity<?> response = controller.delete("test");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchSuccess() {
        List<Contractor> list = new ArrayList<>();
        list.add(new Contractor());
        Mockito.when(service.search(any(ContractorSearch.class), eq(0), eq(0))).thenReturn(list);
        ResponseEntity<?> response = controller.search(new ContractorSearch(), 0, 0);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSearchFailure() {
        Mockito.when(service.search(any(ContractorSearch.class), eq(0), eq(0))).thenReturn(new ArrayList<>());
        ResponseEntity<?> response = controller.search(new ContractorSearch(), 0, 0);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
