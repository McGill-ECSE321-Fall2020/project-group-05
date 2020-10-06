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
	public ArtOrder createArtOrder(boolean aIsDelivered, PieceLocation aTargetLocation, String aTargetAddress, String aDeliveryTracker, String aIdCode, ArtPiece aArtPiece, Ticket aTicket) {
		
		ArtOrder ao = new ArtOrder(aIsDelivered, aTargetLocation, aTargetAddress, aDeliveryTracker, aIdCode, aArtPiece, aTicket);
		entityManager.persist(ao);
		return ao;
	}
	
	@Transactional
	public ArtOrder createArtOrder(boolean aIsDelivered, PieceLocation aTargetLocation, String aTargetAddress, String aDeliveryTracker, String aIdCode, PieceLocation aBasicLocationForArtPiece, String aAddressLocationForArtPiece, String aIdCodeForArtPiece, ArtListing aArtListingForArtPiece, boolean aIsPaymentConfirmedForTicket, double aPaymentAmountForTicket, String aIdCodeForTicket, Customer aCustomerForTicket, Artist aArtistForTicket) {
		
		ArtOrder ao = new ArtOrder(aIsDelivered, aTargetLocation, aTargetAddress, aDeliveryTracker, aIdCode, aBasicLocationForArtPiece, aAddressLocationForArtPiece, aIdCodeForArtPiece, aArtListingForArtPiece, aIsPaymentConfirmedForTicket, aPaymentAmountForTicket, aIdCodeForTicket, aCustomerForTicket, aArtistForTicket);
		entityManager.persist(ao);
		return ao;
	}
	
	@Transactional
	public ArtOrder getArtOrder(String aIdCode) {
		return entityManager.find(ArtOrder.class, aIdCode);
	}
	
}
