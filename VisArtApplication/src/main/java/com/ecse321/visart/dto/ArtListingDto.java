package com.ecse321.visart.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtListing.PostVisibility;

public class ArtListingDto {

  private String description;
  private String[] postImages;
  private String title;
  private PostVisibility visibility;
  private Float[] dimensions;
  private String idCode;
  private List<String> artPieceIds;
  private List<String> favoritedCustomerIds;
  private List<String> tagIds;
  private String managerId;
  private String artistId;

  public ArtListingDto(ArtListing listing) {
    description = listing.getDescription();
    postImages = listing.getPostingPicLink();
    title = listing.getTitle();
    visibility = listing.getVisibility();
    dimensions = listing.getDimensions();
    idCode = listing.getIdCode();
    if (listing.getManager() != null)
      managerId = listing.getManager().getIdCode();
    if (listing.getPieces() != null)
      artPieceIds = listing.getPieces().stream().map((piece) -> piece.getIdCode())
        .collect(Collectors.toList());
    favoritedCustomerIds = listing.getFavoritedCustomer().stream()
        .map((customer) -> customer.getIdCode()).collect(Collectors.toList());
    if (listing.getTags() != null)
      tagIds = listing.getTags().stream().map((t) -> t.getIdCode()).collect(Collectors.toList());
    if (listing.getArtist() != null)
      artistId = listing.getArtist().getIdCode();
  }

  public ArtListingDto(String description, String[] postImages, String title,
      PostVisibility visibility, Float[] dimensions, String idCode, List<String> artPieceIds,
      List<String> favortiedCustomerIds, List<String> tagIds, String artistId) {
    super();
    this.description = description;
    this.postImages = postImages;
    this.title = title;
    this.visibility = visibility;
    this.dimensions = dimensions;
    this.idCode = idCode;
    this.artPieceIds = artPieceIds;
    this.favoritedCustomerIds = favortiedCustomerIds;
    this.tagIds = tagIds;
    this.artistId = artistId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String[] getPostImages() {
    return postImages;
  }

  public void setPostImages(String[] postImages) {
    this.postImages = postImages;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public PostVisibility getVisibility() {
    return visibility;
  }

  public void setVisibility(PostVisibility visibility) {
    this.visibility = visibility;
  }

  public Float[] getDimensions() {
    return dimensions;
  }

  public void setDimensions(Float[] dimensions) {
    this.dimensions = dimensions;
  }

  public String getIdCode() {
    return idCode;
  }

  public void setIdCode(String idCode) {
    this.idCode = idCode;
  }

  public List<String> getArtPieces() {
    return artPieceIds;
  }

  public void setArtPieces(List<String> artPieces) {
    this.artPieceIds = artPieces;
  }

  public List<String> getFavortiedCustomers() {
    return favoritedCustomerIds;
  }

  public void setFavortiedCustomers(List<String> favortiedCustomers) {
    this.favoritedCustomerIds = favortiedCustomers;
  }

  public List<String> getTags() {
    return tagIds;
  }

  public void setTags(List<String> tags) {
    this.tagIds = tags;
  }

  public String getArtist() {
    return artistId;
  }

  public void setArtist(String artist) {
    this.artistId = artist;
  }
}
