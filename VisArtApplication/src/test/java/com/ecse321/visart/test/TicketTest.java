package com.ecse321.visart.test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.model.Ticket;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ArtOrderRepository;
import com.ecse321.visart.repositories.ArtPieceRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.CustomerRepository;
import com.ecse321.visart.repositories.ManagerRepository;
import com.ecse321.visart.repositories.TicketRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TicketTest {
	
	 @Autowired
	  private TicketRepository ticketRepo;
	 
	 @Autowired
	 private CustomerRepository customerRepo;
	 
	 @Autowired
	 private ArtistRepository artistRepo;
	 
	 @Autowired
	 private ManagerRepository managerRepo;
	 
	 @Autowired
	 private ArtListingRepository artlistingRepo;
	 
	 @Autowired
	 private ArtPieceRepository artpieceRepo;
	 
	 @Autowired
	 private ArtOrderRepository artorderRepo;
	//ID generator
	 Long l = System.currentTimeMillis();
	 
	 //Attributes 
	 String email;
	 String displayName;
	 String username;
	 String password;
	 String id = "test"+l;
	 boolean isAPaymentConfirmed;
	 double aPaymentAmount;
	 User aUser;
	 User aUser2;
	 User aUser3;
	 Customer aCustomer;
	 Customer aCustomer2;
	 Artist anArtist;
	 Manager aManager;
	 ArtListing aListing;
	 ArtPiece anArtPiece;
	 ArtOrder aOrder;
	// ArtOrder anArtOrder;
	   //specific for second order
	 boolean isDeliveredForOrder;
	 String aTargetAddressForOrder;
	 String aDeliveryTrackerForOrder ;
	 String aIdCodeForOrder;
	 
	 @Before
	 void init() {
		 
		
		 
		 //Attributes 
		 email = "riad.elmahmoudy@mail.mcgill.ca";
		 displayName = "Riad";
		 username = "riadelm";
		 password = "aNicePassword";
		 id = "test"+l;
		 isAPaymentConfirmed = true;
		 aPaymentAmount = 100.34;
		 
		 aCustomer = customerRepo.createCustomer(id+"1", email+"customer", displayName+"customer", username+"customer", password+"customer");
		 anArtist = artistRepo.createArtist(id+"2", email+"artist", displayName+"artist", username+"artist", password+"artist");
		 aManager = managerRepo.createManager(id+"3", email+"manager", displayName+"manager", username+"manager", password+"manager");
		 aListing = artlistingRepo.createArtListing(ArtListing.PostVisibility.Public, id+"4", anArtist);
		 anArtPiece = artpieceRepo.createArtPiece(ArtPiece.PieceLocation.AtGallery,"UniversityStreet", id+5, aListing);
		 aOrder = artorderRepo.createArtOrder(false, ArtPiece.PieceLocation.AtGallery, "sherbrookeSt", "00Tracker", id+9, anArtPiece);
	
	 }
	 
	 @Test
	 void ticketCreateTest() {
		 init();
		//ID generator
		 Long l = System.currentTimeMillis();
		 
		 
		 //Create Ticket firstWay
		 Ticket testTicket = ticketRepo.createTicket(isAPaymentConfirmed, aPaymentAmount, id, aOrder, aCustomer, anArtist);
		 
		 //TEST if Ticket was created
		 assertNotNull(testTicket); 
		 
		 //Print Ticket
		 System.out.println("=================CREATE===============");
		 System.out.println(testTicket);
		 System.out.println("=================CREATE===============");
		 
		 //Find Ticket
		 Ticket testTicket2 = ticketRepo.getTicket(id);
		 
		 //TEST if Manager was retrieved
		 assertNotNull(testTicket2);
		 
		 //Print retrieved ticket
		 System.out.println("=================FIND===============");
		 System.out.println(testTicket2);
		 System.out.println("=================FIND===============");
		 
		 
	 }
		 


}



