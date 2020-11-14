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

import com.ecse321.visart.dto.CredentialsDto;
import com.ecse321.visart.dto.UserDto;
import com.ecse321.visart.model.User;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.service.UserService;

@CrossOrigin(origins = "*")
@RestController
public class UserRestController {

  @Autowired
  private UserService service;

  @Autowired
  private EntityRepository entityRepo;

  /**
   * 
   * @return
   */
  @GetMapping(value = { "/users/get_all", "/users/get_all/" })
  public List<UserDto> getAllUsers() {
    return service.getAllUsers().stream().map(u -> new UserDto(u)).collect(Collectors.toList());
  }

  /**
   * 
   * @param  aIdCode
   * @return
   */
  @GetMapping(value = { "/users/get/{idCode}", "/users/get/{idCode}/" })
  public UserDto getUser(@PathVariable("idCode") String aIdCode) {
    return new UserDto(service.getUser(aIdCode));
  }

  /**
   * 
   * @param  values
   * @return
   */
  @PostMapping(value = { "/users/create", "/users/create/" })
  public UserDto createUser(@RequestBody MultiValueMap<String, String> values) {
    return new UserDto(service.createUser(
        values.getFirst("emailAddress"), values.getFirst("displayname"),
        values.getFirst("username"), values.getFirst("password"),
        values.getFirst("profilePicLink"), values.getFirst("profileDescription")));
  }

  /**
   * 
   * requestBody = {
   *  emailAddress : string
   *  displayname : string
   *  username : string
   *  profilePicLink : string
   *  profileDescription : string
   * }
   */
  @PostMapping(value = { "/users/update/{idCode}", "/users/update/{idCode}/" })
  public UserDto updateUser(@RequestBody MultiValueMap<String, String> values,
      @PathVariable("idCode") String idCode) {
    return new UserDto(service.updateUser(idCode,
        values.getFirst("emailAddress"), values.getFirst("displayname"),
        values.getFirst("username"), values.getFirst("password"),
        values.getFirst("profilePicLink"), values.getFirst("profileDescription")));
  }

  /**
   * 
   * @param  idCode
   * @return
   */
  @PostMapping(value = { "/users/delete/{id}", "/users/delete/{id}/" })
  public Boolean deleteUser(@PathVariable("id") String idCode) {
    return service.deleteUser(idCode);
  }

  @PostMapping(value = { "/users/email_login", "/users/email_login/" })
  public CredentialsDto loginUserByEmail(@RequestBody MultiValueMap<String, String> values) {
    String email = values.getFirst("emailAddress");
    String aPassword = values.getFirst("password");
    if (service.loginUserByEmail(email, aPassword)) {
      List<User> users = entityRepo.findEntityByAttribute("emailAddress", User.class, email);
      String id = users.get(0).getIdCode();
      return service.generateCredentials(id);
    }
    return new CredentialsDto();

  }
  
  @PostMapping(value = { "/users/login", "/users/login/" })
  public CredentialsDto loginUser(@RequestBody MultiValueMap<String, String> values) {
    String username = values.getFirst("username");
    String aPassword = values.getFirst("password");
    if (service.loginUser(username, aPassword)) {
      List<User> users = entityRepo.findEntityByAttribute("username", User.class, username);
      String id = users.get(0).getIdCode();
      return service.generateCredentials(id);
    }
    return new CredentialsDto();

  }
}
