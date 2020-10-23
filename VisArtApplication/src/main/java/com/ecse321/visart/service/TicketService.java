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
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.TicketRepository;

@Service
public class TicketService {
	
	@Autowired
	TicketRepository ticketRepo;
	
	@Autowired
	EntityRepository entityRepo;
	
	@Transactional
	public Ticket createTicket(boolean aIsPaymentConfirmed, double aPaymentAmount, String aIdCode,
		      ArtOrder aOrder, Customer aCustomer, Artist aArtist){
		if(aIdCode == null || aIdCode == "") {
			throw new IllegalArgumentException("Ticket id code cannot be empty!");
		}
		return ticketRepo.createTicket(aIsPaymentConfirmed, aPaymentAmount, aIdCode, aOrder, aCustomer, aArtist);
	}
	
	 @Transactional
	  public Ticket getTicket(String aIdCode) {
	    return ticketRepo.getTicket(aIdCode);
	  }
	 
	 @Transactional
	  public List<Ticket> getAllTickets() {
	    return entityRepo.getAllEntities(Ticket.class);
	  }
	  

}
