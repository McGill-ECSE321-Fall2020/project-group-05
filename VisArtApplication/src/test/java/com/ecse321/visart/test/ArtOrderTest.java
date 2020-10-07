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
	private ArtPieceRepository apRepo;
	private CustomerRepository cRepo;
	private TicketRepository tRepo;
	private ArtListingRepository alRepo;
	private ManagerRepository mRepo;
	private ArtistRepository aRepo;
	
	//ID GENERATOR
	Long l = System.currentTimeMillis();

	
	
	//Create manager, artist, artListing, artPiece, customer and ticket instance
	Manager manager = mRepo.createManager(""+l, "timcook@gmail.com", "Tim Cook", "timcook56", "apple123");
	Artist artist = aRepo.createArtist(""+l+1, "steve@gmail.com", "Steve Jobs", "steve56", "apple1234");
	ArtListing artListing = alRepo.createArtListing(ArtListing.PostVisibility.Public, ""+l+2, manager,artist);
	ArtPiece artPiece = apRepo.createArtPiece(PieceLocation.Offsite, "346STLOUIS", ""+l+3, artListing);
	Customer customer = cRepo.createCustomer(""+l+4, "KPP@GMAIL.COM", "KYARY", "KPP123", "japan56");
	Ticket ticket = tRepo.createTicket(true, 600, ""+l+5, false, PieceLocation.Offsite, "123THORNCREST", "WOW12", ""+l+6, artPiece, customer, artist);
	
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

	
	
	
	
	
	
	
	
	
	
	
	
	
