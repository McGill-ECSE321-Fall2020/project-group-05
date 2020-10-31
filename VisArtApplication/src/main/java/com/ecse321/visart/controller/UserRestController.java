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
import com.ecse321.visart.dto.UserDto;
import com.ecse321.visart.service.UserService;

@CrossOrigin(origins = "*")
@RestController
public class UserRestController {

  @Autowired
  private UserService service;

  @GetMapping(value = { "/users", "/users/" })
  public List<UserDto> getAllUsers() {
    return service.getAllUsers().stream().map(u -> new UserDto(u)).collect(Collectors.toList());
  }

  @PostMapping(value = { "/users/{name}", "/users/{name}/" })
  public UserDto createUser(@RequestBody MultiValueMap<String, String> values) {
    return new UserDto(service.createUser(values.getFirst("idCode"), 
        values.getFirst("emailAddress"), values.getFirst("displayName"),
        values.getFirst("userName"), values.getFirst("password"),
        values.getFirst("profilePicLink"), values.getFirst("profileDescription")));
  }
  
}
