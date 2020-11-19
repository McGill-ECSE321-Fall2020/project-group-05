package com.ecse321.visart.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Tag;

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
  private double price;
  private String artistDisplayname;
  private String artistUsername;

  private List<ArtPieceDto> artPieces;
  private List<TagDto> tags;

  public ArtListingDto(ArtListing listing) {
    price = listing.getPrice();
    description = listing.getDescription();
    postImages = listing.getPostingPicLink();
    title = listing.getTitle();
    visibility = listing.getVisibility();
    dimensions = listing.getDimensions();
    idCode = listing.getIdCode();
    if (listing.getManager() != null)
      setManagerId(listing.getManager().getIdCode());
    if (listing.getPieces() != null)
      setArtPieces(listing.getPieces());
    setFavoritedCustomerIds(listing.getFavoritedCustomer().stream()
        .map((customer) -> customer.getIdCode()).collect(Collectors.toList()));
    if (listing.getTags() != null)
      setTags(listing.getTags());
    if (listing.getArtist() != null) {
      artistId = listing.getArtist().getIdCode();
      artistDisplayname = listing.getArtist().getCustomer().getUser().getDisplayname();
      artistUsername = listing.getArtist().getCustomer().getUser().getUsername();
    }
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
    this.setFavoritedCustomerIds(favortiedCustomerIds);
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

  public String getArtist() {
    return artistId;
  }

  public void setArtist(String artist) {
    this.artistId = artist;
  }

  public String getManagerId() {
    return managerId;
  }

  public void setManagerId(String managerId) {
    this.managerId = managerId;
  }

  public List<String> getFavoritedCustomerIds() {
    return favoritedCustomerIds;
  }

  public void setFavoritedCustomerIds(List<String> favoritedCustomerIds) {
    this.favoritedCustomerIds = favoritedCustomerIds;
  }

  public List<ArtPieceDto> getArtPieces() {
    return artPieces;
  }

  public void setArtPieces(List<ArtPiece> artPieces) {
    this.artPieces = artPieces.stream().map(p -> new ArtPieceDto(p)).collect(Collectors.toList());
    this.artPieceIds = artPieces.stream().map((piece) -> piece.getIdCode())
        .collect(Collectors.toList());
  }

  public List<TagDto> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags.stream().map(t -> new TagDto(t)).collect(Collectors.toList());
    this.tagIds = tags.stream().map((t) -> t.getIdCode()).collect(Collectors.toList());
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getArtistDisplayname() {
    return artistDisplayname;
  }

  public void setArtistDisplayname(String artistDisplayname) {
    this.artistDisplayname = artistDisplayname;
  }

  public String getArtistUsername() {
    return artistUsername;
  }

  public void setArtistUsername(String artistUsername) {
    this.artistUsername = artistUsername;
  }
}
