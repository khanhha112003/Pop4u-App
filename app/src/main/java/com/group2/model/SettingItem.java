package com.group2.model;

import java.io.Serializable;

public class SettingItem implements Serializable {
    String settingID;
    int settingIcon;
    String settingTitle;

    public SettingItem(String settingID, int settingIcon, String settingTitle) {
        this.settingID = settingID;
        this.settingIcon = settingIcon;
        this.settingTitle = settingTitle;
    }

    public String getSettingID() {
        return settingID;
    }

    public void setSettingID(String settingID) {
        this.settingID = settingID;
    }

    public int getSettingIcon() {
        return settingIcon;
    }

    public void setSettingIcon(int settingIcon) {
        this.settingIcon = settingIcon;
    }

    public String getSettingTitle() {
        return settingTitle;
    }

    public void setSettingTitle(String settingTitle) {
        this.settingTitle = settingTitle;
    }
}
