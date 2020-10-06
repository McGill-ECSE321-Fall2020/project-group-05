package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.Manager;
import com.ecse321.visart.repositories.ManagerRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class VisArtTest {

  @Autowired
  private ManagerRepository mr;

  @Test
  void test() {
    Long l = System.currentTimeMillis();
    Manager e = mr.createManager(""+l, "daniel.bucci@ggmaol.com", "db", "db", "pog");
    assertNotNull(e);
    System.out.println(e);
    Manager e1 = mr.getManager(""+l);
    assertNotNull(e1);
    System.out.println(e1);
  }

}