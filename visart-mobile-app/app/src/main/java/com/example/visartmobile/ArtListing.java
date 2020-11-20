package com.example.visartmobile;

public class ArtListing {

    private String description;
    private String[] postImages;
    private String title;
    private PostVisibility visibility;
    private String idCode;
    private String managerId;
    private String artistId;
    private double price;
    private String artistDisplayname;
    private String artistUsername;

    public ArtListing() {
    }

    public ArtListing(String description, String[] postImages, String title, PostVisibility visibility, String idCode, String managerId, String artistId, double price, String artistDisplayname, String artistUsername) {
        this.description = description;
        this.postImages = postImages;
        this.title = title;
        this.visibility = visibility;
        this.idCode = idCode;
        this.managerId = managerId;
        this.artistId = artistId;
        this.price = price;
        this.artistDisplayname = artistDisplayname;
        this.artistUsername = artistUsername;
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

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
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
