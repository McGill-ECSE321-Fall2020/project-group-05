package com.ecse321.visart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Ticket;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.CustomerRepository;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.TicketRepository;

@Service
public class ArtistService {

  @Autowired
  ArtistRepository artistRepo;

  @Autowired
  CustomerRepository customerRepo;

  @Autowired
  TicketRepository ticketRepo;

  @Autowired
  ArtListingRepository artListingRepo;

  @Autowired
  EntityRepository entityRepo;

  /**
   * 
   * This method creates an Artist instance that is persisted in the database.
   * This method also creates a User given a Customer id to attach to, which are
   * all persisted in the database.
   * 
   * @param  customerId the id of the customer to add an Artist to
   * @return            persisted Artist instance
   */
  @Transactional
  public Artist createArtist(String customerId) {
    String key = EntityRepository.getUniqueKey();

    if (customerId == null || customerId.length() < 1) {
      throw new IllegalArgumentException("Customer id code cannot be empty!");
    }
    Customer customer = customerRepo.getCustomer(customerId);
    if (customer == null) {
      throw new IllegalArgumentException("Customer id must be valid id of existing customer!");
    }

    return artistRepo.createArtist(key, customer);
  }

  /**
   * updateArtist method updates the given Artist instance's properties in the
   * database.
   * 
   * @param artist the artist whose properties will be updated in the database
   */
  @Transactional
  public void updateArtist(String artistId) {
    Artist artist = artistRepo.getArtist(artistId);
    artistRepo.updateArtist(artist);
  }

  /**
   * addListings adds a list of ArtListings to an Artist. The list must not be
   * empty or null, and the ids must all be of valid, existing objects in the
   * database.
   * 
   * @param artistId   the id of the artist to add listings to
   * @param listingIds the list of listing ids to add to the artist
   */
  @Transactional
  public void addListings(String artistId, List<String> listingIds) {
    Artist artist = artistRepo.getArtist(artistId, false, true);
    if (artist == null) {
      throw new IllegalArgumentException("Artist id must be valid id of existing artist!");
    }
    if (listingIds == null) {
      throw new IllegalArgumentException("list of art listing ids must not be null!");
    }
    List<ArtListing> listings = listingIds.stream().map(id -> artListingRepo.getArtListing(id))
        .collect(Collectors.toList());
    if (listings.stream().anyMatch(listing -> listing == null)) {
      throw new IllegalArgumentException(
          "art listing id must be valid id of existing art listing!");
    }
    for (ArtListing listing : listings) {
      artist.addPostedListing(listing);
    }
    artistRepo.updateArtist(artist);
  }

  /**
   * removeListings removes a list of ArtListings from an Artist. The list must
   * not be empty or null, and the ids must all be of valid, existing objects in
   * the database, which are added to the Artist object specified by the id.
   * 
   * @param artistId   the id of the artist to remove listings from
   * @param listingIds the list of listing ids to remove from the artist
   */
  @Transactional
  public void removeListings(String artistId, List<String> listingIds) {
    Artist artist = artistRepo.getArtist(artistId, false, true);
    if (artist == null) {
      throw new IllegalArgumentException("Artist id must be valid id of existing artist!");
    }
    if (listingIds == null) {
      throw new IllegalArgumentException("list of art listing ids must not be null!");
    }

    List<ArtListing> listings = listingIds.stream().map(id -> artListingRepo.getArtListing(id))
        .collect(Collectors.toList());
    if (listings.stream().anyMatch(listing -> listing == null
        || !listing.getArtist().getIdCode().equals(artist.getIdCode()))) {
      throw new IllegalArgumentException(
          "art listing id must be valid id of existing art listing belonging to artist!");
    }
    for (ArtListing listing : listings) {
      artist.removePostedListing(listing);
    }
    artistRepo.updateArtist(artist);
  }

  /**
   * addSoldTickets adds Tickets to an Artist specified by id. The list must not
   * be empty or null, and the ids must all be of valid, existing objects in the
   * database.
   * 
   * @param artistId  the id of the artist to add tickets to
   * @param ticketIds the list of ticket ids to add to the artist
   */
  @Transactional
  public void addSoldTickets(String artistId, List<String> ticketIds) {
    Artist artist = artistRepo.getArtist(artistId, true, false);
    if (artist == null) {
      throw new IllegalArgumentException("Artist id must be valid id of existing artist!");
    }
    if (ticketIds == null) {
      throw new IllegalArgumentException("list of ticket ids must not be null!");
    }

    List<Ticket> tickets = ticketIds.stream().map(id -> ticketRepo.getTicket(id))
        .collect(Collectors.toList());
    if (tickets.stream().anyMatch(ticket -> ticket == null)) {
      throw new IllegalArgumentException(
          "ticket id must be valid id of existing ticket!");
    }

    for (Ticket ticket : tickets) {
      artist.addSoldTicket(ticket);
    }
    artistRepo.updateArtist(artist);
  }

  /**
   * removeSoldTickets removes Tickets from an Artist specified by id. The list
   * must not be empty or null, and the ids must all be of valid, existing objects
   * in the database, added to the Artist.
   * 
   * @param artistId  the id of the artist to remove tickets from
   * @param ticketIds the list of ticket ids to remove from the artist
   */
  @Transactional
  public void removeSoldTickets(String artistId, List<String> ticketIds) {
    Artist artist = artistRepo.getArtist(artistId, true, false);
    if (artist == null) {
      throw new IllegalArgumentException("Artist id must be valid id of existing artist!");
    }
    if (ticketIds == null) {
      throw new IllegalArgumentException("list of ticket ids must not be null!");
    }

    List<Ticket> tickets = ticketIds.stream().map(id -> ticketRepo.getTicket(id))
        .collect(Collectors.toList());
    if (tickets.stream().anyMatch(
        ticket -> ticket == null || !ticket.getArtist().getIdCode().equals(artist.getIdCode()))) {
      throw new IllegalArgumentException(
          "ticket id must be valid id of existing ticket!");
    }

    for (Ticket ticket : tickets) {
      artist.removeSoldTicket(ticket);
    }
    artistRepo.updateArtist(artist);
  }

  /**
   * 
   * Overloaded getArtist method, that by default, does not load collections of
   * tickets or artListings.
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
