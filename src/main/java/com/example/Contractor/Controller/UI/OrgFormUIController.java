package com.example.Contractor.Controller.UI;

import com.example.Contractor.Controller.OrgFormController;
import com.example.Contractor.DTO.OrgForm;
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
@RequestMapping("/ui/contractor/org_form")
@RequiredArgsConstructor
public class OrgFormUIController {

    private final OrgFormController controller;

    @Secured("USER")
    @GetMapping("/all")
    public ResponseEntity<?> get() {
        return controller.get();
    }

    @Secured("USER")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") int id) {
        return controller.get(id);
    }

    @Secured("CONTRACTOR_SUPERUSER")
    @PutMapping("/save")
    public ResponseEntity<?> save(@RequestBody OrgForm orgForm) {
        return controller.save(orgForm);
    }

    @Secured("CONTRACTOR_SUPERUSER")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        return controller.delete(id);
    }

}
