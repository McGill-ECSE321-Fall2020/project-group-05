/** 

 */

package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.model.Tag.TagType;

/**
 * CRUD Repository operations for a Tag
 * 
 * @author Nikola Milekic
 * @author Daniel Bucci
 *
 */

@Repository
public class TagRepository {

  @Autowired
  EntityManager entityManager;

  /**
   * createTag method creates a Tag instance for an ArtListing that is persisted
   * in
   * the database.
   * 
   * @param  aType    Type of tag for a listing
   * @param  aKeyword keyword of the tag
   * @param  aIdCode  database primary key for this tag
   * @param  aListing ArtListing to attach to
   * @return          persisted Tag instance
   */
  @Transactional
  public Tag createTag(TagType aType, String aKeyword, String aIdCode, ArtListing aListing) {
    Tag tic = new Tag(aType, aKeyword, aIdCode, aListing);
    entityManager.persist(tic);
    return tic;
  }

  /**
   * getTag method retrieves a Tag instance for an ArtListing from the database.
   * 
   * @param  aIdCode database primary key for Tag
   * @return         persisted Tag instance from database
   */
  @Transactional
  public Tag getTag(String aIdCode) {
    return entityManager.find(Tag.class, aIdCode);
  }

  /**
   * updateTag method updates a Tag instance's properties in the
   * database.
   * 
   * @param t the altered Tag instance whose changes write to database
   */
  @Transactional
  public void updateTag(Tag t) {
    entityManager.merge(t);
  }

  /**
   * Overloaded deleteTag method deletes the given Tag instance from the database.
   * 
   * @param  t the Tag to delete from database
   * @return   true if successful delete
   */
  @Transactional
  public boolean deleteTag(Tag t) {
    return deleteTag(t.getIdCode());
  }

  /**
   * deleteTag method removes the Tag instance from the database, given its
   * primary key.
   * 
   * @param  id the primary key of the Tag to delete
   * @return    true if successful delete
   */
  @Transactional
  public boolean deleteTag(String id) {
    Tag entity = entityManager.find(Tag.class, id);
    if (entityManager.contains(entity)) {
      entityManager.remove(entityManager.merge(entity));
    } else {
      entityManager.remove(entity);
    }

    return (!entityManager.contains(entity));
  }

}
