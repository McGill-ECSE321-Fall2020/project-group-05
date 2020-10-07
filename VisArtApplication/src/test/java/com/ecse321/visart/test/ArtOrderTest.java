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
import com.ecse321.visart.model.ArtListing.PostVisibility;
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
public class ArtOrderTest {
	
	
	@Autowired
	private ArtOrderRepository aoRepo;
	
	//ID GENERATOR
	Long l = System.currentTimeMillis();
	
	//Attributes
	String aEmailAddress = "timcook@gmail.com";
	String aDisplayname = "Tim Cook";
	String  aUsername = "timcook56";
	String aPassword = "apple123";

	//Creating instances
	User aUser = new User(""+l+1,aEmailAddress, aDisplayname, aUsername, aPassword);
	Manager manager = new Manager("" + l, aUser); 
	Customer customer = new Customer(""+l+2,aUser);
	Artist artist = new Artist(""+l+3, customer);
	ArtListing artListing = new ArtListing(ArtListing.PostVisibility.Public, ""+l+5,manager, artist);
	ArtPiece artPiece = new ArtPiece(PieceLocation.AtGallery, "112MAYWOOD", ""+l+4,artListing);
	ArtOrder artOrder = new ArtOrder(true, PieceLocation.AtGallery, "567Sugar", "DJ21I",""+l+7,artPiece,true,500,""+l+8, customer, artist);
	Ticket ticket = new Ticket(true, 300, ""+l+6,artOrder,customer,artist);
	
	
	//Attributes for artOrder1
	String address = "123 STLOUIS";
	String tracker = "AZA123";
	
	//Attributes for artOrder2
	String address2 = "125 ALSTON";
	String tracker2 = "AZA124";
	
	
	@Test
	void testEntry1(){
		
		//Create
		ArtOrder artOrder1 = aoRepo.createArtOrder(true, ArtPiece.PieceLocation.AtGallery, address, tracker, ""+l+7, artPiece, true, 200, ""+l+8, customer, artist);
		
		//Test if Art Order was created
		assertNotNull(artOrder1);
				
		//Print Art Order
		System.out.println("============================");
		System.out.println(artOrder1);
		System.out.println("============================");
			
		

	}
	
	@Test
	void testEntry2() {
		
		//Create
		ArtOrder artOrder2 = aoRepo.createArtOrder(false, ArtPiece.PieceLocation.Offsite, address2, tracker2, ""+l+9, artPiece, ticket);
		
		//Test if Art Order was created
		assertNotNull(artOrder2);
		
		//Print Art Order
		System.out.println("============================");
		System.out.println(artOrder2);
		System.out.println("============================");
	
	}
	
	
	
	@Test
	void testGet1( ) {
		
		//Find artOrder1
		ArtOrder artOrder3 = aoRepo.getArtOrder(""+l+7);
		
		//Test if Art Order was retrieved
		assertNotNull(artOrder3);
		assertEquals(address, artOrder3.getTargetAddress());
		assertEquals(tracker, artOrder3.getDeliveryTracker());
		
		// Print Art Order
		System.out.println("============================");
		System.out.println(artOrder3);
		System.out.println("============================");
		
		
		
	}
	
	@Test
	void testGet2( ) {
		//Find artOrder2
		ArtOrder artOrder4 = aoRepo.getArtOrder(""+l+9);
		
		//Test if Art Order was retrieved
		assertNotNull(artOrder4);
		assertEquals(address2, artOrder4.getTargetAddress());
		assertEquals(tracker2, artOrder4.getDeliveryTracker());
		
		// Print Art Order
		System.out.println("============================");
		System.out.println(artOrder4);
		System.out.println("============================");
		
		
		
	}
	
	
	
	
}

	
	
	
	
	
	
	
	
	
	
	
	
	
