package com.ecse321.visart.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.awt.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.User;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.ManagerRepository;
import com.ecse321.visart.service.ManagerService;

@ExtendWith(MockitoExtension.class)
public class TestManagerService {
  
  private static String id = "123";
  private static String email = "johndoe@gmail.com";
  private static String displayname = "johndoe";
  private static String username = "johndoe123";
  private static String password = "password";
  private static String profilepic = "";
  private static String profileDescription = "Hi I am John Doe";
  
  @Mock
  private ManagerRepository managerRepo;
  
  @Mock
  private EntityRepository entityRepo;
  
  @InjectMocks
  private ManagerService service;
  
 
  
  @BeforeEach
  public void setMockOutput() {
    // Mock the Repository methods, returning what we want to expect from the
    // database, instead of actually querying the database.
    lenient().when(managerRepo.getManager(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(id)) {
            User user = new User(id, email, displayname, username, password, profilepic, profileDescription);
            return new Manager(invocation.getArgument(0),user);
          } else {
            return null;
          }
        });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    
    lenient().when(entityRepo.findEntityByAttribute(anyString(), any(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
      
      String name = invocation.getArgument(2); //get the display name
      
      if(name.equals(displayname)||name.equals(username)) {
        return new ArrayList<Manager>();
      } else {
       ArrayList<Manager> managersWithName = new ArrayList<Manager>();
       managersWithName.add(new Manager());
       return managersWithName;
      }
      
     
    });
    
    lenient().when(managerRepo.deleteManager(anyString())).thenAnswer((InvocationOnMock invocation) -> {
      if (invocation.getArgument(0) == id) {
            return true;
      } else {
        return false;
      }
    });

    lenient().when(managerRepo.createManager(anyString(), anyString(), anyString(), anyString(),
        anyString(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
          id = invocation.getArgument(0);
          String email = invocation.getArgument(1);
          String displayname = invocation.getArgument(2);
          String username = invocation.getArgument(3);
          String password = invocation.getArgument(4);
          String profilepic = invocation.getArgument(5);
          String profileDescription = invocation.getArgument(6);
          User user = new User(id, email, displayname, username, password, profilepic, profileDescription);
          Manager manager = new Manager(id, user);
          return manager;
        });

    // In the tests below, test the Service class's DATA VALIDATION on the
    // parameters that are given to it.
  }
  
  @Test
  public void testCreateManager() {
    // assertEquals(0, service.getAllUsers().size());

    Manager manager = null;
    try {
      manager = service.createManager(email, displayname, username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      System.out.println(e.getMessage());
    }
    assertNotNull(manager);
    id = manager.getIdCode();
    assertEquals(email, manager.getUser().getEmailAddress());
  }
  
  @Test
  public void testCreateNullManager() {
    String name = null;
    String error = null;
    Manager manager = null;
    try {
      manager = service.createManager(name, name, name, name, name, name);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(manager);
    assertEquals("Email address is invalid", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateBadEmailManager() {
    // assertEquals(0, service.getAllUsers().size());
    String error = null;
    Manager manager = null;
    try {
      manager = service.createManager(email+"@.com", displayname, username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNull(manager);
    assertEquals("Email address is invalid", error);
  }
  
  
  @Test
  public void testCreateInvalidUsernamenameManager() {
    // assertEquals(0, service.getAllUsers().size());
    String error = null;
    Manager manager = null;
    try {
      manager = service.createManager(email, displayname, "hi", password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNull(manager);
    assertEquals("This User Name is invalid, must be between 5 and 25 characters!", error);
  }
  
  
  @Test
  public void testCreateInvalidDisplaynameManager() {
    // assertEquals(0, service.getAllUsers().size());
    String error = null;
    Manager manager = null;
    try {
      manager = service.createManager(email, "test", username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNull(manager);
    assertEquals("This Display Name is invalid, must be between 5 and 25 characters!", error);
  }
  
  @Test
  public void testCreateDuplicateDisplaynameManager() {
    // assertEquals(0, service.getAllUsers().size());
    String error = null;
    Manager manager = null;
    try {
      manager = service.createManager( email, displayname+"1", username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNull(manager);
    assertEquals("This Display Name is already taken!", error);
  }

  @Test
  public void testCreateDuplicateUsernameManager() {
    // assertEquals(0, service.getAllUsers().size());
    String error = null;
    Manager manager = null;
    try {
      manager = service.createManager(email, displayname, username+"1", password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNull(manager);
    assertEquals("This Username is already taken!", error);
  }
  
  
  
  @Test
  public void testGetManager() {
    Manager manager = null;
    try {
      manager = service.createManager(email, displayname, username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(manager);
    assertEquals(id, manager.getIdCode());
    
    try {
      manager = service.getManager(id);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(manager);
    assertEquals(id, manager.getIdCode());
    
  }
  
  @Test
  public void testDeleteManager() {
    
    assertTrue(service.deleteManager(id));
    assertFalse(service.deleteManager(""));
    assertFalse(service.deleteManager(null));
    
  }
  

}
