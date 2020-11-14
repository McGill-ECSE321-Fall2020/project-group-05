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
   * GET Request
   * example:
   * https://vis-art-application-production.herokuapp.com/artists/get/6230178493701286t350148
   * returns: gives the artist object, given an artistId
   * 
   * {url}/artists/get/{idCode}
   * params: none
   * body  : none
   * idCode = artist id to get data on
   */
  @GetMapping(value = { "/artists/get/{idCode}", "/artists/get/{idCode}/" })
  public ArtistDto getArtist(@PathVariable("idCode") String idCode) {
    return new ArtistDto(artistService.getArtist(idCode, true, true));
  }

  /**
   * GET Request
   * example: https://vis-art-application-production.herokuapp.com/artists/get_all
   */
  @GetMapping(value = { "/artists/get_all", "/artists/get_all/" })
  public List<ArtistDto> getAllArtists() {
    return artistService.getAllArtists().stream().map(a -> new ArtistDto(a))
        .collect(Collectors.toList());
  }

  /**
   * GET Request
   * example:
   * https://vis-art-application-production.herokuapp.com/artists/get_all_keys
   * returns: list of string all Artist idCodes
   */
  @GetMapping(value = { "/artists/get_all_keys", "/artists/get_all_keys/" })
  public List<String> getAllArtistKeys() {
    return artistService.getAllKeys();
  }

  /**
   * POST Request
   * example:
   * https://vis-art-application-production.herokuapp.com/artists/create
   * requestBody={
   * customerId : '321437210536819473021'
   * }
   * returns: the artist object, created on top of the given customer entity
   * 
   */
  @PostMapping(value = { "/artists/create", "/artists/create/" })
  public ArtistDto createArtist(@RequestBody MultiValueMap<String, String> map) {
    return new ArtistDto(artistService.createArtist(map.getFirst("customerId")));
  }

  /**
   * POST Request
   * example:
   * https://vis-art-application-production.herokuapp.com/artists/delete/232614890312658309127423
   * returns: true if the id is a valid artistId and the artist is deleted
   */
  @PostMapping(value = { "/artists/delete/{idCode}", "/artists/delete/{idCode}/" })
  public boolean deleteArtist(@PathVariable("idCode") String idCode) {
    return artistService.deleteArtist(idCode);
  }

  /**
   * POST Request
   * description: adds the artListing to the manager, given an {idCode} for the
   * manager id, and {listingId} for the artListingId
   * example:
   * https://vis-art-application-production.herokuapp.com/artists/add_listing/232614890312658309127423/4361279043218
   * returns: nothing
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
   * POST Request
   * description: adds the ticket to the manager, given an {idCode} for the
   * manager id, and {ticketId} for the ticketId
   * example: https://vis-art-application-production.herokuapp.com/artists/add_listing/232614890312658309127423/4361279043218
   * returns: nothing
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
   * POST Request
   * description: removes the artListing to the manager, given an {idCode} for the
   * manager id, and {listingId} for the artListingId
   * example:
   * https://vis-art-application-production.herokuapp.com/artists/add_listing/232614890312658309127423/4361279043218
   * returns: nothing
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
   * POST Request
   * description: removes the ticket to the manager, given an {idCode} for the
   * manager id, and {ticketId} for the ticketId
   * example: https://vis-art-application-production.herokuapp.com/artists/add_listing/232614890312658309127423/4361279043218
   * returns: nothing
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
