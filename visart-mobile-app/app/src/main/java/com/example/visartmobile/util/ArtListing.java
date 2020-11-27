package com.example.visartmobile.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArtListing {
    private boolean isPromoted;
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
    private String artPieceId;

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
        this.artPieceId = "";
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

    public String getArtPieceId() {
        return artPieceId;
    }

    public void setArtPieceId(String idCode) {
        this.artPieceId = idCode;
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

    public static ArtListing parseJSON(JSONObject artlistingObj) {
        ArtListing al = new ArtListing();

        try {
            al.setDescription(artlistingObj.getString("description"));
            List<String> postImagesList = jsonArrayToList(artlistingObj.getJSONArray("postImages"));
            String[] postImages = new String[postImagesList.size()];
            postImagesList.toArray(postImages);
            al.setPostImages(postImages);
            al.setTitle(artlistingObj.getString("title"));
            al.setVisibility(PostVisibility.fromString(artlistingObj.getString("visibility")));
            al.setIdCode(artlistingObj.getString("idCode"));
            String managerId = artlistingObj.getString("managerId");
            managerId = managerId == null || managerId.equals("null") ? null : managerId;
            al.setManagerId(managerId);
            al.setPromoted(managerId != null);
            al.setArtistId(artlistingObj.getString("artistId"));
            al.setPrice(artlistingObj.getDouble("price"));
            al.setArtistDisplayname(artlistingObj.getString("artistDisplayname"));
            al.setArtistUsername(artlistingObj.getString("artistUsername"));
        
            return al;
        } catch (JSONException e) {
            return null;
        }
    }

    public static ArrayList<ArtListing> parseJSONArray(JSONArray artListingObj) {
        ArrayList<ArtListing> list = new ArrayList<>();
        for (int i = 0; i < artListingObj.length(); i++) {
            try {
                list.add(parseJSON(artListingObj.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static <T> List<T> jsonArrayToList(JSONArray arr) throws JSONException {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            try {
                Object t = arr.get(i);
                result.add((T) t);
            } catch (Exception e) {
            }
        }
        return result;
    }

    public boolean isPromoted() {
        return isPromoted;
    }

    public void setPromoted(boolean promoted) {
        isPromoted = promoted;
    }
}
