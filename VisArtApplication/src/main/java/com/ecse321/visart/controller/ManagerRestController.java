package com.ecse321.visart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecse321.visart.dto.ManagerDto;
import com.ecse321.visart.service.ManagerService;

@CrossOrigin(origins = "*")
@RestController
public class ManagerRestController {
  
  @Autowired
  private ManagerService service;
  
  @GetMapping(value = { "/managers", "/managers/" })
  public List<ManagerDto> getAllUsers() {
    return service.getAllUsers().stream().map(m -> new ManagerDto(m)).collect(Collectors.toList());
  }
  
  @PostMapping(value = { "/managers/{name}", "/managers/{name}/" })
  public ManagerDto createUser(@PathVariable("name") String name) {
    return new ManagerDto(service.createManager(name, "", "", "", "", "", ""));
  }

}
