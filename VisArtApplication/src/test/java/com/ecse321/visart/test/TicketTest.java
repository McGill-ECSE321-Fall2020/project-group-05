package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import com.ecse321.visart.repositories.TicketRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TicketTest {
	
	 @Autowired
	  private TicketRepository ticketRepo;
	 
	 @Test
	 void ticketCreateTest() {
		//ID generator
		 Long l = System.currentTimeMillis();
		 
		 //Attributes 
		 String email = "riad.elmahmoudy@mail.mcgill.ca";
		 String displayName = "Riad";
		 String username = "riadelm";
		 String password = "aNicePassword";
		 String id = ""+l;
		 boolean isAPaymentConfirmed = true;
		 double aPaymentAmount = 100.34;
		 User aUser = new User(id+1,email, displayName, username, password);
		 Customer aCustomer = new Customer(id+2, aUser);
		 Artist anArtist = new Artist(id+3, aCustomer);
		 Manager aManager = new Manager(id+2, aUser);
		 ArtListing aListing = new ArtListing(ArtListing.PostVisibility.Public, id, aManager, anArtist);
		 ArtPiece anArtPiece = new ArtPiece(ArtPiece.PieceLocation.Offsite, "UniversityStreet", id+4, aListing);
		 ArtOrder anArtOrder = new ArtOrder(false, ArtPiece.PieceLocation.Offsite, "SherbrookeStreet", "00Tracker", id+5, anArtPiece, false,100.00, "id123", aCustomer, anArtist);
		   //specific for second order
		 boolean isDeliveredForOrder = false;
		 String aTargetAddressForOrder = "PeelStreet";
		 String aDeliveryTrackerForOrder = "00Tracker00";
		 String aIdCodeForOrder = "id123";
		 
		 //Create Ticket firstWay
		 Ticket testTicket = ticketRepo.createTicket(isAPaymentConfirmed, aPaymentAmount, ""+l, isDeliveredForOrder, ArtPiece.PieceLocation.Offsite, aTargetAddressForOrder, aDeliveryTrackerForOrder, aIdCodeForOrder, anArtPiece, aCustomer, anArtist);
		 
		 //TEST if Ticket was created
		 assertNotNull(testTicket); 
		 
		 //Print Ticket
		 System.out.println("=================CREATE===============");
		 System.out.println(testTicket);
		 System.out.println("=================CREATE===============");
		 
		 //Find Ticket
		 Ticket testTicket2 = ticketRepo.getTicket(""+l);
		 
		 //TEST if Manager was retrieved
		 assertNotNull(testTicket2);
		 
		 //Print retrieved ticket
		 System.out.println("=================FIND===============");
		 System.out.println(testTicket2);
		 System.out.println("=================FIND===============");
		 
		 
	 }
		 


}
