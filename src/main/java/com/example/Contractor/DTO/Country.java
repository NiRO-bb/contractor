package com.example.Contractor.DTO;

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

}
