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
import com.ecse321.visart.service.CustomerService;

@CrossOrigin(origins = "*")
@RestController
public class CustomerRestController {

  @Autowired
  private CustomerService service;

  /**
   * 
   * @return
   */
  @GetMapping(value = { "/customers/get_all", "/customers/get_all/" })
  public List<CustomerDto> getAllCustomers() {
    return service.getAllCustomers().stream().map(u -> new CustomerDto(u))
        .collect(Collectors.toList());
  }

  /**
   * 
   * @param  aIdCode
   * @return
   */
  @GetMapping(value = { "/customers/get/{idCode}", "/customers/get/{idCode}/" })
  public CustomerDto getCustomer(@PathVariable("idCode") String aIdCode) {
    return new CustomerDto(service.getCustomer(aIdCode));
  }

  /**
   * 
   * @param  aIdCode
   * @return
   */
  @GetMapping(value = {
      "/customers/get_favorite_listings/{idCode}",
      "/customers/get_favorite_listings/{idCode}" })
  public List<String> getCustomerFavoriteListings(@RequestParam("idCode") String aIdCode) {
    CustomerDto customer = new CustomerDto(service.getCustomer(aIdCode, false, true));
    return customer.getFavoriteListings();
  }

  /**
   * 
   * @param  idCode
   * @param  values
   * @return
   */
  @PostMapping(value = {
      "/customers/add_favorite_listing/{idCode}",
      "/customers/add_favorite_listing/{idCode}/" })
  public CustomerDto addCustomerFavListing(@PathVariable("idCode") String idCode,
      @RequestBody MultiValueMap<String, String> values) {
    String listingId = values.getFirst("listingIdCode");
    service.addfavoriteList(idCode, listingId);
    return new CustomerDto(service.getCustomer(idCode));
  }

  /**
   * 
   * @param  values
   * @return
   */
  @PostMapping(value = {
      "/customers/remove_favorite_listing/{idCode}",
      "/customers/remove_favorite_listing/{idCode}/" })
  public CustomerDto deleteCustomerFavListing(@RequestBody MultiValueMap<String, String> values,
      @PathVariable("idCode") String idCode) {
    String listingId = values.getFirst("listingIdCode");
    service.removefavoriteList(idCode, listingId);
    return new CustomerDto(service.getCustomer(idCode));
  }

  /**
   * 
   * @param  aIdCode
   * @return
   */
  @GetMapping(value = { "/customers/{idCode}", "/customers/{idCode}/" })
  public List<String> getboughtTicketsList(@PathVariable("idCode") String aIdCode) {
    CustomerDto customer = new CustomerDto(service.getCustomer(aIdCode, true, true));
    return customer.getBoughtTickets();
  }

  /**
   * 
   * @param  values
   * @return
   */
  @PostMapping(value = { "/customers/create", "/customers/create/" })
  public CustomerDto createCustomer(@RequestBody MultiValueMap<String, String> values) {
    return new CustomerDto(service.createCustomer(
        values.getFirst("emailAddress"), values.getFirst("displayname"),
        values.getFirst("username"), values.getFirst("password"),
        values.getFirst("profilePicLink"), values.getFirst("profileDescription")));
  }

  /**
   * 
   * @param  idCode
   * @return
   */
  @PostMapping(value = { "/customers/delete/{idCode}", "/customers/delete/{idCode}/" })
  public Boolean deleteCustomer(@PathVariable("id") String idCode) {
    return service.deleteCustomer(idCode);
  }

}
