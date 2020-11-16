/**
 * @author riad el mahmoudy
 */
package com.ecse321.visart.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.CustomerRepository;
import com.ecse321.visart.repositories.TagRepository;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TagTest {

  @Autowired
  private TagRepository tagRepo;

  @Autowired
  private CustomerRepository customerRepo;

  @Autowired
  private ArtistRepository artistRepo;

  @Autowired
  private ArtListingRepository listingRepo;

  static Tag testTag2;

  static String keyword;
  static String id;
  static String email;
  static String displayName;
  static String username;
  static String password;
  static Customer aCustomer;
  static Artist aArtist;
  static ArtListing aListing;
  static String profilePic;
  static String profileDesc;
  static String title;
  static String listingDesc;

  // Pseudo-random ID for tested entities
  static Long l = System.currentTimeMillis();

  @Test
  @Order(1)
  void init() {

    // Attributes
    keyword = "Acrylic";
    id = "test" + l;
    email = "riad.elmahmoudy@mail.mcgill.ca";
    displayName = "Riad";
    username = "riadelm";
    password = "aNicePassword";
    profilePic = "Bezos.jpg";
    profileDesc = "ACAB, billionaires shouldnt exist";
    listingDesc = "mona lisa copy";
    title = "fake monalisa";
    aCustomer = customerRepo.createCustomer(id + 2, email, displayName, username, password,
        profilePic,
        profileDesc);
    aArtist = artistRepo.createArtist(id + 5, aCustomer);
    aListing = listingRepo.createArtListing(0.0,ArtListing.PostVisibility.Public, title, listingDesc,
        id + 4, aArtist);

  }

  @Test
  @Order(2)
  void testCreate1() {
    // Create
    Tag testTag = tagRepo.createTag(Tag.TagType.Material, keyword, id, aListing);
    listingRepo.updateArtListing(aListing);
    tagRepo.updateTag(testTag);
    // TEST if Tag was created
    assertNotNull(testTag);

    // Print Tag
    System.out.println("=================CREATE===============");
    System.out.println(testTag);
    System.out.println("=================CREATE===============");
  }

  @Test
  @Order(3)
  void testGet1() {
    // Find Tag
    testTag2 = tagRepo.getTag(id);

    // TEST if Tag was retrieved
    assertNotNull(testTag2);
    assertEquals(Tag.TagType.Material, testTag2.getType());
    assertEquals(keyword, testTag2.getKeyword());
    assertEquals(aListing.getIdCode(), testTag2.getListing().getIdCode());
    System.out.println("=================FIND===============");
    System.out.println(testTag2.getKeyword());
    System.out.println("=================FIND===============");

  }

  @Test
  @Order(4)
  void testUpdate1() {
    // Find manager
    testTag2 = tagRepo.getTag(id);

    // Update Tag
    testTag2.setKeyword("newKeyWord");
    tagRepo.updateTag(testTag2);

    // TEST if Tag was retrieved and if properly updated
    assertNotNull(testTag2);
    assertEquals(Tag.TagType.Material, testTag2.getType());
    assertEquals("newKeyWord", testTag2.getKeyword());
    assertEquals(aListing.getIdCode(), testTag2.getListing().getIdCode());

    System.out.println("=================UPDATE===============");
    System.out.println(testTag2);
    System.out.println("=================UPDATE===============");
  }

  @Test
  @Order(5)
  void testDelete1() {
    tagRepo.deleteTag(testTag2);
    // Deleting the repositories created out of the database
    listingRepo.deleteArtListing(aListing);
    artistRepo.deleteArtist(aArtist);
    customerRepo.deleteCustomer(aCustomer);

    assertEquals(null, tagRepo.getTag(id));
    assertEquals(null, artistRepo.getArtist(id + 5));
    assertEquals(null, customerRepo.getCustomer(id + 2));
    assertEquals(null, listingRepo.getArtListing(id + 4));
  }
}
