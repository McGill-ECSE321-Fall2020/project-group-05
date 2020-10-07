package com.ecse321.visart.test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.Ticket;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.repositories.ArtPieceRepository;
import com.ecse321.visart.repositories.CustomerRepository;
import com.ecse321.visart.repositories.ManagerRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.ArtOrderRepository;
import com.ecse321.visart.repositories.TicketRepository;
import com.ecse321.visart.repositories.ArtListingRepository;



@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtPieceTest {
	
	//ID generator
	Long l = System.currentTimeMillis();
	
	@Autowired
	private ArtPieceRepository apRepo;
	
	String aEmailAddress = "timcook@gmail.com";
	String aDisplayname = "Tim Cook";
	String  aUsername = "timcook56";
	String aPassword = "apple123";

	//Creating instances

	User aUser = new User(""+l+1,aEmailAddress, aDisplayname, aUsername, aPassword);
	User aUser2 = new User(""+l+4,aEmailAddress, aDisplayname, aUsername, aPassword);

	Manager manager = new Manager("" + l, aUser2); 
	Customer customer = new Customer(""+l+2,aUser);

	

	Artist artist = new Artist(""+l+3, customer);
	ArtListing artListing = new ArtListing(ArtListing.PostVisibility.Public, ""+l+5,manager, artist);
	
	//attributes
	String address = "124 Maywood";
	
	
	
	@Test
	void createEntry(){
		
		//Create
		ArtPiece artPieceTest = apRepo.createArtPiece(PieceLocation.AtGallery, address, ""+l, artListing);
		
		//Test if ArtPiece was created
		assertNotNull(artPieceTest);
		
		// Print Art Listing
		System.out.println("============================");
		System.out.println(artPieceTest);
		System.out.println("============================");
	}
	
	
	@Test
	void testGet() {
		
		
		//Find artPieceTest
		ArtPiece artPieceTest2  = apRepo.getArtPiece(""+l);
		
		//Test if ArtPiece was retrieved
		assertNotNull(artPieceTest2);
		assertEquals(address, artPieceTest2.getAddressLocation());
		assertEquals(artListing, artPieceTest2.getArtListing());
		
		System.out.println("============================");
		System.out.println(artPieceTest2);
		System.out.println("============================");
		
		
		
	}
	
	
	

}

