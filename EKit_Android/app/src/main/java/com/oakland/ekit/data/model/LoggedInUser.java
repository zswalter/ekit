package com.oakland.ekit.data.model;

import org.json.JSONObject;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String mUserName;
    private String mPassword;

    private String userId;
    private String displayName;
    private boolean isSpecial;
    private JSONObject serverData;

    public LoggedInUser(String userId, String displayName, Boolean isSpecial, JSONObject serverData, String userName, String password) {
        this.userId = userId;
        this.displayName = displayName;
        this.isSpecial = isSpecial;
        this.serverData = serverData;
        this.mUserName = userName;
        this.mPassword = password;
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


    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }
}
