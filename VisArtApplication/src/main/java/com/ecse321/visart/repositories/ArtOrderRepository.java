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
	TicketRepository tRepository;
	
	@Transactional
	public ArtOrder createArtOrder(boolean aIsDelivered, PieceLocation aTargetLocation, String aTargetAddress, String aDeliveryTracker, String aIdCode, ArtPiece aArtPiece) {
		
		ArtOrder ao = new ArtOrder(aIsDelivered, aTargetLocation, aTargetAddress, aDeliveryTracker, aIdCode, aArtPiece);
		entityManager.persist(ao);
		return ao;
	}
	
	@Transactional
	public ArtOrder getArtOrder(String aIdCode) {
		return entityManager.find(ArtOrder.class, aIdCode);
	}
	
	@Transactional
	public void updateArtOrder(ArtOrder ao) {
		entityManager.merge(ao);
	}
	
	@Transactional
	public boolean deleteArtOrder(ArtOrder ao) {
		ArtOrder entity = entityManager.find(ArtOrder.class, ao.getIdCode());
		if(entityManager.contains(entity)) {
			entityManager.remove(entityManager.merge(entity));
		} else {
			entityManager.remove(entity);
		}
		if(ao.getTicket()!=null) {
			tRepository.deleteTicket(ao.getTicket());
		}
		return (!entityManager.contains(entity) && !entityManager.contains(ao.getTicket()));
	}
}
