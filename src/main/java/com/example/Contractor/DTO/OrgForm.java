package com.example.Contractor.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents 'org_form' entity from database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrgForm {

    private int id;
    private String name;
    private boolean isActive;

    public String desc() {
        return String.format("{ \"id\":\"%s\", \"name\":\"%s\" }", id, name);
    }

}
