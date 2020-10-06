package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EventTest {

  @Autowired
  private EventRepository eRepo;

  @Test
  void test() {
    Long l = System.currentTimeMillis();
    Event e = eRepo.createEvent(""+l, "Ryan");
    assertNotNull(e);
    System.out.println(e);
    Event e1 = eRepo.findEvent(""+l);
    assertNotNull(e1);
    System.out.println(e1);
  }

}
