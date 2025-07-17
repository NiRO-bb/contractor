package com.example.Contractor;

import com.example.Contractor.Controller.OrgFormController;
import com.example.Contractor.DTO.OrgForm;
import com.example.Contractor.Service.OrgFormService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class OrgFormControllerTest {

    private OrgFormService service = Mockito.mock(OrgFormService.class);
    private OrgFormController controller = new OrgFormController(service);

    @Test
    public void testGetAllSuccess() {
        Mockito.when(service.get()).thenReturn(Collections.singletonList(new OrgForm()));
        ResponseEntity<?> response = controller.get();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllEmpty() {
        Mockito.when(service.get()).thenReturn(new ArrayList<>());
        ResponseEntity<?> response = controller.get();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllFailure() {
        Mockito.when(service.get()).thenThrow(new RuntimeException("error"));
        Assertions.assertThrows(RuntimeException.class, () -> controller.get());
    }

    @Test
    public void testGetSuccess() {
        Mockito.when(service.get(any(Integer.class))).thenReturn(Optional.of(new OrgForm()));
        ResponseEntity<?> response = controller.get(0);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetEmpty() {
        Mockito.when(service.get(any(Integer.class))).thenReturn(Optional.empty());
        ResponseEntity<?> response = controller.get(0);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetFailure() {
        Mockito.when(service.get(any(Integer.class))).thenThrow(new RuntimeException("error"));
        Assertions.assertThrows(RuntimeException.class, () -> controller.get(0));
    }

    @Test
    public void testSaveSuccess() {
        Mockito.when(service.save(any(OrgForm.class))).thenReturn(Optional.of(new OrgForm()));
        ResponseEntity<?> response = controller.save(new OrgForm());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSaveEmpty() {
        Mockito.when(service.save(any(OrgForm.class))).thenReturn(Optional.empty());
        ResponseEntity<?> response = controller.save(new OrgForm());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSaveFailure() {
        Mockito.when(service.save(any(OrgForm.class))).thenThrow(new RuntimeException("error"));
        Assertions.assertThrows(RuntimeException.class, () -> controller.save(new OrgForm()));
    }

    @Test
    public void testDeleteSuccess() {
        Mockito.when(service.delete(any(Integer.class))).thenReturn(1);
        ResponseEntity<?> response = controller.delete(0);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteZero() {
        Mockito.when(service.delete(any(Integer.class))).thenReturn(0);
        ResponseEntity<?> response = controller.delete(0);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteFailure() {
        Mockito.when(service.delete(any(Integer.class))).thenThrow(new RuntimeException("error"));
        Assertions.assertThrows(RuntimeException.class, () -> controller.delete(0));
    }

}
