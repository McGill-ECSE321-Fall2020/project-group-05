package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.Ticket;

@Repository
public class ArtPieceRepository {

	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public ArtPiece getArtPiece(String idCode) {
		return entityManager.find(ArtPiece.class, idCode);
	}
	
	@Transactional
	public ArtPiece createArtPiece(PieceLocation aBasicLocation, String aAddressLocation, String aIdCode, ArtListing aArtListing, ArtOrder aArtOrder) {
		ArtPiece apiece = new ArtPiece(aBasicLocation, aAddressLocation, aIdCode, aArtListing, aArtOrder);
		entityManager.persist(apiece);
		return apiece;
	}
	
	@Transactional
	public ArtPiece createArtPiece(PieceLocation aBasicLocation, String aAddressLocation, String aIdCode, ArtListing aArtListing, boolean aIsDeliveredForArtOrder, PieceLocation aTargetLocationForArtOrder, String aTargetAddressForArtOrder, String aDeliveryTrackerForArtOrder, String aIdCodeForArtOrder, Ticket aTicketForArtOrder) {
		ArtPiece apiece = new ArtPiece(aBasicLocation, aAddressLocation, aIdCode, aArtListing, aIsDeliveredForArtOrder, aTargetLocationForArtOrder, aTargetAddressForArtOrder, aDeliveryTrackerForArtOrder, aIdCodeForArtOrder, aTicketForArtOrder);
		entityManager.persist(apiece);
		return apiece;
	}
	
	
}
