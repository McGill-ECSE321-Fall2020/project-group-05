package com.ecse321.visart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecse321.visart.dto.CustomerDto;
import com.ecse321.visart.service.CustomerService;

@CrossOrigin(origins = "*")
@RestController
public class CustomerRestController {

  @Autowired
  private CustomerService service;

  @GetMapping(value = { "/customers", "/customers/" })
  public List<CustomerDto> getAllUsers() {
    return service.getAllUsers().stream().map(u -> new CustomerDto(u)).collect(Collectors.toList());
  }

  @PostMapping(value = { "/customers/{name}", "/customers/{name}/" })
  public CustomerDto createUser(@PathVariable("name") String name) {
    return new CustomerDto(service.createCustomer(name, "", "", "", "", "", ""));
  }
}
