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
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.model.User;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.CustomerRepository;
import com.ecse321.visart.repositories.TagRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TagTest {
    // ID generator
    Long l = System.currentTimeMillis();

  @Autowired
  private TagRepository tagRepo;
  
  @Autowired
  private CustomerRepository customerRepo;
  
  @Autowired
  private ArtistRepository artistRepo;
  
  @Autowired
  private ArtListingRepository listingRepo;
  String keyword ;
  String id ;
  String email ;
  String displayName ;
  String username ;
  String password;
  Customer aCustomer;
  Artist aArtist;
  ArtListing aListing;
  
  
  @Before
  void init() {
	
	    // Attributes
	  	keyword = "Acrylic";
	    id = "test" + l;
	    email = "riad.elmahmoudy@mail.mcgill.ca";
	    displayName = "Riad";
	    username = "riadelm";
	    password = "aNicePassword";
	    aCustomer = customerRepo.createCustomer(id+2, email, displayName, username, password);
	    aArtist = artistRepo.createArtist(id+5, aCustomer);
	    aListing = listingRepo.createArtListing(ArtListing.PostVisibility.Public, id+4, aArtist);
	    
  }

  @Test
  void tagCreateTest() {
init();

    // Create
    Tag testTag = tagRepo.createTag(Tag.TagType.Material, keyword, id, aListing);

    // TEST if Tag was created
    assertNotNull(testTag);

    // Print Tag
    System.out.println("=================CREATE===============");
    System.out.println(testTag);
    System.out.println("=================CREATE===============");

    // Find Tag
    Tag testTag2 = tagRepo.getTag(id);

    // TEST if Tag was retrieved
    assertNotNull(testTag2);
    assertEquals(Tag.TagType.Material, testTag2.getType());
    assertEquals(keyword, testTag2.getKeyword());
    assertEquals(aListing, testTag.getListing());
    System.out.println("=================FIND===============");
    System.out.println(testTag2.getKeyword());
    System.out.println("=================FIND===============");

  }
}
