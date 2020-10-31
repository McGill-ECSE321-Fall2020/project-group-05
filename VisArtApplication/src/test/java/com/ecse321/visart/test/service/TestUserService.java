package com.ecse321.visart.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.ecse321.visart.model.User;
import com.ecse321.visart.model.User;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.UserRepository;
import com.ecse321.visart.service.UserService;

@ExtendWith(MockitoExtension.class)
public class TestUserService {
  @Mock
  private UserRepository userRepo;

  @Mock
  private EntityRepository entityRepo;

  @InjectMocks
  private UserService service;

  private static String id = "123";
  private static String email = "johndoe@gmail.com";
  private static String displayname = "johndoe";
  private static String username = "johndoe123";
  private static String password = "password";
  private static String profilepic = "";
  private static String profileDescription = "Hi I am John Doe";
  
  private static User testUser;

  @BeforeEach
  public void setMockOutput() {
    // Mock the Repository methods, returning what we want to expect from the
    // database, instead of actually querying the database.

    lenient().when(userRepo.getUser(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(id)) {
            User user = new User(id, email, displayname, username, password, profilepic, profileDescription);
            return user;
          } else {
            return null;
          }
        });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    
    lenient().when(userRepo.deleteUser(anyString())).thenAnswer((InvocationOnMock invocation) -> {
      if (invocation.getArgument(0) == id) {
        return true;
      } else {
        return false;
      }
    });
    
    lenient().when(userRepo.deleteUser(anyString())).thenAnswer((InvocationOnMock invocation) -> {
      if (invocation.getArgument(0) == id) {
            return true;
      } else {
        return false;
      }
    });
    
    lenient().doAnswer((InvocationOnMock invocation) -> {
      testUser = invocation.getArgument(0);
      return testUser;
    }).when(userRepo).updateUser(any());
    
    
    
    lenient().when(entityRepo.findEntityByAttribute(anyString(), any(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
      
      String name = invocation.getArgument(2); //get the display name
      
      if(name.equals(displayname)||name.equals(username)) {
        return new ArrayList<User>();
      } else {
       ArrayList<User> UsersWithName = new ArrayList<User>();
       UsersWithName.add(new User());
       return UsersWithName;
      }
      
     
    });
  

    lenient().when(userRepo.createUser(anyString(), anyString(), anyString(), anyString(),
        anyString(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
          String id = invocation.getArgument(0);
          User user = new User(id, email, displayname, username, password, profilepic, profileDescription);
          return user;
        });

    // In the tests below, test the Service class's DATA VALIDATION on the
    // parameters that are given to it.
  }

  @Test
  public void testCreateUser() {
    // assertEquals(0, service.getAllUsers().size());
    User user = null;
    try {
      user = service.createUser(id, email, displayname, username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(user);
    assertEquals(id, user.getIdCode());
  }

  @Test
  public void testCreateNullUser() {
    String name = null;
    String error = null;
    User user = null;
    try {
      user = service.createUser(name, name, name, name, name, name, name);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(user);
    assertEquals("Manager id code cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateBadEmailUser() {
    // assertEquals(0, service.getAllUsers().size());
    String error = null;
    User User = null;
    try {
      User = service.createUser(id, email+"@.com", displayname, username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNull(User);
    assertEquals("Email address is invalid", error);
  }
  
  
  @Test
  public void testCreateInvalidUsernamenameUser() {
    // assertEquals(0, service.getAllUsers().size());
    String error = null;
    User User = null;
    try {
      User = service.createUser(id, email, displayname, "hi", password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNull(User);
    assertEquals("This User Name is invalid, must be between 5 and 25 characters!", error);
  }
  
  
  @Test
  public void testCreateInvalidDisplaynameUser() {
    // assertEquals(0, service.getAllUsers().size());
    String error = null;
    User User = null;
    try {
      User = service.createUser(id, email, "test", username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNull(User);
    assertEquals("This Display Name is invalid, must be between 5 and 25 characters!", error);
  }
  
  @Test
  public void testCreateDuplicateDisplaynameUser() {
    // assertEquals(0, service.getAllUsers().size());
    String error = null;
    User User = null;
    try {
      User = service.createUser(id, email, displayname+"1", username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNull(User);
    assertEquals("This Display Name is already taken!", error);
  }

  @Test
  public void testCreateDuplicateUsernameUser() {
    // assertEquals(0, service.getAllUsers().size());
    String error = null;
    User User = null;
    try {
      User = service.createUser(id, email, displayname, username+"1", password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNull(User);
    assertEquals("This Username is already taken!", error);
  }
  
  @Test
  public void testUpdatingNullUser() {
    String error = null;
    User user = null;
    try {
      user = service.updateUser(null, email, displayname, username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNull(user);
    assertEquals("User cannot be found", error);
    
  }
  
  @Test
  public void testupdateValidUserEmail() {
    String error = null;
    User user = null;
    try {
      user = service.updateUser(id, "email123@gmail.com", displayname, username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNotNull(user);
    assertEquals("email123@gmail.com", user.getEmailAddress());
    
  }
  
  @Test
  public void testupdateValidUserDisplayname() {
    String error = null;
    User user = null;
    displayname = "newdp";
    try {
      user = service.updateUser(id, email, displayname, username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    displayname = "johndoe";
    assertNotNull(user);
    assertEquals("newdp", user.getDisplayname());
    
  }
  
  @Test
  public void testUpdateValidUserUsername() {
    String error = null;
    User user = null;
    username = "username";
    try {
      user = service.updateUser(id, email, displayname, username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    username = "johndoe123";
    assertNotNull(user);
    assertEquals("username", user.getUsername());
    
  }
  
  @Test
  public void testupdateValidUserPassword() {
    String error = null;
    User user = null;
    try {
      user = service.updateUser(id, email, displayname, username, "newPassword", profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNotNull(user);
    assertEquals("newPassword", user.getPassword());
    
  }
  
  @Test
  public void testupdateValidUserDescription() {
    String error = null;
    User user = null;
    try {
      user = service.updateUser(id, email, displayname, username, password, profilepic, "profile desc");
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNotNull(user);
    assertEquals("profile desc", user.getProfileDescription());
    
  }
  
  @Test
  public void testDeleteUser() {
    assertTrue(service.deleteUser(id));
    assertFalse(service.deleteUser(""));
    assertFalse(service.deleteUser(null));
  }
  
  
  
  
  
}
