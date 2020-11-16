package com.ecse321.visart.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Ticket;

public class CustomerDto {

  private UserDto user;
  private List<String> boughtTicketIds;
  private List<String> favoriteListingIds;
  private String artistId;
  private String idCode;
  private List<TicketDto> boughtTickets;
  private List<ArtListingDto> favoriteListings;

  public CustomerDto(Customer customer) {
    setUser(new UserDto(customer.getUser()));
    setBoughtTicketIds(customer.getBoughtTickets());
    setFavoriteListingIds(customer.getFavoriteListings());
    if (customer.getArtist() != null)
      setArtistId(customer.getArtist().getIdCode());
    idCode = customer.getIdCode();
  }

  public CustomerDto(UserDto user, List<String> boughtTickets, List<String> favoriteListings,
      String artist, String idCode) {
    super();
    this.user = user;
    this.boughtTicketIds = (boughtTickets);
    this.favoriteListingIds = (favoriteListings);
    this.setArtistId(artist);
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

  public String getIdCode() {
    return idCode;
  }

  public void setIdCode(String idCode) {
    this.idCode = idCode;
  }

  public List<String> getFavoriteListingIds() {
    return favoriteListingIds;
  }

  public void setFavoriteListingIds(List<ArtListing> list) {
    this.favoriteListingIds = list.stream().map((l) -> l.getIdCode())
        .collect(Collectors.toList());
    this.favoriteListings = list.stream().map(l -> new ArtListingDto(l))
        .collect(Collectors.toList());
  }

  public String getArtistId() {
    return artistId;
  }

  public void setArtistId(String artistId) {
    this.artistId = artistId;
  }

  public List<String> getBoughtTicketIds() {
    return boughtTicketIds;
  }

  public void setBoughtTicketIds(List<Ticket> boughtTickets) {
    this.boughtTickets = boughtTickets.stream().map(t -> new TicketDto(t))
        .collect(Collectors.toList());
    this.boughtTicketIds = boughtTickets.stream().map((t) -> t.getIdCode())
        .collect(Collectors.toList());
  }

  public List<TicketDto> getBoughtTickets() {
    return boughtTickets;
  }

  public List<ArtListingDto> getFavoriteListings() {
    return favoriteListings;
  }

}
