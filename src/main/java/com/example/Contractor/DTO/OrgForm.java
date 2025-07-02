package com.example.Contractor.DTO;

/**
 * Represents 'org_form' entity from database.
 */
public class OrgForm {

    private int id;
    private String name;
    private boolean isActive;

    /**
     * Creates full initialized instance.
     *
     * @param id
     * @param name
     * @param isActive
     */
    public OrgForm(int id, String name, boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

}
