package com.example.Contractor.DTO;

import com.example.Contractor.Controller.ContractorControllerImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * Represents request body for search method of {@code ContractorController}.
 * <p>
 * Contains fields that used as sorting parameters in SQL query.
 *
 * @see ContractorControllerImpl
 */
@NoArgsConstructor
@AllArgsConstructor
public class ContractorSearch {

    private String contractorId;
    private String parentId;
    private String contractorSearch;
    private String country;
    private Integer industry;
    private String orgForm;

    /**
     * Provides access to {@code contractorId} field.
     * <p>
     * Since field can be null, {@code Optional} is used.
     *
     * @return value of {@code contractorId}
     */
    public Optional<String> getContractorId() {
        return Optional.ofNullable(contractorId);
    }

    /**
     * Provides access to {@code parentId} field.
     * <p>
     * Since field can be null, {@code Optional} is used.
     *
     * @return value of {@code parentId}
     */
    public Optional<String> getParentId() {
        return Optional.ofNullable(parentId);
    }

    /**
     * Provides access to {@code contractorSearch} field.
     * <p>
     * Since field can be null, {@code Optional} is used.
     *
     * @return value of {@code contractorSearch}
     */
    public Optional<String> getContractorSearch() {
        return Optional.ofNullable(contractorSearch);
    }

    /**
     * Provides access to {@code country} field.
     * <p>
     * Since field can be null, {@code Optional} is used.
     *
     * @return value of {@code country}
     */
    public Optional<String> getCountry() {
        return Optional.ofNullable(country);
    }

    /**
     * Provides access to {@code industry} field.
     * <p>
     * Since field can be null, {@code Optional} is used.
     *
     * @return value of {@code industry}
     */
    public Optional<String> getIndustry() {
        return Optional.ofNullable(industry == null ? null : String.valueOf(industry));
    }

    /**
     * Provides access to {@code orgForm} field.
     * <p>
     * Since field can be null, {@code Optional} is used.
     *
     * @return value of {@code orgForm}
     */
    public Optional<String> getOrgForm() {
        return Optional.ofNullable(orgForm);
    }

}
