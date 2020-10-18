package com.ecse321.visart.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

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

  private static final String USER_KEY = "MockTestUser";

  @BeforeEach
  public void setMockOutput() {
    // Mock the Repository methods, returning what we want to expect from the
    // database, instead of actually querying the database.

    lenient().when(userRepo.getUser(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(USER_KEY)) {
            User user = new User(USER_KEY, USER_KEY, USER_KEY, USER_KEY, USER_KEY, USER_KEY,
                USER_KEY);
            return user;
          } else {
            return null;
          }
        });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };

    lenient().when(userRepo.createUser(anyString(), anyString(), anyString(), anyString(),
        anyString(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
          String id = invocation.getArgument(0);
          User user = new User(id, id, id, id, id, id, id);
          return user;
        });

    // In the tests below, test the Service class's DATA VALIDATION on the
    // parameters that are given to it.
  }

  @Test
  public void testCreateUser() {
    // assertEquals(0, service.getAllUsers().size());

    String name = "Oscar";
    User user = null;
    try {
      user = service.createUser(name, name, name, name, name, name, name);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(user);
    assertEquals(name, user.getIdCode());
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
    assertEquals("User id code cannot be empty!", error); // expected error message for service data validation.
  }
}
