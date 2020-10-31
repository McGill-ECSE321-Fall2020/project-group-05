package com.ecse321.visart.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

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
  
  @Mock
  private ManagerRepository managerRepo;
  
  @Mock
  private EntityRepository entityRepo;
  
  @InjectMocks
  private ManagerService service;
  
  private static final String MANAGER_KEY = "MockTestManager";
  
  @BeforeEach
  public void setMockOutput() {
    // Mock the Repository methods, returning what we want to expect from the
    // database, instead of actually querying the database.
    lenient().when(managerRepo.getManager(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(MANAGER_KEY)) {
            User user = new User(MANAGER_KEY, MANAGER_KEY, MANAGER_KEY, MANAGER_KEY, MANAGER_KEY, MANAGER_KEY,
                MANAGER_KEY);
            return new Manager(invocation.getArgument(0),user);
          } else {
            return null;
          }
        });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };

    lenient().when(managerRepo.createManager(anyString(), anyString(), anyString(), anyString(),
        anyString(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
          String id = invocation.getArgument(0);
          User user = new User(id, id, id, id, id, id, id);
          Manager manager = new Manager(id, user);
          return manager;
        });

    // In the tests below, test the Service class's DATA VALIDATION on the
    // parameters that are given to it.
  }
  
  @Test
  public void testCreateManager() {
    // assertEquals(0, service.getAllUsers().size());

    String name = "Oscar";
    Manager manager = null;
    try {
      manager = service.createManager(name, name, name, name, name, name, name);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(manager);
    assertEquals(name, manager.getIdCode());
  }
  
  @Test
  public void testCreateNullManager() {
    String name = null;
    String error = null;
    Manager manager = null;
    try {
      manager = service.createManager(name, name, name, name, name, name, name);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(manager);
    assertEquals("Manager id code cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  

}
