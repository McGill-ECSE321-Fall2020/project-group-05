package com.ecse321.visart.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.dto.ArtListingDto;
import com.ecse321.visart.service.ArtListingService;
import com.ecse321.visart.service.ArtistService;

@CrossOrigin(origins = "*")
@RestController

public class ArtListingRestController {
  @Autowired
  private ArtListingService artListingService;

  @Autowired
  private ArtistService artistService;

  /**
   * 
   * @param  idCode
   * @return
   */
  @PostMapping(value = { "/artlisting/delete/{idCode}", "/artlisting/delete/{idCode}/" })
  public boolean deleteArtListing(@PathVariable("idCode") String idCode) {
    return artListingService.deleteArtListing(idCode);
  }

  /**
   * 
   * @return
   */
  @GetMapping(value = { "/artlisting/get_all", "/artlisting/get_all/" })
  public List<ArtListingDto> getAllArtListings() {
    return artListingService.getAllArtListings().stream().map(al -> new ArtListingDto(al))
        .collect(Collectors.toList());
  }

  /**
   * 
   * @param  aIdCode
   * @return
   */
  @GetMapping(value = { "/artlisting/get/{idCode}", "/artlisting/get/{idCode}" })
  public ArtListingDto getArtListing(@PathVariable("idCode") String aIdCode) {
    return new ArtListingDto(artListingService.getArtListing(aIdCode));
  }

  /**
   * 
   * @param  aVisibility
   * @param  aDescription
   * @param  aTitle
   * @param  artistId
   * @return
   */
  @PostMapping(value = { "/artlisting/create", "/artlisting/create/" })
  public ArtListingDto createArtListing(@RequestBody MultiValueMap<String, String> map) {
    PostVisibility aVisibility = PostVisibility.fromString(map.getFirst("aVisibility"));
    String aDescription = map.getFirst("aDescription");
    String aTitle = map.getFirst("aTitle");
    Artist artist = artistService.getArtist(map.getFirst("artistId"));
    Double price = Double.valueOf(map.getFirst("price"));

    return new ArtListingDto(
        artListingService.createArtListing(price, aVisibility, aDescription, aTitle,
            artist));
  }

  /**
   * 
   * @param  aIdCode
   * @param  aVisibility
   * @param  aDescription
   * @param  aTitle
   * @param  artistId
   * @return
   */
  @PostMapping(value = { "/artlisting/update/{aIdCode}", "/artlisting/update/{aIdCode}/" })
  public ArtListingDto updateArtListing(@PathVariable("aIdCode") String aIdCode,
      @RequestBody MultiValueMap<String, String> map) {

    PostVisibility aVisibility = PostVisibility.fromString(map.getFirst("aVisibility"));
    String aDescription = map.getFirst("aDescription");
    String aTitle = map.getFirst("aTitle");
    Double price = null;
    if (map.getFirst("price") != null)
      price = Double.valueOf(map.getFirst("price"));

    return new ArtListingDto(
        artListingService.updateArtListing(aIdCode, price, aVisibility, aDescription, aTitle));
  }

  /**
   * 
   * @param  aIdCode
   * @param  aDimensions
   * @return
   */
  @PostMapping(value = {
      "/artlisting/update_dimensions/{aIdCode}",
      "/artlisting/update_dimensions/{aIdCode}/" })
  public ArtListingDto updateDimensions(@PathVariable("aIdCode") String aIdCode,
      @RequestBody MultiValueMap<String, String> map) {
    ArrayList<String> dimensions = new ArrayList<>();
    map.forEach((key, val) -> {
      if (key.equals("dimensions")) {
        dimensions.addAll(val);
      }
    });
    List<Float> arr = dimensions.stream().map((d) -> Float.valueOf(d)).collect(Collectors.toList());
    return new ArtListingDto(artListingService.updateDimensions(aIdCode, arr));
  }

  /**
   * 
   * @param  aIdCode
   * @param  aPostImages
   * @return
   */
  @PostMapping(value = {
      "/artlisting/update_post_images/{aIdCode}",
      "/artlisting/update_post_images/{aIdCode}/" })
  public ArtListingDto updatePostImages(@PathVariable("aIdCode") String aIdCode,
      @RequestBody MultiValueMap<String, String> map) {
    ArrayList<String> images = new ArrayList<>();
    map.forEach((key, val) -> {
      if (key.equals("images")) {
        images.addAll(val);
      }
    });
    return new ArtListingDto(artListingService.updatePostImages(aIdCode, images));
  }

  /**
   * 
   * @param  aIdCode
   * @param  apCode
   * @return
   */
  @PostMapping(value = { "/artlisting/add_piece/{aIdCode}", "/artlisting/add_piece/{aIdCode}/" })
  public ArtListingDto addArtPiece(@PathVariable("aIdCode") String aIdCode,
      @RequestBody MultiValueMap<String, String> map) {
    String apCode = map.getFirst("artPieceId");
    return new ArtListingDto(artListingService.addArtPiece(aIdCode, apCode));
  }

  /**
   * 
   * @param  aIdCode
   * @param  map
   * @return
   */
  @PostMapping(value = {
      "/artlisting/remove_piece/{aIdCode}",
      "/artlisting/remove_piece/{aIdCode}/" })
  public ArtListingDto removeArtPiece(@PathVariable("aIdCode") String aIdCode,
      @RequestBody MultiValueMap<String, String> map) {
    String apCode = map.getFirst("artPieceId");
    return new ArtListingDto(artListingService.removeArtPiece(aIdCode, apCode));
  }

  /**
   * 
   * @param  aIdCode
   * @return
   */
  @GetMapping(value = {
      "/artlisting/get_favorited_customers/{aIdCode}",
      "/artlisting/get_favorited_customers/{aIdCode}/" })
  public List<String> getFavoritedCustomers(@PathVariable("aIdCode") String aIdCode) {
    return artListingService.getFavoritedCustomers(aIdCode).stream().map(c -> c.getIdCode())
        .collect(Collectors.toList());
  }

  /**
   * 
   * @param  aIdCode
   * @param  map
   * @return
   */
  @PostMapping(value = { "/artlisting/add_tag/{aIdCode}", "/artlisting/add_tag/{aIdCode}/" })
  public ArtListingDto addTag(@PathVariable("aIdCode") String aIdCode,
      @RequestBody MultiValueMap<String, String> map) {
    String tCode = map.getFirst("tagCode");
    return new ArtListingDto(artListingService.addTag(aIdCode, tCode));
  }

  /**
   * 
   * @param  aIdCode
   * @param  tCode
   * @return
   */
  @PostMapping(value = { "/artlisting/remove_tag/{aIdCode}", "/artlisting/remove_tag/{aIdCode}/" })
  public ArtListingDto removeTag(@PathVariable("aIdCode") String aIdCode,
      @RequestBody MultiValueMap<String, String> map) {
    String tCode = map.getFirst("tagCode");
    return new ArtListingDto(artListingService.removeTag(aIdCode, tCode));
  }

  /**
   * 
   * @return
   */
  @GetMapping(value = { "/artlisting/get_unsold_art", "/artlisting/get_unsold_art/" })
  public List<ArtListingDto> getUnsoldArtworks() {
    return artListingService.getUnsoldArtworks().stream().map(al -> new ArtListingDto(al))
        .collect(Collectors.toList());
  }

  /**
   * example:
   * https://heroku.com/artlisting/get_artwork_by_keyword?keywords=bob%20burgers,hello,dingaling
   * keywords -> ["bob burgers", "hello", "dingaling"]
   * 
   * @param  keywords
   * @return
   */
  @GetMapping(value = {
      "/artlisting/get_artwork_by_keyword",
      "/artlisting/get_artwork_by_keyword/" })
  public List<ArtListingDto> filterArtworkByTagAsListings(
      @RequestParam(value = "keywords") List<String> keywords) {
    return artListingService.filterArtworkByTagAsListings(keywords).stream()
        .map(al -> new ArtListingDto(al))
        .collect(Collectors.toList());
  }

}
