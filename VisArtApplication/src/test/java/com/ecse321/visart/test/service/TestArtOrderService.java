package com.ecse321.visart.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
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

@ExtendWith(MockitoExtension.class)
public class TestArtOrderService {

  @Mock
  private ArtOrderRepository aoRepo;

  @Mock
  private ArtPieceRepository apRepo;

  @Mock
  private EntityRepository entityRepo;

  @InjectMocks
  private ArtOrderService serviceAo;

  private static final String AO_KEY = "MockTestAO";
  private static final String AP_KEY = "MockTestAP";
  private static ArtOrder artOrderTest = null;
  private static final boolean TEST_BOOLEAN = true;
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

    lenient().when(aoRepo.getArtOrder(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(AO_KEY)) {
            ArtPiece APTEST = new ArtPiece(pieceLocation, "locationtest",
                "123", TEST_LISTING);
            ArtOrder artOrder1 = new ArtOrder(TEST_BOOLEAN, pieceLocation, AO_KEY,
                AO_KEY, AO_KEY, APTEST);

            return artOrder1;
          } else {

            return null;
          }
        });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };

    lenient().when(apRepo.getArtPiece(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(AP_KEY)) {
            ArtListing TEST_LISTING = new ArtListing(PostVisibility.Draft, "name",
                "listing", "mockcode",
                aArtist);
            ArtPiece artPiece = new ArtPiece(pieceLocation, AP_KEY, AP_KEY,
                TEST_LISTING);
            return artPiece;
          } else {

            return null;
          }
        });

    lenient().when(aoRepo.createArtOrder(Mockito.anyBoolean(), Mockito.any(PieceLocation.class),
        anyString(), anyString(), anyString(), Mockito.any(ArtPiece.class)))
        .thenAnswer((InvocationOnMock invocation) -> {
          Boolean delivery = invocation.getArgument(0);
          PieceLocation location = invocation.getArgument(1);
          String target = invocation.getArgument(2);
          String tracker = invocation.getArgument(3);
          String id = invocation.getArgument(4);
          ArtPiece artpiece = invocation.getArgument(5);
          ArtOrder artOrder2 = new ArtOrder(delivery, location, target,
              tracker, id, artpiece);
          ;
          return artOrder2;
        });

    lenient().doAnswer((InvocationOnMock invocation) -> {
      artOrderTest = invocation.getArgument(0);
      return artOrderTest;
    }).when(aoRepo).updateArtOrder(any());

    lenient().when(aoRepo.deleteArtOrder(anyString())).thenAnswer((InvocationOnMock invocation) -> {
      if (invocation.getArgument(0).equals(AO_KEY)) {
        return true;
      } else {
        return false;
      }
    });

    // In the tests below, test the Service class's DATA VALIDATION on the
    // parameters that are given to it.
  }

  @Test
  public void testCreateArtOrder() {

    ArtOrder artOrder = null;
    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122223333";
    String idP = AP_KEY;

    try {
      artOrder = serviceAo.createArtOrder(delivery, pieceLocation,
          target, tracker, idP);
    } catch (IllegalArgumentException e) {

      fail();
    }

    assertNotNull(artOrder);
    assertEquals(artOrder.getDeliveryTracker(), tracker);

  }

  @Test
  public void testCreateArtOrderNullLocation() {
    Boolean delivery = true;
    PieceLocation pieceLocation = null;
    String target = "123";
    String tracker = "111122223333";
    String idP = AP_KEY;
    String error = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(delivery, pieceLocation, target, tracker,
          idP);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Target Location cannot be empty!", error); // expected error message for service
                                                             // data
    // validation.
  }

  @Test
  public void testCreateArtOrderNullAddress() {

    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = null;
    String tracker = "111122223333";
    String idP = AP_KEY;
    String error = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(delivery, pieceLocation, target, tracker,
          idP);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Target Address cannot be empty!", error); // expected error message for service
                                                            // data
    // validation.
  }

  @Test
  public void testCreateArtOrderNullTracker() {

    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = null;
    String idP = AP_KEY;
    String error = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(delivery, pieceLocation,
          target, tracker, idP);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Delivery Tracker cannot be empty!", error); // expected error message for service
                                                              // data
  } // validation.

  @Test
  public void testCreateArtOrderInvalidPiece() {

    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122223333";
    String idP = "1224";
    String error = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(delivery, pieceLocation, target, tracker,
          idP);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Art Piece id must be valid!", error); // expected error message for service data
  }

  @Test
  public void testCreateArtOrderTooLongTracker() {

    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "d18xc5iQR8hfDI3QkhVETz8FiVLw5joauSotI1YnH92wWiiVxlSi1l106UnKNLNXgGEFPQhbIPN8yaCDKLpWBGoMeoin9YpzJCvmsGhLzgiGOggnQ7mF6YwjjU1Vqd3dsNiPK4J4gpdh6ItLlPUVaLgcqRbvd9TgB5p574xCVSSmEFeIYaPuwvVw9mhl6FUrqAwFTs511fSKMntzhOyDmiZy6qAVXZLfTFqiylLqAfLkw5XXkOyPmCBJK5cIyjM3Uilhkx9w9UVAB1tN8nAHFQqh6aNSRrfIA11w6toGIEUfpLUhOzxkT9kB98LKOg44qH3GfXqnwaRj51PqjtstYyA4ylBaFbPLtcqxN6qBjwrIT6GqzoXS42nEzvGWuMAZr1n6vjS7G0kgexkSJ9uBLXZEfkw6vGvLj6m94mwhFagCyXhN8nIdcj8xYtNAwu2v6uS3a170ovcJdZVAK0R1RZtEyWPXFfPBZkZ0KTT8TTfgpDCSoHpPorCpVa038SSHVtg0jR3kozivIP0is6gDTfW8HrMRizrcBX8wyEuqs56py6wkVO5S8CRJMfZOXPArKNAhaqBkKgPPYmzWsr1vYSEdjUD0POvRwKgVy3Sv1Ja0RdceIVqSpFqrzKbDR5fmtBvYoBl20QOUF2I8BNo3b2ghbDYoZR27exc58SL0aKC8KBXuQwz1QlkhRBfm09OAOH7qrBNPOxEf8vPOCXZnMnOCLhuEoO5QYDpmruGgKAM76aqZRDGeWEWaKReBjns5qIdfqiC1sNA2ivF7EZb1L9qj50WBt8nnzLPydKMdvJDUSdz2O1hynOavf8cc9gDJVqza6KeoZN4cBsz7ZOaMZ9TSfQcLTnbG1t6tGTL7AUKBXUjy8StUXwuUGFNyIWUneDKjRPFwY5rswtJhVmmij4iDvUJ8KXk1u4mhbkNV4KY331wHDGhpBnFYAYQmLVESHz13WA0zsBlDDSko1qvNpdsDfYX74DVV0iSSDR7tj";
    String idP = "1224";
    String error = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.createArtOrder(delivery, pieceLocation, target, tracker,
          idP);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Delivery Tracker is too long!", error); // expected error message for service data
  }

  @Test
  public void testGetArtOrder() {
    ArtOrder artOrder = null;
    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122223333";
    String id = AO_KEY;
    String idP = AP_KEY;

    try {
      artOrder = serviceAo.createArtOrder(delivery, pieceLocation,
          target, tracker, idP);
    } catch (IllegalArgumentException e) {

      fail();
    }

    // artOrder = null;

    try {
      artOrder = serviceAo.getArtOrder(id);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }

    assertNotNull(artOrder);
    assertEquals(id, artOrder.getIdCode());

  }

  @Test
  public void testGetNonExistingArtOrder() {
    ArtOrder artOrder = null;
    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122223333";
    String idP = AP_KEY;
    String idFake = "fake";

    try {
      artOrder = serviceAo.createArtOrder(delivery, pieceLocation,
          target, tracker, idP);
    } catch (IllegalArgumentException e) {

      fail();
    }

    // artOrder = null;

    try {
      artOrder = serviceAo.getArtOrder(idFake);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }

    assertNull(artOrder);

  }

  @Test
  public void testUpdateArtOrderNullID() {

    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122223333";
    String idP = AP_KEY;
    ArtOrder artOrder = null;
    String idNull = null;
    String error = null;

    try {
      artOrder = serviceAo.updateArtOrder(idNull, delivery, pieceLocation, target, tracker,
          idP);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(artOrder);
    assertEquals("ArtOrder id code cannot be empty!", error); // expected error message for service
                                                              // data
    // validation.
  }

  @Test
  public void testUpdateArtOrderNullLocation() {
    Boolean delivery = true;
    PieceLocation pieceLocation = null;
    String target = "123";
    String tracker = "111122223333";
    String id = AO_KEY;
    String idP = AP_KEY;
    PieceLocation pieceLocationOld = serviceAo.getArtOrder(id).getTargetLocation();
    ArtOrder artOrder = null;
    String error = null;

    try {
      artOrder = serviceAo.updateArtOrder(id, delivery, pieceLocation, target, tracker,
          idP);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNotNull(artOrder);
    assertEquals(pieceLocationOld, artOrder.getTargetLocation()); // expected error message for
                                                                  // service
    // data
    // validation.
  }

  @Test
  public void testUpdateArtOrderNullAddress() {

    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = null;
    String targetOld = serviceAo.getArtOrder(AO_KEY).getTargetAddress();
    String tracker = "111122223333";
    String id = AO_KEY;
    String idP = AP_KEY;
    String error = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(id, delivery, pieceLocation, target,
          tracker, idP);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNotNull(artOrder);
    assertEquals(targetOld, artOrder.getTargetAddress()); // expected error message for service
                                                          // data
    // validation.
  }

  @Test
  public void testUpdateArtOrderNullTracker() {

    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = null;
    String id = AO_KEY;
    String idP = AP_KEY;
    String trackerOld = serviceAo.getArtOrder(id).getDeliveryTracker();
    String error = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(id, delivery,
          pieceLocation, target, tracker, idP);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNotNull(artOrder);
    assertEquals(trackerOld, artOrder.getDeliveryTracker()); // expected error message for service
                                                             // data
  } // validation.

  @Test
  public void testUpdateArtOrderNullPiece() {

    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122223333";
    String id = AO_KEY;
    String idP = null;
    String idPOld = serviceAo.getArtOrder(id).getArtPiece().getIdCode();
    String error = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(id, delivery, pieceLocation, target, tracker,
          idP);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNotNull(artOrder);
    assertEquals(idPOld, artOrder.getArtPiece().getIdCode()); // expected error message for service
                                                              // data
  }

  @Test
  public void testUpdateArtOrderValidTracker() {

    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122224444666";
    String id = AO_KEY;
    String idP = AP_KEY;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(id, delivery,
          pieceLocation, target, tracker,
          idP);
    } catch (IllegalArgumentException e) {

    }
    assertNotNull(artOrder);
    assertEquals(artOrder.getDeliveryTracker(), tracker); // expected error message for
                                                          // service data
  }

  @Test
  public void testUpdateArtOrderValidTarget() {

    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = "12355";
    String tracker = "111122224444";
    String id = AO_KEY;
    String idP = AP_KEY;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(id, delivery,
          pieceLocation, target, tracker,
          idP);
    } catch (IllegalArgumentException e) {

    }
    assertNotNull(artOrder);
    assertEquals(artOrder.getTargetAddress(), target); // expected error message for
                                                       // service data
  }

  @Test
  public void testUpdateArtOrderValidPieceLocation() {

    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122224444";
    String id = AO_KEY;
    String idP = AP_KEY;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(id, delivery,
          pieceLocation, target, tracker,
          idP);
    } catch (IllegalArgumentException e) {

    }
    assertNotNull(artOrder);
    assertEquals(artOrder.getTargetLocation(), pieceLocation); // expected error message for
                                                               // service data
  }

  @Test
  public void testUpdateArtOrderValidDelivery() {

    Boolean delivery = false;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "111122224444";
    String id = AO_KEY;
    String idP = AP_KEY;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(id, delivery,
          pieceLocation, target, tracker,
          idP);
    } catch (IllegalArgumentException e) {

    }
    assertNotNull(artOrder);
    assertEquals(artOrder.getIsDelivered(), false); // expected error message for
                                                    // service data
  }

  @Test
  public void testUpdateArtOrderTooLongTracker() {

    Boolean delivery = true;
    PieceLocation pieceLocation = PieceLocation.AtGallery;
    String target = "123";
    String tracker = "d18xc5iQR8hfDI3QkhVETz8FiVLw5joauSotI1YnH92wWiiVxlSi1l106UnKNLNXgGEFPQhbIPN8yaCDKLpWBGoMeoin9YpzJCvmsGhLzgiGOggnQ7mF6YwjjU1Vqd3dsNiPK4J4gpdh6ItLlPUVaLgcqRbvd9TgB5p574xCVSSmEFeIYaPuwvVw9mhl6FUrqAwFTs511fSKMntzhOyDmiZy6qAVXZLfTFqiylLqAfLkw5XXkOyPmCBJK5cIyjM3Uilhkx9w9UVAB1tN8nAHFQqh6aNSRrfIA11w6toGIEUfpLUhOzxkT9kB98LKOg44qH3GfXqnwaRj51PqjtstYyA4ylBaFbPLtcqxN6qBjwrIT6GqzoXS42nEzvGWuMAZr1n6vjS7G0kgexkSJ9uBLXZEfkw6vGvLj6m94mwhFagCyXhN8nIdcj8xYtNAwu2v6uS3a170ovcJdZVAK0R1RZtEyWPXFfPBZkZ0KTT8TTfgpDCSoHpPorCpVa038SSHVtg0jR3kozivIP0is6gDTfW8HrMRizrcBX8wyEuqs56py6wkVO5S8CRJMfZOXPArKNAhaqBkKgPPYmzWsr1vYSEdjUD0POvRwKgVy3Sv1Ja0RdceIVqSpFqrzKbDR5fmtBvYoBl20QOUF2I8BNo3b2ghbDYoZR27exc58SL0aKC8KBXuQwz1QlkhRBfm09OAOH7qrBNPOxEf8vPOCXZnMnOCLhuEoO5QYDpmruGgKAM76aqZRDGeWEWaKReBjns5qIdfqiC1sNA2ivF7EZb1L9qj50WBt8nnzLPydKMdvJDUSdz2O1hynOavf8cc9gDJVqza6KeoZN4cBsz7ZOaMZ9TSfQcLTnbG1t6tGTL7AUKBXUjy8StUXwuUGFNyIWUneDKjRPFwY5rswtJhVmmij4iDvUJ8KXk1u4mhbkNV4KY331wHDGhpBnFYAYQmLVESHz13WA0zsBlDDSko1qvNpdsDfYX74DVV0iSSDR7tj";
    String idP = AP_KEY;
    String id = AO_KEY;
    String error = null;
    ArtOrder artOrder = null;

    try {
      artOrder = serviceAo.updateArtOrder(id, delivery, pieceLocation, target, tracker,
          idP);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(artOrder);
    assertEquals("Delivery Tracker is too long!", error); // expected error message for service data
  }

  @Test
  public void testDeleteArtOrder() {

    assertTrue(serviceAo.deleteArtOrder(AO_KEY));
    assertFalse(serviceAo.deleteArtOrder(""));
    // assertFalse(serviceAo.deleteArtOrder(null));

  }

  /**
   * @Test
   *       public void testAddTicket() {
   *       ArtOrder artOrder = null;
   *       Boolean delivery = true;
   *       PieceLocation pieceLocation = PieceLocation.AtGallery;
   *       String target = "123";
   *       String tracker = "111122223333";
   *       String id = AO_KEY;
   *       String idP = AP_KEY;
   *       try {
   *       artOrder = serviceAo.createArtOrder(delivery, pieceLocation,
   *       target, tracker, idP);
   *       serviceAo.addTicket(, );
   *       } catch (IllegalArgumentException e) {
   *       // Check that no error occurred
   *       fail();
   *       }
   *       assertNotNull(artOrder);
   *       assertNotNull(artOrder.getTicket());
   * 
   *       //assertEquals(manager.getPromotedListing(0).getDescription(),
   *       "testpost");
   * 
   *       }
   * 
   **/

}
