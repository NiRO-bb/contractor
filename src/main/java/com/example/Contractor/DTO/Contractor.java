package com.example.Contractor.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Date createDate;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Date modifyDate;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String createUserId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String modifyUserId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private boolean isActive;

    public String desc() {
        return String.format("{ \"id\":\"%s\", \"name\":\"%s\" }", id, name);
    }

}
