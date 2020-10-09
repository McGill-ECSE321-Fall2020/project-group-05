package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.User;

@Repository
public class ArtistRepository {

	@Autowired
	EntityManager entityManager;

	@Transactional
	public Artist createArtist(String aIdCode, String aEmailAddress, String aDisplayname, String aUsername,
			String aPassword, String aProfilePicLink, String aProfileDescription) {
		User usr = new User(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword, aProfilePicLink,
				aProfileDescription);
		Customer customer = new Customer(aIdCode, usr);
		Artist artist = new Artist(aIdCode, customer);
		entityManager.persist(usr);
		entityManager.persist(customer);
		entityManager.persist(artist);
		return artist;
	}

	/**
	 * 
	 * @param aIdCode
	 * @param aCustomer a persisted Customer object
	 * @return
	 */
	@Transactional
	public Artist createArtist(String aIdCode, Customer aCustomer) {
		Artist artist = new Artist(aIdCode, aCustomer);
		entityManager.persist(artist);
		return artist;
	}

	@Transactional
	public Artist getArtist(String aIdCode) {
		return entityManager.find(Artist.class, aIdCode);
	}

	@Transactional
	public boolean deleteArtist(Artist artist) {
		Artist entity = entityManager.find(Artist.class, artist.getIdCode());
		if (entityManager.contains(entity)) {
			entityManager.remove(entityManager.merge(entity));
		} else {
			entityManager.remove(entity);
		}
		return !entityManager.contains(entity);
	}

}
