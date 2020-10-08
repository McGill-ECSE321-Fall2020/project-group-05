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
	public Ticket createTicket(boolean aIsPaymentConfirmed, double aPaymentAmount, String aIdCode, boolean aIsDeliveredForOrder, PieceLocation aTargetLocationForOrder, String aTargetAddressForOrder, String aDeliveryTrackerForOrder, String aIdCodeForOrder, ArtPiece aArtPieceForOrder, Customer aCustomer, Artist aArtist) {
		Ticket tic = new Ticket(aIsPaymentConfirmed, aPaymentAmount, aIdCode, aIsDeliveredForOrder, aTargetLocationForOrder, aTargetAddressForOrder, aDeliveryTrackerForOrder, aIdCodeForOrder, aArtPieceForOrder, aCustomer, aArtist);
		entityManager.persist(tic);
		entityManager.persist(tic.getOrder());
		return tic;
	}
	
	@Transactional
	public Ticket getTicket(String aIdCode) {
		return entityManager.find(Ticket.class, aIdCode);
	}
	
}
