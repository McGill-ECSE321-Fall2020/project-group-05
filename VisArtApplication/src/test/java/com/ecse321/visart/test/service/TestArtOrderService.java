package com.ecse321.visart.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.service.ArtOrderService;

@ExtendWith(MockitoExtension.class)
public class TestArtOrderService {

  @Mock
  private ArtOrderRepository aoRepo;

  @Mock
  private EntityRepository entityRepo;

  @InjectMocks
  private ArtOrderService serviceAo;

  private static final String AO_KEY = "MockTestAO";
  private static final boolean TEST_BOOLEAN = true;
  private static final PieceLocation pieceLocation = PieceLocation.AtGallery;
  private static final User aUser = new User("a", "b", "c", "d", "e", "f", "g");
  private static final Customer aCustomer = new Customer("customerCode", aUser);
  private static final Artist aArtist = new Artist("artistCode", aCustomer);
  private static final ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
      "listing", "mockcode",
      aArtist);
  private static final ArtPiece AP_TEST = new ArtPiece(pieceLocation, "locationtest",
      "123", TEST_LISTING);

  @BeforeEach
  public void setMockOutput() {
    // Mock the Repository methods, returning what we want to expect from the
    // database, instead of actually querying the database.

    lenient().when(aoRepo.getArtOrder(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(AO_KEY)) {
            ArtOrder artOrder1 = new ArtOrder(TEST_BOOLEAN, pieceLocation, AO_KEY,
                AO_KEY, AO_KEY, AP_TEST);
            
            return artOrder1;
          } else {
            
            return null;
          }
        });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };

    lenient().when(aoRepo.createArtOrder(Mockito.anyBoolean(), Mockito.any(PieceLocation.class),
        anyString(), anyString(), anyString(), Mockito.any(ArtPiece.class)))
        .thenAnswer((InvocationOnMock invocation) -> {
          String id = invocation.getArgument(0);
          ArtOrder artOrder2 = new ArtOrder(TEST_BOOLEAN, pieceLocation, id,
              id, id, AP_TEST);
          ;
          return artOrder2;
        });

    // In the tests below, test the Service class's DATA VALIDATION on the
    // parameters that are given to it.
  }

  @Test
  public void testCreateArtOrder() {

    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(true, PieceLocation.AtGallery,
          AO_KEY, AO_KEY, AO_KEY, AP_TEST);
    } catch (IllegalArgumentException e) {

      fail();
    }

    assertNotNull(artOrder);
    assertEquals(artOrder.getIdCode(), AO_KEY);

  }

  @Test
  public void testCreateArtOrderNullID() {
    ArtOrder artOrder = null;
    String idNull = null;
    String error = null;

    try {
      artOrder = serviceAo.createArtOrder(TEST_BOOLEAN, pieceLocation, AO_KEY, AO_KEY, idNull,
          AP_TEST);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("User id code cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateArtOrderNullLocation() {
    PieceLocation piece = null;
    String error = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(TEST_BOOLEAN, piece, AO_KEY, AO_KEY, AO_KEY,
          AP_TEST);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Target Location cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateArtOrderNullAddress() {
   
    String aTargetAddress = null;
    String error = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(TEST_BOOLEAN, pieceLocation, aTargetAddress, AO_KEY, AO_KEY,
          AP_TEST);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Target Address cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateArtOrderNullDelivery() {
   
    String aDeliveryTracker = null;
    String error = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(TEST_BOOLEAN, pieceLocation,
          AO_KEY, aDeliveryTracker, AO_KEY,
          AP_TEST);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Delivery Tracker cannot be empty!", error); // expected error message for service data
  }                                            // validation.
  
  @Test
  public void testCreateArtOrderBadTracker() {
   
    String aDeliveryTracker = "1Z8DJ2";
    String error = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(TEST_BOOLEAN, pieceLocation,
          AO_KEY, aDeliveryTracker, AO_KEY,
          AP_TEST);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Delivery Tracker must contain 12 digits", error); // expected error message for service data
  }          
  
  

  @Test
  public void testCreateArtOrderNullPiece() {
   
    ArtPiece artpiece = null;
    String error = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(TEST_BOOLEAN, pieceLocation, AO_KEY, AO_KEY, AO_KEY,
          artpiece);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Art Piece cannot be empty!", error); // expected error message for service data
  }                       

}
