package com.example.Contractor.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Represents Contractor entity from database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contractor {

    private String id;
    private String parentId;
    private String name;
    private String nameFull;
    private String inn;
    private String ogrn;
    private String country;
    private int industry;
    private int orgForm;
    private Date createDate;
    private Date modifyDate;
    private String createUserId;
    private String modifyUserId;
    private boolean isActive;

    public String desc() {
        return String.format("{ \"id\":\"%s\", \"name\":\"%s\" }", id, name);
    }

}
