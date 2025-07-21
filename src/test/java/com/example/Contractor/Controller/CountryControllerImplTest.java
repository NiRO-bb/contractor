package com.example.Contractor.Controller;

import com.example.Contractor.DTO.Country;
import com.example.Contractor.Service.CountryService;
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
public class CountryControllerImplTest {

    private CountryService service = Mockito.mock(CountryService.class);
    private CountryControllerImpl controller = new CountryControllerImpl(service);

    @Test
    public void testGetAllSuccess() {
        Mockito.when(service.get()).thenReturn(Collections.singletonList(new Country()));
        ResponseEntity<?> response = controller.get();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllEmpty() {
        Mockito.when(service.get()).thenReturn(new ArrayList<>());
        ResponseEntity<?> response = controller.get();
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetAllFailure() {
        Mockito.when(service.get()).thenThrow(new RuntimeException("error"));
        Assertions.assertThrows(RuntimeException.class, () -> controller.get());
    }

    @Test
    public void testGetSuccess() {
        Mockito.when(service.get(any(String.class))).thenReturn(Optional.of(new Country()));
        ResponseEntity<?> response = controller.get("test");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetEmpty() {
        Mockito.when(service.get(any(String.class))).thenReturn(Optional.empty());
        ResponseEntity<?> response = controller.get("test");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetFailure() {
        Mockito.when(service.get(any(String.class))).thenThrow(new RuntimeException("error"));
        Assertions.assertThrows(RuntimeException.class, () -> controller.get("test"));
    }

    @Test
    public void testSaveSuccess() {
        Mockito.when(service.save(any(Country.class))).thenReturn(Optional.of(new Country()));
        ResponseEntity<?> response = controller.save(new Country());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSaveEmpty() {
        Mockito.when(service.save(any(Country.class))).thenReturn(Optional.empty());
        ResponseEntity<?> response = controller.save(new Country());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSaveFailure() {
        Mockito.when(service.save(any(Country.class))).thenThrow(new RuntimeException("error"));
        Assertions.assertThrows(RuntimeException.class, () -> controller.save(new Country()));
    }

    @Test
    public void testDeleteSuccess() {
        Mockito.when(service.delete(any(String.class))).thenReturn(1);
        ResponseEntity<?> response = controller.delete("test");
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteZero() {
        Mockito.when(service.delete(any(String.class))).thenReturn(0);
        ResponseEntity<?> response = controller.delete("test");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteFailure() {
        Mockito.when(service.delete(any(String.class))).thenThrow(new RuntimeException("error"));
        Assertions.assertThrows(RuntimeException.class, () -> controller.delete("test"));
    }

}
