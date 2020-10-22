/**
 * @author Riad El Mahmoudy
 */
package com.ecse321.visart.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Ticket;
import com.ecse321.visart.repositories.TicketRepository;

public class TicketService {
	
	@Autowired
	TicketRepository ticketRepo;
	
	public Ticket createTicket(boolean aIsPaymentConfirmed, double aPaymentAmount, String aIdCode,
		      ArtOrder aOrder, Customer aCustomer, Artist aArtist){
		if(aIdCode == null || aIdCode == "") {
			throw new IllegalArgumentException("User id code cannot be empty!");
		}
		return ticketRepo.createTicket(aIsPaymentConfirmed, aPaymentAmount, aIdCode, aOrder, aCustomer, aArtist);
	}

}
