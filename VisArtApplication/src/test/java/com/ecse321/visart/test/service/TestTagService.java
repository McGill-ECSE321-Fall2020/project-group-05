package com.ecse321.visart.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.TagRepository;
import com.ecse321.visart.service.TagService;

@ExtendWith(MockitoExtension.class)
public class TestTagService {
  @Mock
  private TagRepository tagRepo;

  @Mock
  private EntityRepository entityRepo;

  @InjectMocks
  private TagService tagService;
  
  private static final String TAG_KEY = "MockTestForTag";
  private static final TagType TAG_TYPE = TagType.Material;
  private static final User aUser = new User("a","b","c","d","e","f","g");
  private static final Customer aCustomer = new Customer("customerCode", aUser);
  private static final Artist aArtist = new Artist("artistCode", aCustomer);
  private static final ArtListing TAG_LISTING = new ArtListing(PostVisibility.Draft, "name", "listing", "mockcode",
	      aArtist);
	  
 
  @BeforeEach
  public void setMockOutput() {
    // Mock the Repository methods, returning what we want to expect from the
    // database, instead of actually querying the database.

    lenient().when(tagRepo.getTag(anyString())).thenAnswer(
        (InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(TAG_KEY)) {
            Tag myTag = new Tag(TAG_TYPE, TAG_KEY, TAG_KEY, TAG_LISTING);
            return myTag;
          } else {
            return null;
          }
        });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };

    lenient().when(tagRepo.createTag(any(), anyString(), anyString(), 
    		any())).thenAnswer((InvocationOnMock invocation) -> {
          String id = invocation.getArgument(2);
          TagType type = (TagType)invocation.getArgument(0);
          ArtListing listing = (ArtListing)invocation.getArgument(3);
          Tag tag = new Tag(type, id, id, listing);
          return tag;
        });

    // In the tests below, test the Service class's DATA VALIDATION on the
    // parameters that are given to it.
   
  }

  @Test
  public void testCreateTag() {
    // assertEquals(0, service.getAllUsers().size());

    String keyword = "key";
    TagType type = TagType.Category;
    User aUser = new User("a","b","c","d","e","f","g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing listing = new ArtListing(PostVisibility.Draft, "name", "listing", "mockcode",
  	      aArtist);
    
    Tag tag = null;
    try {
      tag = tagService.createTag(type, keyword, keyword, listing);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(tag);
    assertEquals(keyword, tag.getIdCode());
  }

  @Test
  public void testCreateNullTag() {
    String error = null;
	String keyword = null;
	TagType type = null;
    Tag tag = null;
    ArtListing listing = null;
    try {
      tag = tagService.createTag(type, keyword, keyword, listing);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(tag);
    //assertEquals("Tag id code cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateNullTagID() {
    String error = null;
	String keyword = "renaissance";
	String id = null;
	TagType type = TagType.Topic;
    Tag tag = null;
    User aUser = new User("a","b","c","d","e","f","g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing listing = new ArtListing(PostVisibility.Draft, "name", "listing", "mockcode",
  	      aArtist);
    try {
      tag = tagService.createTag(type, keyword, id, listing);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(tag);
    assertEquals("Tag id code cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateNullTagType() {
    String error = null;
	String keyword = "renaissance";
	String id = "mockcode";
	TagType type = null;
    Tag tag = null;
    User aUser = new User("a","b","c","d","e","f","g");
    Customer aCustomer = new Customer("customerCode", aUser);
    Artist aArtist = new Artist("artistCode", aCustomer);
    ArtListing listing = new ArtListing(PostVisibility.Draft, "name", "listing", "mockcode",
  	      aArtist);
    try {
      tag = tagService.createTag(type, keyword, id, listing);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(tag);
    assertEquals("Tag type cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  @Test
  public void testCreateNullTagListing() {
    String error = null;
	String keyword = "renaissance";
	String id = "mockcode";
	TagType type = TagType.Topic;
    Tag tag = null;
    ArtListing listing = null;
    try {
      tag = tagService.createTag(type, keyword, id, listing);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(tag);
    assertEquals("Tag listing cannot be empty!", error); // expected error message for service data
                                                          // validation.
  }
  
  
  

  
  
}


