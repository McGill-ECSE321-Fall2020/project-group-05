package com.ecse321.visart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.ecse321.visart.service.ArtistService;

@CrossOrigin(origins = "*")
@RestController
public class ArtistRestController {

  @Autowired
  private ArtistService artistService;

}
