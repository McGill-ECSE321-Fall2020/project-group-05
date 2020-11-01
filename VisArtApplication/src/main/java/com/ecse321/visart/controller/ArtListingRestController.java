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

  @PostMapping(value = { "/ArtListings/{aIdCode}", "/ArtListings/{aIdCode}/" })
  public ArtListingDto createArtListing(@PathVariable("aIdCode") String aIdCode,
      @RequestParam(value = "aVisibility") PostVisibility aVisibility,
      @RequestParam(value = "aDescription") String aDescription,
      @RequestParam(value = "aTitle") String aTitle,
      ArtistDto ADTO) {

    Artist a = servicea.createArtist(ADTO.getIdCode(), ADTO.getCustomer().getUser().getEmailAddress(), ADTO.getCustomer().getUser().getDisplayname(), ADTO.getCustomer().getUser().getUsername(), ADTO.getCustomer().getUser().getPassword(), ADTO.getCustomer().getUser().getProfilePicLink(), ADTO.getCustomer().getUser().getProfileDescription());
    return new ArtListingDto(service.createArtListing(aVisibility, aDescription, aTitle,
        aIdCode, a));
  }
}
