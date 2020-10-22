package com.ecse321.visart.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ecse321.visart.model.Artist;

public class ArtistDto {
  private CustomerDto customer;
  private List<String> listingIds;
  private List<String> soldTicketIds;
  private String idCode;

  public CustomerDto getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDto customer) {
    this.customer = customer;
  }

  public ArtistDto(Artist artist) {
    customer = new CustomerDto(artist.getCustomer());
    setListings(
        artist.getPostedListings().stream().map((l) -> l.getIdCode()).collect(Collectors.toList()));
    setSoldTickets(
        artist.getSoldTickets().stream().map((t) -> t.getIdCode()).collect(Collectors.toList()));
    idCode = artist.getIdCode();
  }

  public ArtistDto(CustomerDto customer, List<String> listings, List<String> soldTickets, String idCode) {
    super();
    this.customer = customer;
    this.listingIds = listings;
    this.soldTicketIds = soldTickets;
    this.idCode = idCode;
  }

  public List<String> getListings() {
    return listingIds;
  }

  public void setListings(List<String> listings) {
    this.listingIds = listings;
  }

  public List<String> getSoldTickets() {
    return soldTicketIds;
  }

  public void setSoldTickets(List<String> soldTickets) {
    this.soldTicketIds = soldTickets;
  }

  public String getIdCode() {
    return idCode;
  }

  public void setIdCode(String idCode) {
    this.idCode = idCode;
  }

}
