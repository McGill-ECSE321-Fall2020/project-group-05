package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.Artist;
import com.ecse321.visart.repositories.ArtistRepository;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtistTest {
	Long l = System.currentTimeMillis();
	
	//Attributes
	String aEmailAddress = "timcook@gmail.com";
	String aDisplayname = "Tim Cook";
	String  aUsername = "timcook56";
	String aPassword = "apple123";

	@Autowired
	private ArtistRepository aRepo;

	@Test
	void testEntry() {
		
		
		//Create
		Artist testArtist = aRepo.createArtist(""+l, aEmailAddress, aDisplayname, aUsername, aPassword);
		
		//Test if Artist was created
		assertNotNull(testArtist);
		
		//Print Artist
		System.out.println("============================");
		System.out.println(testArtist);
		System.out.println("============================");
	}

	
	@Test
	void testGet() {
		
		Artist testArtist2 = aRepo.getArtist(""+l);
		assertNotNull(testArtist2);
		assertEquals(aEmailAddress, testArtist2.getCustomer().getUser().getEmailAddress());
		assertEquals(aDisplayname, testArtist2.getCustomer().getUser().getDisplayname());
		assertEquals(aUsername, testArtist2.getCustomer().getUser().getUsername());
		assertEquals(aPassword, testArtist2.getCustomer().getUser().getPassword());
		
		
		System.out.println("============================");
		System.out.println(testArtist2.getCustomer().getUser().getUsername());
		System.out.println("============================");
	}

}

