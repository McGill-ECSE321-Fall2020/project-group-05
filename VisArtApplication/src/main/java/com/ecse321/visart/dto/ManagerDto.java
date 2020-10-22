package com.ecse321.visart.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ecse321.visart.model.Manager;

public class ManagerDto {

  private UserDto user;
  private List<String> promotedListingIds;
  private String idCode;

  public ManagerDto(Manager manager) {
    promotedListingIds = manager.getPromotedListings().stream().map((l) -> l.getIdCode())
        .collect(Collectors.toList());
    user = new UserDto(manager.getUser());
    idCode = manager.getIdCode();
  }

  public ManagerDto(UserDto user, List<String> promotedListingIds, String idCode) {
    super();
    this.user = user;
    this.promotedListingIds = promotedListingIds;
    this.idCode = idCode;
  }

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
    this.user = user;
  }

  public List<String> getPromotedListings() {
    return promotedListingIds;
  }

  public void setPromotedListings(List<String> promotedListings) {
    this.promotedListingIds = promotedListings;
  }

  public String getIdCode() {
    return idCode;
  }

  public void setIdCode(String idCode) {
    this.idCode = idCode;
  }

}
