package com.oakland.ekit.data.model;

import org.json.JSONObject;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private boolean isSpecial;
    private JSONObject serverData;

    public LoggedInUser(String userId, String displayName, Boolean isSpecial, JSONObject serverData) {
        this.userId = userId;
        this.displayName = displayName;
        this.isSpecial = isSpecial;
        this.serverData = serverData;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean getIsSpecial() {
        return isSpecial;
    }

    public JSONObject getServerData() {
        return serverData;
    }
}
