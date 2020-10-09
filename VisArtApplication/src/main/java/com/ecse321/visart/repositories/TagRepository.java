package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.model.Tag.TagType;

/**
 * 
 * @author anwar
 *
 */

@Repository
public class TagRepository {

  @Autowired
  EntityManager entityManager;

  /**
   * createTag
   * 
   * This method creates a tag instance for an art listing that is persisted in
   * the database
   * 
   * @param  aType    Type of tag for a listing
   * @param  aKeyword keywords associated
   * @param  aIdCode  database Id for this tag
   * @param  aListing specific art listing
   * @return          persisted tag instance
   */
  @Transactional
  public Tag createTag(TagType aType, String aKeyword, String aIdCode, ArtListing aListing) {
    Tag tic = new Tag(aType, aKeyword, aIdCode, aListing);
    entityManager.persist(tic);
    return tic;
  }

  /**
   * getTag
   * 
   * This method retrieves a tag instance for an art listing from the database
   * 
   * @param  aIdCode database Id for this tag
   * @return         retrieves tag information
   */
  @Transactional
  public Tag getTag(String aIdCode) {
    return entityManager.find(Tag.class, aIdCode);
  }

  /**
   * updateTag
   * 
   * This method updates tag information for a specific listing
   * 
   * @param t 
   */
  @Transactional
  public void updateTag(Tag t) {
    entityManager.merge(t);
  }

  /**
   * deleteTag
   * 
   * This method deletes a tag instance for an art listing from the database
   * 
   * @param  t
   * @return
   */
  @Transactional
  public boolean deleteTag(Tag t) {
    Tag entity = entityManager.find(Tag.class, t.getIdCode());
    if (entityManager.contains(entity)) {
      entityManager.remove(entityManager.merge(entity));
    } else {
      entityManager.remove(entity);
    }

    return (!entityManager.contains(entity));
  }

}
