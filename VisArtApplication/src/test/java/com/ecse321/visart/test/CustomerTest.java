package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CustomerTest {
  @Autowired
  private CustomerRepository customerRepo;

  private static String email;

  private static String displayName;

  private static String username;

  private static String password;
  
  // Pseudo-random ID for tested entities
  static Long l = System.currentTimeMillis();

  @Test
  @Order(1)
  void init() {
    // Attributes
    email = "riad.elmahmoudy@mail.mcgill.ca";
    displayName = "Riad";
    username = "riadelm";
    password = "aNicePassword";
  }

  @Test
  @Order(2)
  void testCreate1() {
    Customer testCustomer = customerRepo.createCustomer("" + l, email, displayName, username,
        password);

    assertNotNull(testCustomer);
    assertEquals("Riad", testCustomer.getUser().getDisplayname());

    System.out.println(testCustomer);
  }

  @Test
  @Order(3)
  void testGet1() {

    Customer testCustomer2 = customerRepo.getCustomer("" + l);
    assertNotNull(testCustomer2);
    assertEquals(displayName, testCustomer2.getUser().getDisplayname());
    assertEquals(username, testCustomer2.getUser().getUsername());
    assertEquals(password, testCustomer2.getUser().getPassword());
    assertEquals(email, testCustomer2.getUser().getEmailAddress());
    System.out.println(testCustomer2);

  }
}
