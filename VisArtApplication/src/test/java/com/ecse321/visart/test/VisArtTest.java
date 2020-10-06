package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.Manager;
import com.ecse321.visart.repositories.ManagerRepository;
import com.ecse321.visart.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class VisArtTest {

  @Autowired
  private ManagerRepository mr;
  private UserRepository ur;

  @Test
  void test() {

    Manager e = mr.createManager("123", "daniel.bucci@ggmaol.com", "db", "db", "pog");
    
    assertNotNull(e);
    System.out.println(e);
    Manager e1 = mr.getManager("123");
    assertNotNull(e1);
    System.out.println(e1);
  }

}