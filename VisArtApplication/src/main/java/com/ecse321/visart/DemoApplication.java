package com.ecse321.visart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

/**
 * This Demo Application displays some simple text to the root url at
 * localhost:8080. If it can connect to the database at the url specified by the
 * environment variable DATABASE_URL, then it displays a different set of text,
 * an id from the database.
 * 
 * @author Ryan Au
 *
 */
@Controller
@SpringBootApplication
public class DemoApplication {

  @RequestMapping("/")
  @ResponseBody
  String home() {
    return "Hello World! We are Vis Art!";
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}