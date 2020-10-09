package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.model.ArtPiece;




@Repository
public class ArtListingRepository {
	
	@Autowired
	EntityManager entityManager;
	ArtPieceRepository apRepository;
	TagRepository tRepository;
	
	@Transactional
	public ArtListing createArtListing(PostVisibility aVisibility, String aIdCode, Artist aArtist) {
		
		ArtListing al = new ArtListing(aVisibility, aIdCode, aArtist);
		entityManager.persist(al);
		return al;
	}
	
	@Transactional
	public ArtListing getArtListing(String aIdCode) {
		return entityManager.find(ArtListing.class, aIdCode);
	}
	
	@Transactional
	public void updateArtListing(ArtListing al) {
		entityManager.merge(al);
	}
	
	@Transactional
	public void deleteArtListing(ArtListing al) {
		entityManager.remove(al);
		for (Tag t : al.getTags()) {
			tRepository.deleteTag(t);
		}
		for (ArtPiece t : al.getPieces()) {
			apRepository.deleteArtPiece(t);
		}
	}
	
}
