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

import com.ecse321.visart.dto.CustomerDto;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.service.CustomerService;
import com.ecse321.visart.service.ArtListingService;

@CrossOrigin(origins = "*")
@RestController
public class CustomerRestController {

  @Autowired
  private CustomerService service;

  @Autowired
  private ArtListingService alservice;
  
  @GetMapping(value = { "/customers", "/customers/" })
  public List<CustomerDto> getAllCustomers() {
    return service.getAllCustomers().stream().map(u -> new CustomerDto(u)).collect(Collectors.toList());
  }

  @GetMapping(value = { "/get_customer", "/get_customer/" })
  public CustomerDto getCustomer(@RequestParam("idCode") String aIdCode) {
    return new CustomerDto(service.getCustomer(aIdCode));
  }
  
  @GetMapping(value = { "/customer_favorite_listings", "/customer_favorite_listings" })
  public  List<String> getCustomerFavListings(@RequestParam("idCode" )String aIdCode) {
     CustomerDto customer = new CustomerDto(service.getCustomer(aIdCode,false,true));
     return customer.getFavoriteListings();
  }
  
  @PostMapping(value = {"/customers/{favorite listing}","/customers/{favoritelisting}"})
  public CustomerDto addCustomerFavListing(@RequestBody MultiValueMap<String, String> values) {
      Customer customer = service.getCustomer(values.getFirst("idCode"));
      customer.addFavoriteListing(alservice.getArtListing(values.getFirst("listingIdCode")));
      return new CustomerDto(customer);
  }
  
  @PostMapping(value = {"/customers/{favorite listing}","/customers/{favorite listing}"})
  public CustomerDto deleteCustomerFavListing(@RequestBody MultiValueMap<String, String> values) {
     Customer customer = service.getCustomer(values.getFirst("idCode"));
      customer.removeFavoriteListing(alservice.getArtListing(values.getFirst("listingIdCode")));
      return new CustomerDto(customer);
  }
  
  
  
  @GetMapping(value = { "/bought_tickets", "/bought_tickets" })
  public  List<String> getboughtTicketsList(@RequestParam("idCode" )String aIdCode) {
     CustomerDto customer = new CustomerDto(service.getCustomer(aIdCode,true,true));
     return customer.getBoughtTickets();
  }
  

  @PostMapping(value = { "/customers/{name}", "/customers/{name}/" })
  public CustomerDto createCustomer(@RequestBody MultiValueMap<String, String> values) {
    return new CustomerDto(service.createCustomer(
        values.getFirst("emailAddress"), values.getFirst("displayName"),
        values.getFirst("userName"), values.getFirst("password"),
        values.getFirst("profilePicLink"), values.getFirst("profileDescription")));
  }
  
  @PostMapping(value = {"/delete_managers/{id}","delete_manager/{id}/"})
  public Boolean deleteCustomer(@PathVariable("id") String idCode) {
    return service.deleteCustomer(idCode);
  }
  

}
