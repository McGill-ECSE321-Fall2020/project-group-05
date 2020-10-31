package com.ecse321.visart.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.repositories.ArtOrderRepository;
import com.ecse321.visart.repositories.ArtPieceRepository;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.service.ArtOrderService;
import com.ecse321.visart.service.ArtPieceService;

@ExtendWith(MockitoExtension.class)
public class TestArtPieceService {

  @Mock
  private ArtPieceRepository apRepo;

  @Mock
  private EntityRepository entityRepo;

  @InjectMocks
  private ArtPieceService serviceAp;

  private static final String AP_KEY = "MockTestAP";
  private static final PieceLocation pieceLocation = PieceLocation.AtGallery;
  private static final User aUser = new User("a", "b", "c", "d", "e", "f", "g");
  private static final Customer aCustomer = new Customer("customerCode", aUser);
  private static final Artist aArtist = new Artist("artistCode", aCustomer);
  private static final ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
      "listing", "mockcode",
      aArtist);

  @BeforeEach
  public void setMockOutput() {
    // Mock the Repository methods, returning what we want to expect from the
    // database, instead of actually querying the database.

    lenient().when(apRepo.getArtPiece(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(AP_KEY)) {
            ArtPiece artPiece = new ArtPiece(pieceLocation, AP_KEY, AP_KEY,
                TEST_LISTING);
            return artPiece;
          } else {

            return null;
          }
        });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };

    lenient()
        .when(apRepo.createArtPiece(Mockito.any(PieceLocation.class), anyString(), anyString(),
            Mockito.any(ArtListing.class)))
        .thenAnswer((InvocationOnMock invocation) -> {
          PieceLocation location = invocation.getArgument(0);
          String locationAddress = invocation.getArgument(1);
          String id = invocation.getArgument(2);
          ArtListing artListing = invocation.getArgument(3);

          ArtPiece artpiece = new ArtPiece(location, locationAddress, id, artListing);

          return artpiece;
        });

    // In the tests below, test the Service class's DATA VALIDATION on the
    // parameters that are given to it.
  }

  @Test
  public void testCreateArtPiece() {
    ArtPiece artPiece = null;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String id = "1234";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);

    try {
      artPiece = serviceAp.createArtPiece(Piecelocation, target,
          id, TEST_LISTING);
    } catch (IllegalArgumentException e) {

      fail();
    }

    assertNotNull(artPiece);
    assertEquals(artPiece.getIdCode(), id);

  }
  
  @Test
  public void testCreateArtPieceNullId() {
    ArtPiece artPiece = null;
    String error = null;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "123";
    String id = null;
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);

    try {
      artPiece = serviceAp.createArtPiece(Piecelocation, target,
          id, TEST_LISTING);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
      
    }

    assertNull(artPiece);
    assertEquals("User id code cannot be empty!", error);

  }
  
  @Test
  public void testCreateArtPieceNullLocation() {
    ArtPiece artPiece = null;
    String error = null;
    PieceLocation Piecelocation = null;
    String target = "123";
    String id = "1223";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);

    try {
      artPiece = serviceAp.createArtPiece(Piecelocation, target,
          id, TEST_LISTING);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
      
    }

    assertNull(artPiece);
    assertEquals("Piece Location cannot be empty!", error);

  }
  
  @Test
  public void testCreateArtPieceNullTarget() {
    ArtPiece artPiece = null;
    String error = null;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = null;
    String id = "1223";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
        "listing", "mockcode",
        aArtist);

    try {
      artPiece = serviceAp.createArtPiece(Piecelocation, target,
          id, TEST_LISTING);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
      
    }

    assertNull(artPiece);
    assertEquals("Address Location cannot be empty!", error);

  }
  
  @Test
  public void testCreateArtPieceNullListing() {
    ArtPiece artPiece = null;
    String error = null;
    PieceLocation Piecelocation = PieceLocation.AtGallery;
    String target = "4433";
    String id = "1223";
    User aUser = new User("a", "b", "c", "d", "e", "f", "g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing TEST_LISTING = null;

    try {
      artPiece = serviceAp.createArtPiece(Piecelocation, target,
          id, TEST_LISTING);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
      
    }

    assertNull(artPiece);
    assertEquals("Art Listing cannot be empty!", error);

  }
  
  

}
