package com.example.Contractor.DTO;

/**
 * Represents 'country' entity from database.
 */
public class Country {

    private String id;
    private String name;
    private boolean isActive;

    /**
     * Creates full initialized instance.
     *
     * @param id
     * @param name
     * @param isActive
     */
    public Country(String id, String name, boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

}
