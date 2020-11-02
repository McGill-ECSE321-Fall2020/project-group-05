/**
 * @author Riad El Mahmoudy
 */
package com.ecse321.visart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.model.Tag.TagType;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.TagRepository;

@Service
public class TagService {

  @Autowired
  TagRepository tagRepo;

  @Autowired
  ArtListingRepository artListingRepo;

  @Autowired
  EntityRepository entityRepo;

  /**
   * createTag creates Tag and attaches it to an ArtListing (by given id). Input
   * Strings cannot be null, keyword cannot be empty or longer than 1000
   * characters, and aListingId. aTypeString must be a type from the TagType enum:
   * Topic, Category, Genre, Material, Other
   * 
   * @param  aTypeString a valid TagType identifier
   * @param  aKeyword    a string keyword between 1 and 1000 characters
   * @param  aListingId  a valid id of existing art listing to add tag to
   * @return             a generated Tag with random id written to database
   */
  @Transactional
  public Tag createTag(String aTypeString, String aKeyword, String aListingId) {

    String aIdCode = EntityRepository.getUniqueKey();
    if (aIdCode == null || aIdCode.length() < 1) {
      throw new IllegalArgumentException("Tag id code cannot be empty!");
    }

    TagType aType = TagType.fromString(aTypeString);
    if (aType == null) {
      throw new IllegalArgumentException("Tag type must be a valid type!");
    }

    if (aKeyword == null || aKeyword.length() < 1) {
      throw new IllegalArgumentException("Tag keyword cannot be empty!");
    }

    if (aKeyword.length() > 1000) {
      throw new IllegalArgumentException("Tag keyword is too long!");
    }

    ArtListing aListing = artListingRepo.getArtListing(aListingId);
    if (aListing == null) {
      throw new IllegalArgumentException("Listing id must be a valid id of existing listing!");
    }

    return tagRepo.createTag(aType, aKeyword, aIdCode, aListing);
  }

  /**
   * getTag retrieves a Tag from the database by id.
   * 
   * @param  aIdCode the id code of the Tag in database.
   * @return         Tag if id is valid, null if invalid
   */
  @Transactional
  public Tag getTag(String aIdCode) {
    return tagRepo.getTag(aIdCode);
  }

  /**
   * getTagByType retrieves the Tags in the database based on a given TagType.
   * 
   * @param  type a TagType enum value
   * @return
   */
  @Transactional
  public List<Tag> getTagByType(TagType type) {
    return entityRepo.findEntityByAttribute("type", Tag.class, "" + type.ordinal());
  }

  /**
   * getTagByType retrieves the Tags in the database based on a given TagType
   * identifier but as a String. Valid strings include
   * Topic, Category, Genre, Material, Other
   * 
   * @param  type a valid TagType identifier
   * @return      a list of Tags with the same TagType, from the database
   */
  @Transactional
  public List<Tag> getTagByType(String type) {
    return getTagByType(TagType.fromString(type));
  }

  /**
   * getTagByKeyword retrieves a list of Tags all with the same keyword. There may
   * be duplicates, and so a list is returned.
   * 
   * @param  keyword the keyword to match
   * @return         a list of tags with the same keyword
   */
  @Transactional
  public List<Tag> getTagByKeyword(String keyword) {
    return entityRepo.findEntityByAttribute("keyword", Tag.class, "" + keyword);
  }

  /**
   * getAllTags gets all tags from the database
   * 
   * @return a list of all tags from the database. May be empty list
   */
  @Transactional
  public List<Tag> getAllTags() {
    return entityRepo.getAllEntities(Tag.class);
  }

  /**
   * updateTag updates a given tag's attributes. aIdCode specifies the Tag to
   * update, and subsequent parameters update the Tag's attributes, unless the
   * given parameter is null, in which case the Tag's attribute remains unchanged.
   * aTypeString must be a valid TagType value such as Topic, Category, Genre,
   * Material, Other. aListingId must be a valid id of an existing ArtListing
   * 
   * @param  aIdCode     a valid id of an existing Tag, cannot be null
   * @param  aTypeString a valid TagType value string
   * @param  aKeyword    a string between 1 and 1000 characters, or null/empty for
   *                     no change
   * @param  aListingId  a valid id of an existing ArtListing, or null for no
   *                     change
   * @return             the updated Tag
   */
  @Transactional
  public Tag updateTag(String aTypeString, String aKeyword, String aIdCode, String aListingId) {
    if (aIdCode == null || aIdCode.length() < 1) {
      throw new IllegalArgumentException("Tag id code cannot be empty!");
    }
    Tag tag = tagRepo.getTag(aIdCode);
    if (tag == null) {
      throw new IllegalArgumentException("Tag id must be a valid id!");
    }

    TagType aType = TagType.fromString(aTypeString);
    if (aType != null) {
      tag.setType(aType);
    } else {
      throw new IllegalArgumentException("Tag type cannot be empty!");
    }

    if (aKeyword != null) {
      if (aKeyword.length() < 1) {
        throw new IllegalArgumentException("Tag keyword cannot be empty!");
      } else if (aKeyword.length() > 1000) {
        throw new IllegalArgumentException("Tag keyword is too long!");
      } else {
        tag.setKeyword(aKeyword);
      }
    }

    ArtListing aListing = artListingRepo.getArtListing(aListingId);
    if (aListing == null) {
      throw new IllegalArgumentException("Tag listing cannot be empty!");
    }

    tag.setListing(aListing);

    tagRepo.updateTag(tag);
    return tag;
  }

  /**
   * deleteTag deletes the given Tag from the database, by its idCode.
   * 
   * @param  aIdCode the id of the Tag to delete
   * @return         true for deleted, false for a failure, null if idcode is
   *                 empty
   */
  @Transactional
  public Boolean deleteTag(String aIdCode) {
    if (aIdCode != null && !aIdCode.contentEquals("")) {
      return tagRepo.deleteTag(aIdCode);
    } else {
      return null;
    }
  }
}
