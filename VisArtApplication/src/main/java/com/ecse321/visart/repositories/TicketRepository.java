package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Ticket;




@Repository
public class TicketRepository {
	
	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public Ticket createTicket(boolean aIsPaymentConfirmed, double aPaymentAmount, String aIdCode, ArtOrder aOrder, Customer aCustomer, Artist aArtist) {
		Ticket tic = new Ticket(aIsPaymentConfirmed, aPaymentAmount, aIdCode, aOrder, aCustomer, aArtist);
		entityManager.persist(tic);
		return tic;
	}
	
	@Transactional
	public Ticket getTicket(String aIdCode) {
		return entityManager.find(Ticket.class, aIdCode);
	}
	
	@Transactional
	public boolean deleteTicket(Ticket t) {
		Ticket entity = entityManager.find(Ticket.class, t.getIdCode());
		if(entityManager.contains(entity)) {
			entityManager.remove(entityManager.merge(entity));
		} else {
			entityManager.remove(entity);
		}
		return !entityManager.contains(entity);
	}
	
}
