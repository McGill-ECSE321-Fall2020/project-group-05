package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.model.Ticket;





@Repository
public class ArtOrderRepository {
	
	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public ArtOrder createArtOrder(boolean aIsDelivered, PieceLocation aTargetLocation, String aTargetAddress, String aDeliveryTracker, String aIdCode, ArtPiece aArtPiece) {
		
		ArtOrder ao = new ArtOrder(aIsDelivered, aTargetLocation, aTargetAddress, aDeliveryTracker, aIdCode, aArtPiece);
		entityManager.persist(ao);
		return ao;
	}
	
	@Transactional
	public ArtOrder createArtOrder(boolean aIsDelivered, PieceLocation aTargetLocation, String aTargetAddress, String aDeliveryTracker, String aIdCode, ArtPiece aArtPiece, boolean aIsPaymentConfirmedForTicket, double aPaymentAmountForTicket, String aIdCodeForTicket, Customer aCustomerForTicket, Artist aArtistForTicket) {
		
		ArtOrder ao = new ArtOrder(aIsDelivered, aTargetLocation, aTargetAddress, aDeliveryTracker, aIdCode, aArtPiece);

		entityManager.persist(ao.getTicket());

		entityManager.persist(ao);
		return ao;
	}
	
	@Transactional
	public ArtOrder getArtOrder(String aIdCode) {
		return entityManager.find(ArtOrder.class, aIdCode);
	}
	
}
