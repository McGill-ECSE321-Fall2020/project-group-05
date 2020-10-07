package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.Customer;
import com.ecse321.visart.repositories.CustomerRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerTest {
	 Long l = System.currentTimeMillis();
	 @Autowired
	  private CustomerRepository customerRepo;
	 
	 @Test
	 void customerCreateTest() {
		 Customer testCustomer = customerRepo.createCustomer(""+l, "riad.elmahmoudy@mail.mcgill.ca", "Riad", "riadelm", "aNicePassword");
		 assertNotNull(testCustomer);
		 assertEquals("Riad",testCustomer.getUser().getDisplayname());
		 System.out.println(testCustomer);
		 Customer testCustomer2 = customerRepo.getCustomer(""+l);
		 assertNotNull(testCustomer2);
		 assertEquals("Riad",testCustomer2.getUser().getDisplayname());
		 System.out.println(testCustomer2);
	 }
	 
}
