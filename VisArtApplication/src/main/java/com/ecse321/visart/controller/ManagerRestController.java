package com.ecse321.visart.controller;

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

import com.ecse321.visart.dto.ManagerDto;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.service.ArtListingService;
import com.ecse321.visart.service.ManagerService;

@CrossOrigin(origins = "*")
@RestController
public class ManagerRestController {

  @Autowired
  private ManagerService service;

  @Autowired
  private ArtListingService serviceal;

  /**
   * 
   * @return
   */
  @GetMapping(value = { "/managers/get_all", "/managers/get_all/" })
  public List<ManagerDto> getAllManagers() {
    return service.getAllManagers().stream().map(m -> new ManagerDto(m))
        .collect(Collectors.toList());
  }

  /**
   * 
   * @param  aIdCode
   * @return
   */
  @GetMapping(value = { "/managers/get/{idCode}", "/managers/get/{idCode}/" })
  public ManagerDto getManager(@PathVariable("idCode") String aIdCode) {
    return new ManagerDto(service.getManager(aIdCode));
  }

  /**
   * 
   * @param  aIdCode
   * @return
   */
  @GetMapping(value = { "/managers/get_listings/{idCode}", "/managers/get_listings/{idCode}/" })
  public List<String> getManagerListings(@PathVariable("idCode") String aIdCode) {
    ManagerDto manager = new ManagerDto(service.getManager(aIdCode, true));
    return manager.getPromotedListings();
  }

  /**
   * 
   * @param  values
   * @return
   */
  @PostMapping(value = { "/managers/add_listing/{idCode}", "/managers/add_listing/{idCode}/" })
  public ManagerDto addManagerListing(@RequestBody MultiValueMap<String, String> values,
      @PathVariable("idCode") String idCode) {
    Manager manager = service.getManager(idCode);
    manager.addPromotedListing(serviceal.getArtListing(values.getFirst("listingIdCode")));
    return new ManagerDto(manager);
  }

  /**
   * 
   * @param  values
   * @param  idCode
   * @return
   */
  @PostMapping(value = {
      "/managers/remove_listing/{idCode}",
      "/managers/remove_lisiting/{idCode}/" })
  public ManagerDto deleteManagerListing(@RequestBody MultiValueMap<String, String> values,
      @PathVariable("idCode") String idCode) {
    Manager manager = service.getManager(idCode);
    manager.removePromotedListing(serviceal.getArtListing(values.getFirst("listingIdCode")));

    return new ManagerDto(manager);
  }

  /**
   * 
   * @param  values
   * @return
   */
  @PostMapping(value = { "/managers/create", "/managers/create/" })
  public ManagerDto createManager(@RequestBody MultiValueMap<String, String> values) {
    return new ManagerDto(service.createManager(
        values.getFirst("emailAddress"), values.getFirst("displayname"),
        values.getFirst("username"), values.getFirst("password"),
        values.getFirst("profilePicLink"), values.getFirst("profileDescription")));
  }

  /**
   * 
   * @param  idCode
   * @return
   */
  @PostMapping(value = { "/managers/delete/{id}", "/managers/delete/{id}/" })
  public Boolean deleteManager(@PathVariable("id") String idCode) {
    return service.deleteManager(idCode);
  }

}
