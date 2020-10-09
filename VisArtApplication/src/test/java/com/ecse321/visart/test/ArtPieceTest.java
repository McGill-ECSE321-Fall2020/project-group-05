package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtPieceTest {

	// ID generator
	public static Long l;

	// attributes
	String aEmailAddress = "timcook@gmail.com";
	String aDisplayname = "Tim Cook";
	String aUsername = "timcook56";
	String aPassword = "apple123";
	String address = "124 Maywood";

	public static Artist artist;
	public static ArtListing artListing;

	@Autowired
	private ArtPieceRepository apRepo;

	@Autowired
	private ArtListingRepository alRepo;

	@Autowired
	private ArtistRepository aRepo;

	
	@Test
	@Order(1)
	void init() {
		
		l = System.currentTimeMillis();
		artist = aRepo.createArtist("kl" + l + 13, "steve@gmail.com", "Steve Jobs", "steve56", "apple1234");
		artListing = alRepo.createArtListing(ArtListing.PostVisibility.Public, "ki" + l + 23, artist);

		
		
		
	}

	@Test
	@Order(2)
	void testEntry() {


		// Create
		ArtPiece artPieceTest = apRepo.createArtPiece(PieceLocation.AtGallery, address, "kk" + l + 33, artListing);

		// Test if ArtPiece was created
		assertNotNull(artPieceTest);

		// Print Art Listing
		System.out.println("============================");
		System.out.println(artPieceTest);
		System.out.println("============================");
	}

	@Test
	@Order(3)
	void testGet() {
		

		// Find artPieceTest
		ArtPiece artPieceTest2 = apRepo.getArtPiece("kk" + l + 33);

		// Test if ArtPiece was retrieved
		assertNotNull(artPieceTest2);
		assertEquals(address, artPieceTest2.getAddressLocation());
		assertEquals(artListing.getIdCode(), artPieceTest2.getArtListing().getIdCode());

		System.out.println("============================");
		System.out.println(artPieceTest2);
		System.out.println("============================");

	}

}
