package com.example.Contractor.DTO;

import java.util.Date;

/**
 * Represents Contractor entity from database.
 */
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

    /**
     * Creates instance with main fields only.
     *
     * @param id
     * @param parentId
     * @param name
     * @param nameFull
     * @param inn
     * @param ogrn
     * @param country
     * @param industry
     * @param orgForm
     */
    public Contractor(String id, String parentId, String name, String nameFull, String inn,
                      String ogrn, String country, int industry, int orgForm) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.nameFull = nameFull;
        this.inn = inn;
        this.ogrn = ogrn;
        this.country = country;
        this.industry = industry;
        this.orgForm = orgForm;
    }

    public Contractor() {}

    /**
     * Creates full initialized instance.
     *
     * @param id
     * @param parentId
     * @param name
     * @param nameFull
     * @param inn
     * @param ogrn
     * @param country
     * @param industry
     * @param orgForm
     * @param createDate
     * @param modifyDate
     * @param createUserId
     * @param modifyUserId
     * @param isActive
     */
    public Contractor(String id, String parentId, String name, String nameFull, String inn,
                      String ogrn, String country, int industry, int orgForm, Date createDate,
                      Date modifyDate, String createUserId, String modifyUserId, boolean isActive) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.nameFull = nameFull;
        this.inn = inn;
        this.ogrn = ogrn;
        this.country = country;
        this.industry = industry;
        this.orgForm = orgForm;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.createUserId = createUserId;
        this.modifyUserId = modifyUserId;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public String getNameFull() {
        return nameFull;
    }

    public String getInn() {
        return inn;
    }

    public String getOgrn() {
        return ogrn;
    }

    public String getCountry() {
        return country;
    }

    public int getIndustry() {
        return industry;
    }

    public int getOrgForm() {
        return orgForm;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public boolean isActive() {
        return isActive;
    }

}
