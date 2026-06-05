package com.sunbird.serve.volunteering.config;

/**
 * ThreadLocal-based holder for tenant/user context extracted from JWT.
 * Populated by JwtTenantFilter after authentication.
 */
public class TenantContext {

    private static final ThreadLocal<String> AGENCY_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> AGENCY_TYPE = new ThreadLocal<>();
    private static final ThreadLocal<String> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> USER_EMAIL = new ThreadLocal<>();

    public static String getAgencyId() {
        return AGENCY_ID.get();
    }

    public static void setAgencyId(String agencyId) {
        AGENCY_ID.set(agencyId);
    }

    public static String getAgencyType() {
        return AGENCY_TYPE.get();
    }

    public static void setAgencyType(String agencyType) {
        AGENCY_TYPE.set(agencyType);
    }

    public static String getUserId() {
        return USER_ID.get();
    }

    public static void setUserId(String userId) {
        USER_ID.set(userId);
    }

    public static String getUserEmail() {
        return USER_EMAIL.get();
    }

    public static void setUserEmail(String userEmail) {
        USER_EMAIL.set(userEmail);
    }

    public static boolean isTenantScoped() {
        return AGENCY_ID.get() != null;
    }

    public static void clear() {
        AGENCY_ID.remove();
        AGENCY_TYPE.remove();
        USER_ID.remove();
        USER_EMAIL.remove();
    }
}
