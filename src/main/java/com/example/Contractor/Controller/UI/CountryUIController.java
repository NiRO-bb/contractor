package com.example.Contractor.Controller.UI;

import com.example.Contractor.Controller.CountryController;
import com.example.Contractor.DTO.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ui/contractor/country")
@RequiredArgsConstructor
public class CountryUIController {

    private final CountryController controller;

    @Secured("USER")
    @GetMapping("/all")
    public ResponseEntity<?> get() {
        return controller.get();
    }

    @Secured("USER")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        return controller.get(id);
    }

    @Secured("CONTRACTOR_SUPERUSER")
    @PutMapping("/save")
    public ResponseEntity<?> save(@RequestBody Country country) {
        return controller.save(country);
    }

    @Secured("CONTRACTOR_SUPERUSER")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return controller.delete(id);
    }

}
