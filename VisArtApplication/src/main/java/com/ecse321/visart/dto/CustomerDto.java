package com.ecse321.visart.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ecse321.visart.model.Customer;

public class CustomerDto {

  private UserDto user;
  private List<String> boughtTicketIds;
  private List<String> favoriteListingIds;
  private String artistId;
  private String idCode;

  public CustomerDto(Customer customer) {
    setUser(new UserDto(customer.getUser()));
    boughtTicketIds = customer.getBoughtTickets().stream().map((t) -> t.getIdCode())
        .collect(Collectors.toList());
    favoriteListingIds = customer.getFavoriteListings().stream().map((l) -> l.getIdCode())
        .collect(Collectors.toList());
    artistId = customer.getArtist().getIdCode();
    idCode = customer.getIdCode();
  }

  public CustomerDto(UserDto user, List<String> boughtTickets, List<String> favoriteListings,
      String artist, String idCode) {
    super();
    this.user = user;
    this.boughtTicketIds = boughtTickets;
    this.favoriteListingIds = favoriteListings;
    this.artistId = artist;
    this.idCode = idCode;
  }

  public CustomerDto(UserDto user) {
    super();
    this.user = user;
  }

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
    this.user = user;
  }

  public List<String> getBoughtTickets() {
    return boughtTicketIds;
  }

  public void setBoughtTickets(List<String> boughtTickets) {
    this.boughtTicketIds = boughtTickets;
  }

  public List<String> getFavoriteListings() {
    return favoriteListingIds;
  }

  public void setFavoriteListings(List<String> favoriteListings) {
    this.favoriteListingIds = favoriteListings;
  }

  public String getArtist() {
    return artistId;
  }

  public void setArtist(String artist) {
    this.artistId = artist;
  }

  public String getIdCode() {
    return idCode;
  }

  public void setIdCode(String idCode) {
    this.idCode = idCode;
  }

}
