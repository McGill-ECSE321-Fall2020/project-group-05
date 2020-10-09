/**
 * @author mahin anwar
 * @author riad el mahmoudy
 */
package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.User;
import com.ecse321.visart.repositories.UserRepository;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTest {

  @Autowired
  private UserRepository userRepo;

  private String email;

  private String displayName;

  private String username;

  private String password;

  private static String profilePic;

  private static String profileDesc;

  static private User user2;

  // Pseudo-random ID for tested entities
  static Long l = System.currentTimeMillis();

  @Test
  @Order(1)
  void init() {
    // Attributes
    email = "blah";
    displayName = "Riad";
    username = "riadelm";
    password = "aNicePassword";
    profilePic = "Bezos.jpg";
    profileDesc = "ACAB, billionaires shouldnt exist";
  }

  @Test
  @Order(2)
  void testCreate1() {
    // Create
    User testUser = userRepo.createUser("" + l, email, displayName, username, password, profilePic,
        profileDesc);

    // TEST if User was created
    assertNotNull(testUser);

    // Print Manager
    System.out.println("=================CREATE===============");
    System.out.println(testUser);
    System.out.println("=================CREATE===============");
  }
	@Test
	  @Order(3)
	  void testGet1() {

	    user2 = userRepo.getUser("" + l);
	    assertNotNull(user2);
	    assertEquals(displayName, user2.getDisplayname());
	    assertEquals(username, user2.getUsername());
	    assertEquals(password, user2.getPassword());
	    assertEquals(email, user2.getEmailAddress());
	    assertEquals(profilePic, user2.getProfilePicLink());
	    assertEquals(profileDesc, user2.getProfileDescription());
	    
	    System.out.println(user2);

	  } 
		void testUpdate1() {
	    // Find user
	    user2 = userRepo.getUser("" + l);
	    
	    //Update Customer
	    user2.setDisplayname("newDisplayNameUser");
	    userRepo.updateUser(user2);
	     
	    user2 = userRepo.getUser(""+l);

	    // TEST if Customer was retrieved and if properly updated
	    assertNotNull(user2);
	    assertEquals(email, user2.getEmailAddress());
	    assertEquals("newDisplayNameUser", user2.getDisplayname());
	    assertEquals(username, user2.getUsername());
	    assertEquals(password, user2.getPassword());
	    assertEquals(profilePic, user2.getProfilePicLink());
	    assertEquals(profileDesc, user2.getProfileDescription());

	    System.out.println("=================UPDATE===============");
	    System.out.println(user2);
	    System.out.println("=================UPDATE===============");
	  }
		
		@Test
	  @Order(5)
	  void testDelete1() {
	    userRepo.deleteUser(user2);
	    assertEquals(null, userRepo.getUser("" + l));
	  }

}
