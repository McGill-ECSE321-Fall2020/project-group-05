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
	public ArtPiece createArtPiece(PieceLocation aBasicLocation, String aAddressLocation, String aIdCode, ArtListing aArtListing) {
		ArtPiece apiece = new ArtPiece(aBasicLocation, aAddressLocation, aIdCode, aArtListing);
		entityManager.persist(apiece);
		return apiece;
	}
	
	
}
