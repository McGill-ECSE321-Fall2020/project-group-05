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
import com.ecse321.visart.model.Tag;
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
   * 
   * @param aIsPaymentConfirmed
   * @param aPaymentAmount
   * @param aIdCode
   * @param aOrder
   * @param aCustomer
   * @param aArtist
   * @return new ticket with the parameters
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
    
    else if (aPaymentAmount<0) {
    	throw new IllegalArgumentException("Ticket payment amount cannot be negative!");
    }
    
    else if (aCustomer.equals(aArtist)) {
    	throw new IllegalArgumentException("Ticket customer and artist cannot be the same!");
    }
    
    ArtOrder aOrderObj = aoRepo.getArtOrder(aOrder);
    if(aOrderObj == null) {
    	 throw new IllegalArgumentException("Ticket Order should exist!");
    }
    Artist aArtistObj = artistRepo.getArtist(aArtist);
    if(aArtistObj == null) {
   	 throw new IllegalArgumentException("Ticket Artist should exist!");
   }
    Customer aCustomerObj = customerRepo.getCustomer(aCustomer);
    if(aCustomerObj == null) {
   	 throw new IllegalArgumentException("Ticket Customer should exist!");
   }
    return ticketRepo.createTicket(aIsPaymentConfirmed, aPaymentAmount, aIdCode, aOrderObj, aCustomerObj,
        aArtistObj);
  }

  /**
   * 
   * @param aIdCode
   * @return Ticket with ID
   */
  @Transactional
  public Ticket getTicket(String aIdCode) {
    return ticketRepo.getTicket(aIdCode);
  }
  
  /**
   * 
   * @return list of all tickets
   */
  @Transactional
  public List<Ticket> getAllTickets() {
    return entityRepo.getAllEntities(Ticket.class);
  }
  
  /**
   * 
   * @param aIsPaymentConfirmed
   * @param aPaymentAmount
   * @param aIdCode
   * @param aOrder
   * @param aCustomer
   * @param aArtist
   * @return
   */
  @Transactional
  public Ticket updateTicket(boolean aIsPaymentConfirmed, double aPaymentAmount, String aIdCode,
      String aOrder, String aCustomer, String aArtist) {
    if (aIdCode == null || aIdCode.length()<1) {
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
    
    else if (aPaymentAmount<0) {
    	throw new IllegalArgumentException("Ticket payment amount cannot be negative!");
    }
    
    else if (aCustomer.equals(aArtist)) {
    	throw new IllegalArgumentException("Ticket customer and artist cannot be the same!");
    }
    Ticket ticket = ticketRepo.getTicket(aIdCode);
    if (ticket == null) {
      throw new IllegalArgumentException("Tag id must be a valid id!");
    }
   
    ArtOrder aOrderObj = aoRepo.getArtOrder(aOrder);
    if(aOrderObj == null) {
    	 throw new IllegalArgumentException("Ticket Order should exist!");
    }
    Artist aArtistObj = artistRepo.getArtist(aArtist);
    if(aArtistObj == null) {
   	 throw new IllegalArgumentException("Ticket Artist should exist!");
   }
    Customer aCustomerObj = customerRepo.getCustomer(aCustomer);
    if(aCustomerObj == null) {
   	 throw new IllegalArgumentException("Ticket Customer should exist!");
   }
    
	ticket.setArtist(aArtistObj);
	ticket.setCustomer(aCustomerObj);
	ticket.setIsPaymentConfirmed(aIsPaymentConfirmed);
	ticket.setOrder(aOrderObj);
	ticket.setPaymentAmount(aPaymentAmount);
	
	ticketRepo.updateTicket(ticket);
	return ticket;
  }
  
  /**
   * 
   * @param ticket
   * @return
   */
  @Transactional
  public boolean deleteTicket(Ticket ticket) {

    return ticketRepo.deleteTicket(ticket.getIdCode());
  }
  
  /**
   * 
   * @param aIdCode
   * @return
   */
  @Transactional
  public boolean deleteTicket(String aIdCode) {

    return ticketRepo.deleteTicket(aIdCode);
  }

}
