package com.ecse321.visart.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.model.Tag.TagType;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.TagRepository;
import com.ecse321.visart.service.TagService;

@ExtendWith(MockitoExtension.class)
public class TestTagService {
  @Mock
  private TagRepository tagRepo;

  @Mock
  private EntityRepository entityRepo;
  
  @Mock
  private ArtListingRepository alRepo;

  @InjectMocks
  private TagService tagService;
  
  private static final String TAG_KEY = "MockTestForTag";
  private static final String TAG_ID = "mockcode";
  private static final TagType TAG_TYPE = TagType.Material;
  private static final User aUser = new User("a","b","c","d","e","f","g");
  private static final Customer aCustomer = new Customer("customerCode", aUser);
  private static final Artist aArtist = new Artist("artistCode", aCustomer);
  private static final ArtListing TAG_LISTING = new ArtListing(PostVisibility.Draft, "name", "listing", "listingcode",
	      aArtist);
  private static Tag tagTest = null;
	  
 
  @BeforeEach
  public void setMockOutput() {
    // Mock the Repository methods, returning what we want to expect from the
    // database, instead of actually querying the database.

    lenient().when(tagRepo.getTag(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(TAG_ID)) {
            Tag myTag = new Tag(TAG_TYPE, TAG_KEY, TAG_ID, TAG_LISTING);
            return myTag;
          } else {
            return null;
          }
        });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };

    lenient().when(alRepo.getArtListing(anyString())).thenAnswer((InvocationOnMock invocation) -> {
    	String id = invocation.getArgument(0);
    	return TAG_LISTING;
    });
    lenient().when(tagRepo.createTag(any(), anyString(), anyString(), 
    		any())).thenAnswer((InvocationOnMock invocation) -> {
          String id = invocation.getArgument(2);
          String keyword = invocation.getArgument(1);
          TagType type = invocation.getArgument(0);
          ArtListing listing = invocation.getArgument(3);
     
          Tag tag = new Tag(type, keyword, TAG_ID,listing);
          return tag;
        });
    
    lenient().doAnswer((InvocationOnMock invocation) -> {
        tagTest = invocation.getArgument(0);
        return tagTest;
      }).when(tagRepo).updateTag(any());

    lenient().when(tagRepo.deleteTag(anyString())).thenAnswer((InvocationOnMock invocation) -> {
        if (invocation.getArgument(0).equals(TAG_ID)) {
          return true;
        } else {
          return false;
        }
      });

    // In the tests below, test the Service class's DATA VALIDATION on the
    // parameters that are given to it.
   
  }

  @Test
  public void testCreateTag() {

    String keyword = "mockcode";
    String type = "Topic";
    String listing = "listingcode";
 
    Tag tag = null;
    try {
      tag = tagService.createTag(type, keyword, listing);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(tag);
    assertEquals(TAG_ID, tag.getIdCode());
  }

  @Test
  public void testCreateNullTag() {
	String keyword = null;
	String type = null;
    Tag tag = null;
    String listing = null;
    try {
      tag = tagService.createTag(type, keyword, listing);
    } catch (IllegalArgumentException e) {

    }
    assertNull(tag);
    //assertEquals("Tag id code cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  
  @Test
  public void testCreateNullTagType() {
    String error = null;
	String keyword = "keyword";
	String id = "mockcode";
	String type = null;
    Tag tag = null;
    String listing = "listingcode";
    try {
      tag = tagService.createTag(type, keyword, listing);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(tag);
    assertEquals("Tag type must be a valid type!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateNullTagListing() {
    String error = null;
    String keyword = "keyword";
    String type = "Topic";
    String listing = null;
    Tag tag = null;
 
    try {
      tag = tagService.createTag(type, keyword, listing);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(tag);
    assertEquals("Listing id must be a valid id of existing listing!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateNullTagKeyword() {
	String error = null;
	String keyword = null;
	String type = "Topic";
	String listing = "listingcode";
	Tag tag = null;
    try {
      tag = tagService.createTag(type, keyword, listing);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(tag);
    assertEquals("Tag keyword cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }

  @Test
  public void testCreateNullTagKeywordLength() {
	String keyword = "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey";
	String error = null;
    String type = "Topic";
    String listing = "listingcode";
    Tag tag = null;
    try {
      tag = tagService.createTag(type, keyword, listing);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(tag);
    assertEquals("Tag keyword is too long!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testGetTag() {
	String error = null;
	String keyword = "keyword";
	String type = "Topic";
	String listing = "listingcode";
	Tag tag = null;
	Tag tag2 = null;
	String id = null;
    try {
      tag = tagService.createTag(type, keyword, listing);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(tag);
    id = tag.getIdCode();
    
    try {
      tag2 = tagService.getTag(id);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(tag2);
    assertEquals(id, tag2.getIdCode());
    
  }
  @Test
  public void testUpdateNullTagID() {
    String error = null;
	String keyword = "renaissance";
	String id = null;
	String type = "Topic";
    Tag tag = null;
    String listing = "listingcode";
    try {

      tag = tagService.updateTag(type, keyword, id, listing);

    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Tag id code cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testUpdateNullTagType() {
    String error = null;
	String keyword = "renaissance";
	String id = "mockcode";
	String type = null;
    Tag tag = null;
    String listing = "listingcode";
    try {
      tag = tagService.updateTag(type, keyword, id, listing);

    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Tag type cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testUpdateNullTagListing() {
    String error = null;
	String keyword = "renaissance";
	String id = TAG_ID;
	String type = "Topic";
    Tag tag = null;
    String listing = null;
    try {
      tag = tagService.updateTag(type, keyword, id,  listing);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Tag listing cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
   @Test
   public void testUpdateNullTagKeyword() {
    String error = null;
	String keyword = "";
	String id = TAG_ID;
	String type = "Topic";
    Tag tag = null;
    String listing = "listingcode";
    try {
      tag = tagService.updateTag(type, keyword, id, listing);

    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Tag keyword cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }

  @Test
  public void testUpdateNullTagKeywordLength() {
    String error = null;
	String keyword = "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey"
			+ "keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeykey";
	 String id = TAG_ID;
	 String type = "Topic";
	 Tag tag = null;
	 String listing = "listingcode";
    try {
      tag = tagService.updateTag(type, keyword, id,listing);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Tag keyword is too long!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testUpdateTagKeyword() {
   String error = null;
   String keyword = "key2";
   String type = "Topic";
   String id = "mockcode";
   Tag tag = null;
   String listing = "listingcode";
   try {
     tag = tagService.updateTag(type, keyword,id, listing);
   } catch (IllegalArgumentException e) {
     error = e.getMessage();
   }
   assertEquals(tag.getKeyword(), keyword); // expected error message for service data
                                                         // validation.
 }
  
  @Test
  public void testUpdateTagType() {
   String error = null;
   String keyword = "renaissance";
   String id = TAG_ID;
   String type = "Genre";
   Tag tag = null;
   String listing = "listingcode";
   try {
     tag = tagService.updateTag(type, keyword, id, listing);
   } catch (IllegalArgumentException e) {
     error = e.getMessage();
   }
   assertEquals(tag.getType().toString(), type); // expected error message for service data
                                                         // validation.
 }
  
  @Test
  public void testUpdateTagListing() {
   String error = null;
   String keyword = "renaissance";
   String id = TAG_ID;
   String type = "Material";
   Tag tag = null;
   String listing = "listingcode";
   try {
     tag = tagService.updateTag(type, keyword, id, listing);
   } catch (IllegalArgumentException e) {
     error = e.getMessage();
   }
   
   assertEquals(tag.getListing().getIdCode(), listing); // expected error message for service data
                                                         // validation.
 }
  
 @Test
  public void testDeleteManager() {
    
    assertTrue(tagService.deleteTag(TAG_ID));
    
  }

  
  
}


