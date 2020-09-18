package com.oakland.ekit.ui.login;

import androidx.annotation.Nullable;

import com.oakland.ekit.data.model.LoggedInUser;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {

    @Nullable
    private LoggedInUser success;
    @Nullable
    private Integer error;


    //String userName = "";


    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable LoggedInUser success) {
        this.success = success;
    }

    @Nullable
    LoggedInUser getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
