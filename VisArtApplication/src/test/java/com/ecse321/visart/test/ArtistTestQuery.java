package com.ecse321.visart.test;

import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.Artist;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.CustomerRepository;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtistTestQuery {
  @Autowired
  private ArtistRepository aRepo;

  @Autowired
  CustomerRepository cRepo;

  @Test
  @Order(1)
  void createAndQuery() {
    String id = "" + System.nanoTime();
    Artist a1 = aRepo.createArtist(id + "a", "ding@dong", "dingdong", "dingdong",
        "ding my dong", "silly.com", "I'm a doorbell");
    Artist a2 = aRepo.createArtist(id + "b", "ding@dong.net", "dingdong", "dingdong",
        "ding my dong", "silly.com", "I'm a doorbell");

    List<String> keys = aRepo.getAllKeys();
    System.out.println(keys.toString());

    aRepo.deleteArtist(a1);
    cRepo.deleteCustomer(a1.getCustomer());
    aRepo.deleteArtist(a2);
    cRepo.deleteCustomer(a2.getCustomer());
  }

  // @Test
  // @Order(2)
  void deleteAll() {
    List<String> keys = aRepo.getAllKeys();
    if (keys != null)
      for (String key : keys) {
        Artist a = aRepo.getArtist(key);
        aRepo.deleteArtist(a);
        cRepo.deleteCustomer(a.getCustomer());
      }
  }
}
