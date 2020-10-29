package com.ecse321.visart.test.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.ecse321.visart.model.Artist;
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
  private static final ArtListing TAG_LISTING = new ArtListing(null, null, "listing", "mockcode",
	      null);
	  
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

    lenient().when(tagRepo.createTag(TagType.Genre, anyString(), anyString(), new ArtListing(null, null, "listing2", "mockcode2",
  	      null))).thenAnswer((InvocationOnMock invocation) -> {
          String id = invocation.getArgument(0);
          Tag tag = new Tag(id, id, id, id);
          return tag;
        });

    // In the tests below, test the Service class's DATA VALIDATION on the
    // parameters that are given to it.
  }


}
