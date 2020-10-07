package com.ecse321.visart.test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.Manager;
import com.ecse321.visart.repositories.CustomerRepository;
import com.ecse321.visart.repositories.ManagerRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ManagerTest {
	
	
	 @Autowired
	  private ManagerRepository managerRepo;
	 
	
	 @Test
	 void managerCreateTest() {
		 //ID generator
		 Long l = System.currentTimeMillis();
		 
		 //Attributes 
		 String email = "blah";
		 String displayName = "Riad";
		 String username = "riadelm";
		 String password = "aNicePassword";
		 
		 //Create 
		 Manager testManager = managerRepo.createManager(""+l, email, displayName, username, password);
		 
		 //TEST if Manager was created
		 assertNotNull(testManager);
		 
		 //Print Manager
		 System.out.println("=================CREATE===============");
		 System.out.println(testManager);
		 System.out.println("=================CREATE===============");
		 
		 //Find manager
		 Manager testManager2 = managerRepo.getManager(""+l);
		 
		 //TEST if Manager was retrieved
		 assertNotNull(testManager2);
		 assertEquals(email, testManager2.getUser().getEmailAddress());
		 assertEquals(displayName, testManager2.getUser().getDisplayname());
		 assertEquals(username, testManager2.getUser().getUsername());
		 assertEquals(password, testManager2.getUser().getPassword());
		 System.out.println("=================FIND===============");
		 System.out.println(testManager2);
		 System.out.println("=================FIND===============");
		 
	 }

	 

}
