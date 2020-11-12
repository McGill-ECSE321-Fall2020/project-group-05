package com.ecse321.visart.dto;

public class CredentialsDto {
  private String firebaseJWT;
  private String springJWT;
  private String userId;

  public CredentialsDto() {
    this.firebaseJWT = "";
    this.springJWT = "";
  }

  public CredentialsDto(String firebaseJWT, String springJWT, String userId) {
    super();
    this.firebaseJWT = firebaseJWT;
    this.springJWT = springJWT;
    this.userId = userId;
  }

  public String getFirebaseJWT() {
    return firebaseJWT;
  }

  public void setFirebaseJWT(String firebaseJWT) {
    this.firebaseJWT = firebaseJWT;
  }

  public String getSpringJWT() {
    return springJWT;
  }

  public void setSpringJWT(String springJWT) {
    this.springJWT = springJWT;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}
