package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Gallery;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.UserRole;

@Repository
public class ArtistRepository {
	
	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public Artist createArtist(String aIdCode, String aEmailAddress, String aDisplayname, String aUsername, String aPassword) {
		User usr = new User(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword);
		Customer customer = new Customer(aIdCode, usr);
		Artist artist = new Artist(aIdCode, customer);
		entityManager.persist(artist);
		return artist;
	}
	
	@Transactional
	public Artist getArtist(String aIdCode) {
		return entityManager.find(Artist.class, aIdCode);
	}

}
