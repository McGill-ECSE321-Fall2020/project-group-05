package com.ecse321.visart.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Ticket;

public class ArtistDto {
  private CustomerDto customer;
  private List<String> listingIds;
  private List<String> soldTicketIds;
  private String idCode;
  private List<ArtListingDto> artListingsDtos;
  private List<TicketDto> soldTicketsDtos;

  public CustomerDto getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDto customer) {
    this.customer = customer;
  }

  public ArtistDto(Artist artist) {
    customer = new CustomerDto(artist.getCustomer());
    setListings(artist.getPostedListings());
    setSoldTickets(artist.getSoldTickets());
    idCode = artist.getIdCode();
  }

  public ArtistDto(CustomerDto customer, List<String> listings, List<String> soldTickets,
      String idCode) {
    super();
    this.customer = customer;
    this.listingIds = listings;
    this.soldTicketIds = soldTickets;
    this.idCode = idCode;
  }

  public List<String> getListings() {
    return listingIds;
  }

  public void setListings(List<ArtListing> listings) {
    this.artListingsDtos = listings.stream().map(l -> new ArtListingDto(l)).collect(Collectors.toList());
    this.listingIds = listings.stream().map((l) -> l.getIdCode()).collect(Collectors.toList());
  }

  public List<String> getSoldTickets() {
    return soldTicketIds;
  }

  public void setSoldTickets(List<Ticket> soldTickets) {
    this.soldTicketsDtos = soldTickets.stream().map(l -> new TicketDto(l)).collect(Collectors.toList());
    this.soldTicketIds = soldTickets.stream().map((t) -> t.getIdCode())
        .collect(Collectors.toList());
  }

  public String getIdCode() {
    return idCode;
  }

  public void setIdCode(String idCode) {
    this.idCode = idCode;
  }

  public List<ArtListingDto> getArtListingsDtos() {
    return artListingsDtos;
  }

  public void setArtListingsDtos(List<ArtListingDto> artListingsDtos) {
    this.artListingsDtos = artListingsDtos;
  }

  public List<TicketDto> getSoldTicketsDtos() {
    return soldTicketsDtos;
  }

  public void setSoldTicketsDtos(List<TicketDto> soldTicketsDtos) {
    this.soldTicketsDtos = soldTicketsDtos;
  }

}
