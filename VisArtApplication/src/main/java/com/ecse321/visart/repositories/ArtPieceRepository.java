package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.Ticket;
import com.ecse321.visart.model.User;

@Repository
public class ArtPieceRepository {

	@Autowired
	EntityManager entityManager;
	ArtOrderRepository aoRepository;
	/**
	 * 
	 * @param idCode
	 * @return
	 */
	@Transactional
	public ArtPiece getArtPiece(String idCode) {
		return entityManager.find(ArtPiece.class, idCode);
	}
	/**
	 * 
	 * @param aBasicLocation
	 * @param aAddressLocation
	 * @param aIdCode
	 * @param aArtListing
	 * @return
	 */
	@Transactional
	public ArtPiece createArtPiece(PieceLocation aBasicLocation, String aAddressLocation, String aIdCode, ArtListing aArtListing) {
		ArtPiece apiece = new ArtPiece(aBasicLocation, aAddressLocation, aIdCode, aArtListing);
		entityManager.persist(apiece);
		return apiece;
	}
	/**
	 * 
	 * @param ap
	 */
	@Transactional
	public void updateArtPiece(ArtPiece ap) {
		entityManager.merge(ap);
	}
	/**
	 * 
	 * @param ap
	 * @return
	 */
	@Transactional
	public boolean deleteArtPiece(ArtPiece ap) {
		ArtPiece ape = entityManager.find(ArtPiece.class, ap.getIdCode());
		if (entityManager.contains(ape)) {
			entityManager.remove(entityManager.merge(ape));
		} else {
			entityManager.remove(ape);
		}
		if (ap.getArtOrder()!=null) {
			aoRepository.deleteArtOrder(ap.getArtOrder());
		}
		return !entityManager.contains(ape);
	}
	
}
