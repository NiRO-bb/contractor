package com.example.Contractor.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents 'industry' entity from database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Industry {

    private int id;
    private String name;
    private boolean isActive;

}
