package com.example.Contractor.Utils;

import com.example.Contractor.DTO.ContractorSearch;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Provides means to obtain user access level data.
 */
public final class RoleAccess {

    private RoleAccess() {}

    /**
     * Checks if user has access to some method.
     *
     * @param search contains filtering fields
     * @return result of check (true - access permitted, false - access denied)
     */
    public static boolean hasAccess(ContractorSearch search) {
        List<String> roles = getUserRoles();
        if (roles.contains("CONTRACTOR_SUPERUSER") || roles.contains("SUPERUSER")) {
            return true;
        } else if (roles.contains("CONTRACTOR_RUS")) {
            if (search.getCountry().isEmpty()) {
                return true;
            } else if (search.getCountry().get().equalsIgnoreCase("rus")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves all user roles as String list.
     * Extract data from Security context holder.
     *
     * @return extracted data
     */
    private static List<String> getUserRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().map(Object::toString).toList();
    }

}
