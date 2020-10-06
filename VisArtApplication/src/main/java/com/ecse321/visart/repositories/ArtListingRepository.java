package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Manager;




@Repository
public class ArtListingRepository {
	
	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public ArtListing createArtListing(PostVisibility aVisibility, String aIdCode, Manager aManager, Customer aFavoritedCustomer, Artist aArtist) {
		
		ArtListing al = new ArtListing(aVisibility, aIdCode, aManager, aFavoritedCustomer, aArtist);
		entityManager.persist(al);
		return al;
	}
	
	@Transactional
	public ArtListing getArtListing(String aIdCode) {
		return entityManager.find(ArtListing.class, aIdCode);
	}
	
}
