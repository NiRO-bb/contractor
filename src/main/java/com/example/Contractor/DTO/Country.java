package com.example.Contractor.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents 'country' entity from database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    private String id;
    private String name;


    private boolean isActive;

    public String desc() {
        return String.format("{ \"id\":\"%s\", \"name\":\"%s\" }", id, name);
    }

}
