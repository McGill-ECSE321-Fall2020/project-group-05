/**
 * @author Riad El Mahmoudy
 */
package com.ecse321.visart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Ticket;
import com.ecse321.visart.repositories.ArtOrderRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.CustomerRepository;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.TicketRepository;

@Service
public class TicketService {

  @Autowired
  TicketRepository ticketRepo;

  @Autowired
  EntityRepository entityRepo;

  @Autowired
  ArtOrderRepository aoRepo;

  @Autowired
  ArtistRepository artistRepo;

  @Autowired
  CustomerRepository customerRepo;

  /**
   * createTicket creates Ticket and attaches it to an ArtOrder (by given id), A
   * customer and
   * An artist, also given by id. Input Strings cannot be null, keyword cannot be
   * empty or longer
   * than 1000 characters. In addition, the ids used must return an object
   * 
   * @param  aIsPaymentConfirmed boolean that checks if the payment is confirmed
   * @param  aPaymentAmount      price of the artpiece linked to the order
   * @param  aIdCode             id of the ticket
   * @param  aOrder              order id linked to the ticket
   * @param  aCustomer           customer id represents the buyer
   * @param  aArtist             artist id that represents the seller
   * @return                     new ticket with the parameters
   */
  @Transactional
  public Ticket createTicket(boolean aIsPaymentConfirmed, double aPaymentAmount,
      String aOrder, String aCustomer, String aArtist) {
    String aIdCode = EntityRepository.getUniqueKey();
    if (aIdCode == null || aIdCode == "") {
      throw new IllegalArgumentException("Ticket id code cannot be empty!");
    }

    else if (aOrder == null) {
      throw new IllegalArgumentException("Ticket order cannot be empty!");
    }

    else if (aCustomer == null) {
      throw new IllegalArgumentException("Ticket customer cannot be empty!");
    }

    else if (aArtist == null) {
      throw new IllegalArgumentException("Ticket artist cannot be empty!");
    }

    else if (aPaymentAmount < 0) {
      throw new IllegalArgumentException("Ticket payment amount cannot be negative!");
    }

    else if (aCustomer.equals(aArtist)) {
      throw new IllegalArgumentException("Ticket customer and artist cannot be the same!");
    }

    ArtOrder aOrderObj = aoRepo.getArtOrder(aOrder);
    if (aOrderObj == null) {
      throw new IllegalArgumentException("Ticket Order should exist!");
    }
    Artist aArtistObj = artistRepo.getArtist(aArtist);
    if (aArtistObj == null) {
      throw new IllegalArgumentException("Ticket Artist should exist!");
    }
    Customer aCustomerObj = customerRepo.getCustomer(aCustomer);
    if (aCustomerObj == null) {
      throw new IllegalArgumentException("Ticket Customer should exist!");
    }
    return ticketRepo.createTicket(aIsPaymentConfirmed, aPaymentAmount, aIdCode, aOrderObj,
        aCustomerObj,
        aArtistObj);
  }

  /**
   * getTicket retrieves a Ticket from the database by id.
   * 
   * @param  aIdCode
   * @return         Ticket with ID
   */
  @Transactional
  public Ticket getTicket(String aIdCode) {
    Ticket t = ticketRepo.getTicket(aIdCode);
    t.getOrder().getArtPiece().getArtListing();
    return t;
  }

  /**
   * getAllTickets gets all tickets from the database
   * 
   * @return list of all tickets
   */
  @Transactional
  public List<Ticket> getAllTickets() {
    return entityRepo.getAllEntities(Ticket.class);
  }

  /**
   * updateTicket updates a given ticket's attributes. aIdCode specifies the
   * Ticket to
   * update, and subsequent parameters update the Ticket's attributes, unless the
   * given parameter is null, in which case the Ticket's attribute remains
   * unchanged.
   * aOrder, aArtist and aCustomer must be a valid id of an existing ArtOrder,
   * Artist and
   * Customer
   * 
   * @param  aIsPaymentConfirmed
   * @param  aPaymentAmount
   * @param  aIdCode
   * @param  aOrder
   * @param  aCustomer
   * @param  aArtist
   * @return
   */
  @Transactional
  public Ticket updateTicket(Boolean aIsPaymentConfirmed, Double aPaymentAmount, String aIdCode,
      String aOrder, String aCustomer, String aArtist) {
    if (aIdCode == null || aIdCode.length() < 1) {
      throw new IllegalArgumentException("Ticket id code cannot be empty!");
    }
    Ticket ticket = ticketRepo.getTicket(aIdCode);
    if (ticket == null) {
      throw new IllegalArgumentException("Tag id must be a valid id!");
    }

    if (aCustomer != null && aCustomer.equals(aArtist)) {
      throw new IllegalArgumentException("Ticket customer and artist cannot be the same!");
    }

    if (aOrder != null) {
      ArtOrder aOrderObj = aoRepo.getArtOrder(aOrder);
      if (aOrderObj == null) {
        throw new IllegalArgumentException("Ticket Order should exist!");
      }
      ticket.setOrder(aOrderObj);
    }

    if (aCustomer != null) {
      Customer aCustomerObj = customerRepo.getCustomer(aCustomer);
      if (aCustomerObj == null) {
        throw new IllegalArgumentException("Ticket Customer should exist!");
      }
      ticket.setCustomer(aCustomerObj);
    }

    if (aArtist != null) {
      Artist aArtistObj = artistRepo.getArtist(aArtist);
      if (aArtistObj == null) {
        throw new IllegalArgumentException("Ticket Artist should exist!");
      }
      ticket.setArtist(aArtistObj);
    }

    if (aPaymentAmount != null) {

      if (aPaymentAmount < 0) {
        throw new IllegalArgumentException("Ticket payment amount cannot be negative!");
      }
      ticket.setPaymentAmount(aPaymentAmount);
    }
    if (aIsPaymentConfirmed != null) {
      ticket.setIsPaymentConfirmed(aIsPaymentConfirmed);
    }

    ticketRepo.updateTicket(ticket);
    return ticket;
  }

  /**
   * deleteTicket deletes the ticket from the database given a ticket object
   * 
   * @param  ticket
   * @return
   */
  @Transactional
  public boolean deleteTicket(Ticket ticket) {

    return ticketRepo.deleteTicket(ticket.getIdCode());
  }

  /**
   * deleteTicket deletes the ticket from the database given a ticket id
   * 
   * @param  aIdCode
   * @return
   */
  @Transactional
  public boolean deleteTicket(String aIdCode) {

    return ticketRepo.deleteTicket(aIdCode);
  }

}
