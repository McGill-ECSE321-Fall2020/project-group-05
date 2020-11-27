package com.example.visartmobile.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * UserDto retrieved from the Spring database, containing as much data from a User as needed.
 */
public class UserDto {

    private String emailAddress;
    private String displayname;
    private String username;
    private String profilePicLink;
    private String profileDescription;
    private String idCode;
    private String role;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicLink() {
        return profilePicLink;
    }

    public void setProfilePicLink(String profilePicLink) {
        this.profilePicLink = profilePicLink;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Parse a JSON string and convert into a UserDto. Returns null upon failed parsing.
     *
     * @param string json string data
     * @return parsed UserDto on success, null on failure
     */
    public static UserDto parseJSON(String string) {
        try {
            return parseJSON(new JSONObject(string));
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Parse a JSON Object and convert into a UserDto. Returns null upon failed parsing.
     *
     * @param jsonObject the json object to parse
     * @return parsed UserDto on success, null on failure
     */
    private static UserDto parseJSON(JSONObject jsonObject) {
        UserDto user = new UserDto();
        try {
            user.emailAddress = jsonObject.getString("emailAddress");
            user.displayname = jsonObject.getString("displayname");
            user.username = jsonObject.getString("username");
            user.profilePicLink = jsonObject.getString("profilePicLink");
            user.profileDescription = jsonObject.getString("profileDescription");
            user.idCode = jsonObject.getString("idCode");
            user.role = jsonObject.getString("role");
            return user;
        } catch (JSONException e) {
            return null;
        }
    }
}
