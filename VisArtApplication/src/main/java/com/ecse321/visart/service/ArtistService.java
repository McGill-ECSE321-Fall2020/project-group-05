package com.ecse321.visart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.User;
import com.ecse321.visart.repositories.ArtOrderRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.EntityRepository;

@Service
public class ArtistService {

  @Autowired
  ArtistRepository artistRepo;

  @Autowired
  EntityRepository entityRepo;

  /**
   * 
   * This method creates an Artist instance that is persisted in the database.
   * This method also creates a User and Customer attached to it, which are also
   * persisted in the database.
   * 
   * @param  aIdCode             the database primary key for the artist
   * @param  aEmailAddress       email address of the artist
   * @param  aDisplayname        Full name of the artist
   * @param  aUsername           User name for the profile to be created
   * @param  aPassword           Password for the profile to be created
   * @param  aProfilePicLink     Profile picture link of the artist
   * @param  aProfileDescription Short description about the artist
   * @return                     persisted Artist instance
   */
  @Transactional
  public Artist createArtist(String aIdCode, String aEmailAddress, String aDisplayname,
      String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription) {

    if (aIdCode == null || aIdCode == "") {
      throw new IllegalArgumentException("User id code cannot be empty!");
    }

    if (aEmailAddress == null || aEmailAddress == "") {
      throw new IllegalArgumentException("Email Address cannot be empty!");
    }

    if (aDisplayname == null || aDisplayname == "") {
      throw new IllegalArgumentException("Display name cannot be empty!");
    }

    if (aUsername == null || aUsername == "") {
      throw new IllegalArgumentException("Username cannot be empty!");
    }

    if (aPassword == null || aPassword == "") {
      throw new IllegalArgumentException("Password cannot be empty!"); // add more constraints
    }

    if (aProfilePicLink == null || aProfilePicLink == "") {
      throw new IllegalArgumentException("Profile picture link cannot be empty!");
    }

    if (aProfileDescription == null || aProfileDescription == "") {
      throw new IllegalArgumentException("Profile picture description cannot be empty!");
    }

    return artistRepo.createArtist(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword,
        aProfilePicLink, aProfileDescription);
  }

  /**
   * updateArtist method updates the given Artist instance's properties in the
   * database.
   * 
   * @param artist the artist whose properties will be updated in the database
   */
  @Transactional
  public void updateArtist(Artist artist) {
    artistRepo.updateArtist(artist);
  }

  /**
   * 
   * Overloaded getArtist method, that by lazy loads all nested Collections by
   * default.
   * 
   * @param  aIdCode the database primary key
   * @return         a persisted Artist instance, loaded from the database
   */
  @Transactional
  public Artist getArtist(String aIdCode) {
    return artistRepo.getArtist(aIdCode);
  }

  /**
   * getArtist retrieves an Artist instance from the database, based on the
   * primary key, aIdCode. This instance performs lazy loading by default, but the
   * options alsoTickets and alsoListings can be set to Eager Load all of the
   * objects from the database that are attached to this object as well.
   * 
   * @param  aIdCode      the database primary key of Artist
   * @param  alsoTickets  set true to also fetch the tickets
   * @param  alsoListings set true to also fetch art listings
   * @return              a persisted Artist instance loaded from the databse
   */
  @Transactional
  public Artist getArtist(String aIdCode, boolean alsoTickets, boolean alsoListings) {

    return artistRepo.getArtist(aIdCode, alsoTickets, alsoListings);
  }

  /**
   * deleteArtist method removes an artist from the database, and de-references it
   * from its Customer object. This method also deletes the the underlying
   * artist's referenced objects.
   * 
   * @param  id the primary key of the artist entity to delete
   * @return    true for successful delete
   */
  @Transactional
  public boolean deleteArtist(String id) {

    return artistRepo.deleteArtist(id);
  }

  /**
   * Overloaded deleteArtist(String) method to delete the given artist entity from
   * the database.
   * 
   * @param  artist the entity to remove from the database
   * @return        true if successful
   */
  @Transactional
  public boolean deleteArtist(Artist artist) {
    return artistRepo.deleteArtist(artist);
  }

  /**
   * getAllKeys queries the database for all of the primary keys of the Artists
   * instances.
   * 
   * @return list of primary keys for Artists
   */
  @Transactional
  public List<String> getAllKeys() {
    return artistRepo.getAllKeys();
  }

}
