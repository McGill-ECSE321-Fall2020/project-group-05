package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.User;
import com.ecse321.visart.repositories.CustomerRepository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerTest {
  Long l = System.currentTimeMillis();
  @Autowired
  private CustomerRepository customerRepo;

  @Test
  void customerCreateTest() {
    // ID generator
    Long l = System.currentTimeMillis();

    // Attributes
    String email = "riad.elmahmoudy@mail.mcgill.ca";
    String displayName = "Riad";
    String username = "riadelm";
    String password = "aNicePassword";

    Customer testCustomer = customerRepo.createCustomer("" + l, email, displayName, username,
        password);

    assertNotNull(testCustomer);
    assertEquals("Riad", testCustomer.getUser().getDisplayname());

    System.out.println(testCustomer);

    Customer testCustomer2 = customerRepo.getCustomer("" + l);
    assertNotNull(testCustomer2);
    assertEquals(displayName, testCustomer2.getUser().getDisplayname());
    assertEquals(username, testCustomer2.getUser().getUsername());
    assertEquals(password, testCustomer2.getUser().getPassword());
    assertEquals(email, testCustomer2.getUser().getEmailAddress());
    System.out.println(testCustomer2);
  }

}
