package com.ecse321.visart.dto;

import com.ecse321.visart.model.User;

public class UserDto {
  private String emailAddress;
  private String displayname;
  private String username;
  private String password;
  private String profilePicLink;
  private String profileDescription;
  private String idCode;

  public UserDto() {
  }

  public UserDto(User user) {
    this(user.getEmailAddress(), user.getDisplayname(), user.getUsername(), user.getPassword(),
        user.getProfilePicLink(), user.getProfileDescription(), user.getIdCode());
  }

  public UserDto(String emailAddress, String displayname, String username, String password,
      String profilePicLink, String profileDescription, String idCode) {
    super();
    this.emailAddress = emailAddress;
    this.displayname = displayname;
    this.username = username;
    this.password = password;
    this.profilePicLink = profilePicLink;
    this.profileDescription = profileDescription;
    this.idCode = idCode;
  }

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

}
