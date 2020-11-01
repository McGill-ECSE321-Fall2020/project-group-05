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

@CrossOrigin(origins = "*")
@RestController
public class CustomerRestController {

  @Autowired
  private CustomerService service;

  @GetMapping(value = { "/customers", "/customers/" })
  public List<CustomerDto> getAllCustomers() {
    return service.getAllUsers().stream().map(u -> new CustomerDto(u)).collect(Collectors.toList());
  }

  @GetMapping(value = { "/get_customer", "/get_customer/" })
  public CustomerDto getCustomer(@RequestParam("idCode") String aIdCode) {
    return new CustomerDto(service.getCustomer(aIdCode));
  }

  @PostMapping(value = { "/customers/{name}", "/customers/{name}/" })
  public CustomerDto createCustomer(@RequestBody MultiValueMap<String, String> values) {
    return new CustomerDto(service.createCustomer(values.getFirst("idCode"),
        values.getFirst("emailAddress"), values.getFirst("displayName"),
        values.getFirst("userName"), values.getFirst("password"),
        values.getFirst("profilePicLink"), values.getFirst("profileDescription")));
  }
  
  @PostMapping(value = {"/delete_managers/{id}","delete_manager/{id}/"})
  public Boolean deleteCustomer(@PathVariable("id") String idCode) {
    return service.deleteCustomer(idCode);
  }
  

}
