package com.ecse321.visart.test;

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
import org.springframework.transaction.annotation.Transactional;

public class CustomerTest {
	
	Long l = System.currentTimeMillis();
	String aEmailAddress = "SEKAOWA@ICLOUD.COM";
	String aDisplayname = "FUKASE";
	String  aUsername = "fukase123";
	String aPassword = "japan12";
	
	
	
	
	@Autowired
	private CustomerRepository cRepo;
	
	@Test
	void entryTest(){
		
		//Create
		Customer TestCustomer = cRepo.createCustomer(""+l, aEmailAddress, aDisplayname, aUsername, aPassword);
		
		//Print customer
		System.out.println("============================");
		System.out.println(TestCustomer);
		System.out.println("============================");
		
	}
	
	@Test
	void getTest() {
		
		//Find customer
		Customer TestCustomer2 = cRepo.getCustomer(""+l);
		
		assertNotNull(TestCustomer2);
		assertEquals(aEmailAddress, TestCustomer2.getUser().getEmailAddress());
		assertEquals(aDisplayname, TestCustomer2.getUser().getDisplayname());
		assertEquals(aUsername, TestCustomer2.getUser().getUsername());
		assertEquals(aPassword, TestCustomer2.getUser().getPassword());
		
		//Print customer
		System.out.println("============================");
		System.out.println(TestCustomer2);
		System.out.println("============================");
		
		
		
		
	}
	
	

	
}
