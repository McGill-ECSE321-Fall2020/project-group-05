package com.ecse321.visart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.dto.ArtListingDto;
import com.ecse321.visart.dto.ArtistDto;
import com.ecse321.visart.dto.CustomerDto;
import com.ecse321.visart.service.ArtListingService;
import com.ecse321.visart.service.ArtistService;

@CrossOrigin(origins = "*")
@RestController

public class ArtListingRestController {
  @Autowired
  private ArtListingService service;
  private ArtistService servicea;


  @GetMapping(value = { "/ArtListings", "/ArtListings/" })
  public List<ArtListingDto> getAllArtListings() {
    return service.getAllArtListings().stream().map(al -> new ArtListingDto(al))
        .collect(Collectors.toList());
  }
  
  @GetMapping(value = { "/get_artlisting", "/get_artlisting/" })
  public ArtListingDto getCustomer(@RequestParam("idCode") String aIdCode) {
    return new ArtListingDto(service.getArtListing(aIdCode));
  }

  @PostMapping(value = { "/ArtListings/new", "/ArtListings/new/" })
  public ArtListingDto createArtListing(
      @RequestParam(value = "aVisibility") PostVisibility aVisibility,
      @RequestParam(value = "aDescription") String aDescription,
      @RequestParam(value = "aTitle") String aTitle,
      ArtistDto ADTO) {

    Artist a = servicea.createArtist(ADTO.getCustomer().getIdCode());
    return new ArtListingDto(service.createArtListing(aVisibility, aDescription, aTitle,
        a));
  }
  
  @PostMapping(value = { "/ArtListings/{aIdCode}", "/ArtListings/{aIdCode}/" })
  public ArtListingDto updateArtListing( @PathVariable("aIdCode") String aIdCode,
      @RequestParam(value = "aVisibility") PostVisibility aVisibility,
      @RequestParam(value = "aDescription") String aDescription,
      @RequestParam(value = "aTitle") String aTitle,
      ArtistDto ADTO) {

    return new ArtListingDto(service.updateArtListing(aIdCode, aVisibility, aDescription, aTitle));
  }
  
  @PostMapping(value = { "/update_artlisting_dimensions/{aIdCode}", "/update_artlisting_dimensions/{aIdCode}/" })
  public ArtListingDto updateDimensions( @PathVariable("aIdCode") String aIdCode,
      @RequestParam(value = "aDimensions") Float[] aDimensions) {

    return new ArtListingDto(service.updateDimensions(aIdCode, aDimensions));
  }
  
  @PostMapping(value = { "/update_artlisting_postimages/{aIdCode}", "/update_artlisting_postimages/{aIdCode}/" })
  public ArtListingDto updatePostImages( @PathVariable("aIdCode") String aIdCode,
      @RequestParam(value = "aPostImages") String[] aPostImages) {

    return new ArtListingDto(service.updatePostImages(aIdCode, aPostImages));
  }
  
  @PostMapping(value = { "/artlisting_addpiece/{aIdCode}", "/artlisting_addpiece/{aIdCode}/" })
  public ArtListingDto addArtPiece( @PathVariable("aIdCode") String aIdCode,
      @RequestParam(value = "apCode") String apCode) {

    return new ArtListingDto(service.addArtPiece(aIdCode, apCode));
  }
  
  @PostMapping(value = { "/artlisting_removepiece/{aIdCode}", "/artlisting_removepiece/{aIdCode}/" })
  public ArtListingDto removeArtPiece( @PathVariable("aIdCode") String aIdCode,
      @RequestParam(value = "apCode") String apCode) {

    return new ArtListingDto(service.removeArtPiece(aIdCode, apCode));
  }
  
  @PostMapping(value = { "/artlisting_getfavoritedcustomers/{aIdCode}", "/artlisting_getfavoritedcustomers/{aIdCode}/" })
  public List<CustomerDto> getFavoritedCustomers( @PathVariable("aIdCode") String aIdCode) {
    return service.getFavoritedCustomers(aIdCode).stream().map(c -> new CustomerDto(c))
        .collect(Collectors.toList());
  }
  
  @PostMapping(value = { "/artlisting_addtag/{aIdCode}", "/artlisting_addtag/{aIdCode}/" })
  public ArtListingDto addTag( @PathVariable("aIdCode") String aIdCode,
      @RequestParam(value = "tCode") String tCode) {

    return new ArtListingDto(service.addTag(aIdCode, tCode));
  }
  
  @PostMapping(value = { "/artlisting_removetag/{aIdCode}", "/artlisting_removetag/{aIdCode}/" })
  public ArtListingDto removeTag( @PathVariable("aIdCode") String aIdCode,
      @RequestParam(value = "tCode") String tCode) {

    return new ArtListingDto(service.removeTag(aIdCode, tCode));
  }
  
  @PostMapping(value = { "/artlisting_getunsoldartworks", "/artlisting_getunsoldartworks/" })
  public List<ArtListingDto> getUnsoldArtworks() {

    return service.getUnsoldArtworks().stream().map(al -> new ArtListingDto(al))
        .collect(Collectors.toList());
  }
  
  @PostMapping(value = { "/artlisting_filterartworksbytag", "/artlisting_filterartworksbytag/" })
  public List<ArtListingDto> filterArtworkByTagAsListings(@RequestParam(value = "keywords") String[] keywords) {

    return service.filterArtworkByTagAsListings(keywords).stream().map(al -> new ArtListingDto(al))
        .collect(Collectors.toList());
  }
  
}
