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

import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.User;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.CustomerRepository;
import com.ecse321.visart.service.CustomerService;


@ExtendWith(MockitoExtension.class)
public class TestCustomerService {
  
  private static String id = "123";
  private static String email = "mahinanw@gmail.com";
  private static String displayname = "mahinanw";
  private static String username = "mahinan123";
  private static String password = "password";
  private static String profilepic = "";
  private static String profileDescription = "Hi I am Mahin anw";
  
  @Mock
  private CustomerRepository customerRepo;
  
  @Mock
  private EntityRepository entityRepo;
  
  @InjectMocks
  private CustomerService service;
  
  @BeforeEach
  public void setMockOutput() {
    // Mock the Repository methods, returning what we want to expect from the
    // database, instead of actually querying the database.
    lenient().when(customerRepo.getCustomer(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(id)) {
            User user = new User(id, email, displayname, username, password, profilepic, profileDescription);
            return new Customer(invocation.getArgument(0),user);
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
        return new ArrayList<Customer>();
      } else {
       ArrayList<Customer> customersName = new ArrayList<Customer>();
       customersName.add(new Customer());
       return customersName;
      }
      
     
    });
    
    lenient().when(customerRepo.deleteCustomer(anyString())).thenAnswer((InvocationOnMock invocation) -> {
      if (invocation.getArgument(0) == id) {
            return true;
      } else {
        return false;
      }
    });

    lenient().when(customerRepo.createCustomer(anyString(), anyString(), anyString(), anyString(),
        anyString(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
          String id = invocation.getArgument(0);
          String email = invocation.getArgument(1);
          String displayname = invocation.getArgument(2);
          String username = invocation.getArgument(3);
          String password = invocation.getArgument(4);
          String profilepic = invocation.getArgument(5);
          String profileDescription = invocation.getArgument(6);
          User user = new User(id, email, displayname, username, password, profilepic, profileDescription);
          Customer customer = new Customer(id, user);
          return customer;
        });

  }
  
  @Test
  public void testCreateCustomer() {

    Customer customer = null;
    try {
      customer = service.createCustomer(email, displayname, username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      System.out.println(e.getMessage());
    }
    assertNotNull(customer);
    id = customer.getIdCode();
    assertEquals(email, customer.getUser().getEmailAddress());
  }
  
  @Test
  public void testCreateNullCustomer() {
    String name = null;
    String error = null;
    Customer customer = null;
    try {
      customer = service.createCustomer(name, name, name, name, name, name);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(customer);
    assertEquals("Customer id code cannot be empty!", error); 
  }
  
  @Test
  public void testCreateBadEmailCustomer() {
    // assertEquals(0, service.getAllUsers().size());
    String error = null;
    Customer customer = null;
    try {
      customer = service.createCustomer(email+"@.com", displayname, username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNull(customer);
    assertEquals("Email address is invalid", error);
  }
  
  
  @Test
  public void testCreateInvalidUsernamenameCustomer() {
    // assertEquals(0, service.getAllUsers().size());
    String error = null;
    Customer customer = null;
    try {
      customer = service.createCustomer(email, displayname, "hi", password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNull(customer);
    assertEquals("This User Name is invalid, must be between 5 and 25 characters!", error);
  }
  
  
  @Test
  public void testCreateInvalidDisplaynameCustomer() {
    // assertEquals(0, service.getAllUsers().size());
    String error = null;
    Customer customer = null;
    try {
      customer = service.createCustomer(email, "test", username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }
    assertNull(customer);
    assertEquals("This Display Name is invalid, must be between 5 and 25 characters!", error);
  }
  
 
  
  @Test
  public void testGetCustomer() {
    Customer customer = null;
    try {
      customer = service.createCustomer(email, displayname, username, password, profilepic, profileDescription);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(customer);
    assertEquals(id, customer.getIdCode());
    
    try {
      customer = service.getCustomer(id);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(customer);
    assertEquals(id, customer.getIdCode());
    
  }
  
  @Test
  public void testDeleteCustomer() {
    
    assertTrue(service.deleteCustomer(id));
    assertFalse(service.deleteCustomer(""));
    assertFalse(service.deleteCustomer(null));
    
  }
  

}
