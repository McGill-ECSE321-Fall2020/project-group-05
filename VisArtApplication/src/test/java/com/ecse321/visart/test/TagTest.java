package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import com.ecse321.visart.repositories.TagRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TagTest {

  @Autowired
  private TagRepository tagRepo;

  @Test
  void tagCreateTest() {

    // ID generator
    Long l = System.currentTimeMillis();

    // Attributes
    String keyword = "Acrylic";
    String id = "" + l;
    String email = "riad.elmahmoudy@mail.mcgill.ca";
    String displayName = "Riad";
    String username = "riadelm";
    String password = "aNicePassword";
    User aUser = new User(id + 4, email, displayName, username, password);
    Customer aCustomer = new Customer(id + 3, aUser);
    Manager aManager = new Manager(id + 2, aUser);
    Artist anArtist = new Artist(id + 1, aCustomer);
    ArtListing aListing = new ArtListing(ArtListing.PostVisibility.Public, id, aManager, anArtist);

    // Create
    Tag testTag = tagRepo.createTag(Tag.TagType.Material, keyword, id, aListing);

    // TEST if Tag was created
    assertNotNull(testTag);

    // Print Tag
    System.out.println("=================CREATE===============");
    System.out.println(testTag);
    System.out.println("=================CREATE===============");

    // Find Tag
    Tag testTag2 = tagRepo.getTag("" + l);

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
