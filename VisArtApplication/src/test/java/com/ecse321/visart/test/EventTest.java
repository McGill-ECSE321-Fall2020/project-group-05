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
    Event e = eRepo.createEvent("02", "Ryan");
    assertNotNull(e);
    System.out.println(e);
    Event e1 = eRepo.findEvent("02");
    assertNotNull(e1);
    System.out.println(e1);
  }

}
