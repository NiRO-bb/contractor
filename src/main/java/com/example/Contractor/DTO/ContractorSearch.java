package com.example.Contractor.DTO;

import java.util.Optional;

/**
 * Represents request body for search method of {@code ContractorController}.
 * <p>
 * Contains fields that used as sorting parameters in SQL query.
 *
 * @see com.example.Contractor.Controller.ContractorController
 */
public class ContractorSearch {

    private final String contractorId;
    private final String parentId;
    private final String contractorSearch;
    private final String country;
    private final Integer industry;
    private final String orgForm;

    /**
     * Creates instance with initialized fields (some can be null).
     *
     * @param contractorId {@code id} field of {@link Contractor}
     * @param parentId {@code parentId} field of {@code Contractor}
     * @param contractorSearch any match with one of the following field:
     * {@code name}, {@code nameFull}, {@code inn}, {@code ogrn}
     * @param country partial or complete match with {@code name} field of {@link Country}
     * @param industry {@code industry} field of {@link Contractor}
     * @param orgForm partial or complete match with {@code name} field of {@link OrgForm}
     */
    public ContractorSearch(String contractorId, String parentId, String contractorSearch, String country, Integer industry, String orgForm) {
        this.contractorId = contractorId;
        this.parentId = parentId;
        this.contractorSearch = contractorSearch;
        this.country = country;
        this.industry = industry;
        this.orgForm = orgForm;
    }

    /**
     * Provides access to {@code contractorId} field.
     * <p>
     * Since field can br null, {@code Optional} is used.
     *
     * @return value of {@code contractorId}
     */
    public Optional<String> getContractorId() {
        return Optional.ofNullable(contractorId);
    }

    /**
     * Provides access to {@code parentId} field.
     * <p>
     * Since field can br null, {@code Optional} is used.
     *
     * @return value of {@code parentId}
     */
    public Optional<String> getParentId() {
        return Optional.ofNullable(parentId);
    }

    /**
     * Provides access to {@code contractorSearch} field.
     * <p>
     * Since field can br null, {@code Optional} is used.
     *
     * @return value of {@code contractorSearch}
     */
    public Optional<String> getContractorSearch() {
        return Optional.ofNullable(contractorSearch);
    }

    /**
     * Provides access to {@code country} field.
     * <p>
     * Since field can br null, {@code Optional} is used.
     *
     * @return value of {@code country}
     */
    public Optional<String> getCountry() {
        return Optional.ofNullable(country);
    }

    /**
     * Provides access to {@code industry} field.
     * <p>
     * Since field can br null, {@code Optional} is used.
     *
     * @return value of {@code industry}
     */
    public Optional<String> getIndustry() {
        return Optional.ofNullable(industry == null ? null : String.valueOf(industry));
    }

    /**
     * Provides access to {@code orgForm} field.
     * <p>
     * Since field can br null, {@code Optional} is used.
     *
     * @return value of {@code orgForm}
     */
    public Optional<String> getOrgForm() {
        return Optional.ofNullable(orgForm);
    }

}
