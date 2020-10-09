package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.User;

/**
 * 
 * @author anwar
 * 
 *
 */
@Repository
public class ArtistRepository {

  @Autowired
  EntityManager entityManager;

  /**
   * 
   * This method creates a createArtist instance that is persisted in the database
   * 
   * @param  aIdCode             database Id for the artist
   * @param  aEmailAddress       email address of the artist
   * @param  aDisplayname        Full name of the artist
   * @param  aUsername           User name for the profile to be created
   * @param  aPassword           Password for the profile to be created
   * @param  aProfilePicLink     Profile picture link of the artist
   * @param  aProfileDescription Short description about the artist
   * @return                     persisted createArtist instance
   */
  @Transactional
  public Artist createArtist(String aIdCode, String aEmailAddress, String aDisplayname,
      String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription) {
    User usr = new User(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword, aProfilePicLink,
        aProfileDescription);
    Customer customer = new Customer(aIdCode, usr);
    Artist artist = new Artist(aIdCode, customer);
    entityManager.persist(usr);
    entityManager.persist(customer);
    entityManager.persist(artist);
    return artist;
  }

  /**
   * createArtist
   * 
   * This method creates a createArtist instance that is persisted in the database
   * 
   * @param  aIdCode   database Id for this artist
   * @param  aCustomer Customer information
   * @return           an instance of createArtist
   */
  @Transactional
  public Artist createArtist(String aIdCode, Customer aCustomer) {
    Artist artist = new Artist(aIdCode, aCustomer);
    entityManager.persist(artist);
    return artist;
  }

  @Transactional
  public Artist getArtist(String aIdCode) {
    return entityManager.find(Artist.class, aIdCode);
  }

  /**
   * deleteArtist
   * 
   * This method removes an artist from the database
   * 
   * @param  artist specific artist
   * @return
   */
  @Transactional
  public boolean deleteArtist(Artist artist) {
    Artist entity = entityManager.find(Artist.class, artist.getIdCode());
    if (entityManager.contains(entity)) {
      entityManager.remove(entityManager.merge(entity));
    } else {
      entityManager.remove(entity);
    }
    return !entityManager.contains(entity);
  }

}
