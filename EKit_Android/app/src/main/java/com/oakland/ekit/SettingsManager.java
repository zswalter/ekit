package com.oakland.ekit;

import com.oakland.ekit.data.model.LoggedInUser;

public class SettingsManager {

    public static SettingsManager sharedInstance = new SettingsManager();

    public LoggedInUser mLoggedInUser = null;



}
