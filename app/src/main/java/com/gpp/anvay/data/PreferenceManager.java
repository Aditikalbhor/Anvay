package com.gpp.anvay.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.gpp.anvay.model.UserProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PreferenceManager {
    private static final String PREF_NAME = "anvay_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_IS_GUEST = "is_guest";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_ROLE = "user_role";
    private static final String KEY_USER_DEPT = "user_dept";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_EMERGENCY_PHONE = "user_emergency_phone";
    private static final String KEY_PUSH_NOTIFICATIONS = "push_notifications";
    private static final String KEY_HIGH_CONTRAST = "high_contrast";
    private static final String KEY_OFFLINE_CACHE = "offline_cache";
    private static final String KEY_RECENT_SEARCHES = "recent_searches";

    // Prefix for registered local accounts
    private static final String PREFIX_REG_NAME = "reg_name_";
    private static final String PREFIX_REG_ROLE = "reg_role_";
    private static final String PREFIX_REG_DEPT = "reg_dept_";
    private static final String PREFIX_REG_ID = "reg_id_";
    private static final String PREFIX_REG_EMAIL = "reg_email_";
    private static final String PREFIX_REG_PWD = "reg_pwd_";
    private static final String PREFIX_REG_PHONE = "reg_phone_";

    private final SharedPreferences prefs;

    public PreferenceManager(Context context) {
        this.prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setLoggedIn(boolean loggedIn) {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, loggedIn).apply();
    }

    public boolean isGuestMode() {
        return prefs.getBoolean(KEY_IS_GUEST, false);
    }

    public void setGuestMode(boolean isGuest) {
        prefs.edit().putBoolean(KEY_IS_GUEST, isGuest).apply();
    }

    public UserProfile getUserProfile() {
        UserProfile profile = new UserProfile();
        profile.setName(prefs.getString(KEY_USER_NAME, "Aditi Kulkarni"));
        profile.setRole(prefs.getString(KEY_USER_ROLE, "Student"));
        profile.setDepartment(prefs.getString(KEY_USER_DEPT, "Computer Engineering"));
        profile.setIdNumber(prefs.getString(KEY_USER_ID, "GPP/CO/2024/042"));
        profile.setEmail(prefs.getString(KEY_USER_EMAIL, "aditi.kulkarni@gppune.ac.in"));
        profile.setEmergencyPhone(prefs.getString(KEY_EMERGENCY_PHONE, "+91 98221 54321"));
        profile.setPushNotifications(prefs.getBoolean(KEY_PUSH_NOTIFICATIONS, true));
        profile.setHighContrast(prefs.getBoolean(KEY_HIGH_CONTRAST, false));
        profile.setOfflineCache(prefs.getBoolean(KEY_OFFLINE_CACHE, true));
        return profile;
    }

    public void saveUserProfile(UserProfile profile) {
        if (profile == null) return;
        prefs.edit()
                .putString(KEY_USER_NAME, profile.getName())
                .putString(KEY_USER_ROLE, profile.getRole())
                .putString(KEY_USER_DEPT, profile.getDepartment())
                .putString(KEY_USER_ID, profile.getIdNumber())
                .putString(KEY_USER_EMAIL, profile.getEmail() != null ? profile.getEmail() : "user@gppune.ac.in")
                .putString(KEY_EMERGENCY_PHONE, profile.getEmergencyPhone())
                .putBoolean(KEY_PUSH_NOTIFICATIONS, profile.isPushNotifications())
                .putBoolean(KEY_HIGH_CONTRAST, profile.isHighContrast())
                .putBoolean(KEY_OFFLINE_CACHE, profile.isOfflineCache())
                .apply();
    }

    /**
     * Saves a newly created registered account locally so the user can log in with it.
     */
    public void saveRegisteredAccount(UserProfile profile, String password) {
        if (profile == null || profile.getIdNumber() == null || password == null) return;

        String idKey = profile.getIdNumber().trim().toLowerCase(Locale.ROOT);
        String emailKey = profile.getEmail() != null ? profile.getEmail().trim().toLowerCase(Locale.ROOT) : "";

        SharedPreferences.Editor editor = prefs.edit();

        // Save by ID
        editor.putString(PREFIX_REG_NAME + idKey, profile.getName());
        editor.putString(PREFIX_REG_ROLE + idKey, profile.getRole());
        editor.putString(PREFIX_REG_DEPT + idKey, profile.getDepartment());
        editor.putString(PREFIX_REG_ID + idKey, profile.getIdNumber());
        editor.putString(PREFIX_REG_EMAIL + idKey, profile.getEmail());
        editor.putString(PREFIX_REG_PWD + idKey, password);
        editor.putString(PREFIX_REG_PHONE + idKey, profile.getEmergencyPhone());

        // Save alias by email for login lookup
        if (!emailKey.isEmpty()) {
            editor.putString(PREFIX_REG_NAME + emailKey, profile.getName());
            editor.putString(PREFIX_REG_ROLE + emailKey, profile.getRole());
            editor.putString(PREFIX_REG_DEPT + emailKey, profile.getDepartment());
            editor.putString(PREFIX_REG_ID + emailKey, profile.getIdNumber());
            editor.putString(PREFIX_REG_EMAIL + emailKey, profile.getEmail());
            editor.putString(PREFIX_REG_PWD + emailKey, password);
            editor.putString(PREFIX_REG_PHONE + emailKey, profile.getEmergencyPhone());
        }

        editor.apply();
    }

    /**
     * Checks if a registered local account exists for given ID or Email and validates password.
     */
    public UserProfile authenticateRegisteredAccount(String idOrEmail, String password) {
        if (idOrEmail == null || password == null) return null;
        String key = idOrEmail.trim().toLowerCase(Locale.ROOT);

        String storedPwd = prefs.getString(PREFIX_REG_PWD + key, null);
        if (storedPwd != null && storedPwd.equals(password.trim())) {
            UserProfile profile = new UserProfile();
            profile.setName(prefs.getString(PREFIX_REG_NAME + key, "Registered User"));
            profile.setRole(prefs.getString(PREFIX_REG_ROLE + key, "Student"));
            profile.setDepartment(prefs.getString(PREFIX_REG_DEPT + key, "Computer Engineering"));
            profile.setIdNumber(prefs.getString(PREFIX_REG_ID + key, idOrEmail));
            profile.setEmail(prefs.getString(PREFIX_REG_EMAIL + key, "user@gppune.ac.in"));
            profile.setEmergencyPhone(prefs.getString(PREFIX_REG_PHONE + key, "+91 98000 00000"));
            return profile;
        }
        return null;
    }

    public void logout() {
        prefs.edit()
                .putBoolean(KEY_IS_LOGGED_IN, false)
                .putBoolean(KEY_IS_GUEST, false)
                .apply();
    }

    public void setUserRole(String role) {
        prefs.edit().putString(KEY_USER_ROLE, role).apply();
    }

    public List<String> getRecentSearches() {
        String raw = prefs.getString(KEY_RECENT_SEARCHES, "C-101,AI/ML Lab,HOD IT,Washroom,Server Room");
        if (raw.isEmpty()) return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(raw.split(",")));
    }

    public void addRecentSearch(String query) {
        if (query == null || query.trim().isEmpty()) return;
        List<String> list = getRecentSearches();
        list.remove(query.trim());
        list.add(0, query.trim());
        if (list.size() > 8) {
            list = list.subList(0, 8);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) sb.append(",");
        }
        prefs.edit().putString(KEY_RECENT_SEARCHES, sb.toString()).apply();
    }

    public void clearRecentSearches() {
        prefs.edit().putString(KEY_RECENT_SEARCHES, "").apply();
    }
}
