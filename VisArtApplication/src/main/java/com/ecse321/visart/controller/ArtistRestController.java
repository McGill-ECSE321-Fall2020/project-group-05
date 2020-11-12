package com.ecse321.visart.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.ecse321.visart.dto.ArtistDto;
import com.ecse321.visart.service.ArtistService;

@CrossOrigin(origins = "*")
@RestController
public class ArtistRestController {

  @Autowired
  private ArtistService artistService;

  /**
   * @param  idCode
   * @return
   */
  @GetMapping(value = { "/artists/get/{idCode}", "/artists/get/{idCode}/" })
  public ArtistDto getArtist(@PathVariable("idCode") String idCode) {
    return new ArtistDto(artistService.getArtist(idCode, true, true));
  }

  /**
   * 
   * @return
   */
  @GetMapping(value = { "/artists/get_all", "/artists/get_all/" })
  public List<ArtistDto> getAllArtists() {
    return artistService.getAllArtists().stream().map(a -> new ArtistDto(a))
        .collect(Collectors.toList());
  }

  /**
   * 
   * @return
   */
  @GetMapping(value = { "/artists/get_all_keys", "/artists/get_all_keys/" })
  public List<String> getAllArtistKeys() {
    return artistService.getAllKeys();
  }

  /**
   * 
   * @param  map
   * @return
   */
  @PostMapping(value = { "/artists/create", "/artists/create/" })
  public ArtistDto createArtist(@RequestBody MultiValueMap<String, String> map) {
    return new ArtistDto(artistService.createArtist(map.getFirst("customerId")));
  }

  /**
   * 
   * @param  idCode
   * @return
   */
  @PostMapping(value = { "/artists/delete/{idCode}", "/artists/delete/{idCode}/" })
  public boolean deleteArtist(@PathVariable("idCode") String idCode) {
    return artistService.deleteArtist(idCode);
  }

  /**
   * 
   * @param idCode
   * @param listingId
   */
  @PostMapping(value = {
      "/artists/add_listing/{idCode}/{listingId}",
      "/artists/add_listing/{idCode}/{listingId}/" })
  public void addListing(@PathVariable("idCode") String idCode,
      @PathVariable("listingId") String listingId) {
    List<String> listingIds = Arrays.asList(new String[] { listingId });
    artistService.addListings(idCode, listingIds);
  }

  /**
   * 
   * @param idCode
   * @param ticketId
   */
  @PostMapping(value = {
      "/artists/add_ticket/{idCode}/{ticketId}",
      "/artists/add_ticket/{idCode}/{ticketId}/" })
  public void addSoldTicket(@PathVariable("idCode") String idCode,
      @PathVariable("ticketId") String ticketId) {
    List<String> ticketIds = Arrays.asList(new String[] { ticketId });
    artistService.addSoldTickets(idCode, ticketIds);
  }

  /**
   * 
   * @param idCode
   * @param listingId
   */
  @PostMapping(value = {
      "/artists/remove_listing/{idCode}/{listingId}",
      "/artists/remove_listing/{idCode}/{listingId}/" })
  public void removeListing(@PathVariable("idCode") String idCode,
      @PathVariable("listingId") String listingId) {
    List<String> listingIds = Arrays.asList(new String[] { listingId });
    artistService.addListings(idCode, listingIds);
  }

  /**
   * 
   * @param idCode
   * @param ticketId
   */
  @PostMapping(value = {
      "/artists/remove_ticket/{idCode}/{ticketId}",
      "/artists/remove_ticket/{idCode}/{ticketId}/" })
  public void removeSoldTicket(@PathVariable("idCode") String idCode,
      @PathVariable("ticketId") String ticketId) {
    List<String> ticketIds = Arrays.asList(new String[] { ticketId });
    artistService.removeSoldTickets(idCode, ticketIds);
  }

}
