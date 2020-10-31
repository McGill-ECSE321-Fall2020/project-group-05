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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecse321.visart.dto.ManagerDto;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.service.ManagerService;

@CrossOrigin(origins = "*")
@RestController
public class ManagerRestController {
  
  @Autowired
  private ManagerService service;
  
  @GetMapping(value = { "/managers", "/managers/" })
  public List<ManagerDto> getAllManagers() {
    return service.getAllUsers().stream().map(m -> new ManagerDto(m)).collect(Collectors.toList());
  }
    
  @GetMapping(value = { "/get_manager", "/get_manager/" })
  public ManagerDto getManager(@RequestParam("idCode" )String aIdCode) {
    return new ManagerDto(service.getManager(aIdCode));
  }
  
  @GetMapping(value = { "/manager_listings", "/manager_listings" })
  public  List<String> getManagerListings(@RequestParam("idCode" )String aIdCode) {
     ManagerDto manager = new ManagerDto(service.getManager(aIdCode,true));
     return manager.getPromotedListings();
  }
  
//  @PostMapping(value = {"/managers/{listing}","/managers/{listing}"})
//  public ManagerDto addManagerListing(@RequestBody MultiValueMap<String, String> values) {
//      Manager manager = service.getManager(values.getFirst("idCode"));
//      
//      return new ManagerDto();
//  }
  
  @PostMapping(value = { "/managers/{name}", "/managers/{name}/" })
  public ManagerDto createManager(@RequestBody MultiValueMap<String, String> values) {
    return new ManagerDto(service.createManager(values.getFirst("idCode"), 
        values.getFirst("emailAddress"), values.getFirst("displayName"),
        values.getFirst("userName"), values.getFirst("password"),
        values.getFirst("profilePicLink"), values.getFirst("profileDescription")));
  }
  
  @PostMapping(value = {"/delete_managers/{id}","delete_manager/{id}/"})
  public Boolean deleteManager(@PathVariable("id") String idCode) {
    return service.deleteManager(idCode);
  }
  

}
