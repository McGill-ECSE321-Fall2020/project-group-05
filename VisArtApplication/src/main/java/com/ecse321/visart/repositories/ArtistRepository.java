package com.ecse321.visart.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.User;

/**
 * CRUD Repository operations for Artist objects.
 * 
 * @author Nikola Milekic
 * @author Daniel Bucci
 */
@Repository
public class ArtistRepository {

  @Autowired
  EntityManager entityManager;

  @Autowired
  CustomerRepository customerRepo;

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
   * This method creates an Artist instance that is persisted in the database. It
   * needs a persisted Customer instance to attach to, as Customers are assigned
   * to be Artists.
   * 
   * @param  aIdCode   the database primary key for this artist
   * @param  aCustomer Customer object that this Artist is attached to
   * @return           an instance of Artist
   */
  @Transactional
  public Artist createArtist(String aIdCode, Customer aCustomer) {
    Artist artist = new Artist(aIdCode, aCustomer);
    entityManager.persist(artist);
    entityManager.merge(aCustomer);
    return artist;
  }

  /**
   * updateArtist method updates the given Artist instance's properties in the
   * database.
   * 
   * @param artist the artist whose properties will be updated in the database
   */
  @Transactional
  public void updateArtist(Artist artist) {
    entityManager.merge(artist);
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
    return getArtist(aIdCode, false, false);
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
    Artist a = entityManager.find(Artist.class, aIdCode);
    if (alsoTickets)
      a.getSoldTickets().size();
    if (alsoListings)
      a.getPostedListings().size();
    return a;
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
    Artist entity = entityManager.find(Artist.class, id);
    Customer customer = entityManager.find(Customer.class, entity.getCustomer().getIdCode());
    if (customer != null && customer.getArtist() != null)
      customer.getArtist().delete();
    customerRepo.updateCustomer(customer);

    if (entityManager.contains(entity)) {
      entityManager.remove(entityManager.merge(entity));
    } else {
      entityManager.remove(entity);
    }
    return !entityManager.contains(entity);
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
    return deleteArtist(artist.getIdCode());
  }

  @Transactional
  public List<String> getAllKeys() {
    // Query q = ;
    // CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    // CriteriaQuery<Artist> query = cb.createQuery(Artist.class);
    // Root<Artist> artist = query.from(Artist.class);
    //
    // Path<String> id_path = artist.get("id_code");
    //
    // query.select(artist).

    return entityManager.createQuery("SELECT idCode FROM Artist", String.class).getResultList();
  }
}
