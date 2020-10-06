package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Gallery;
import com.ecse321.visart.model.User;

@Repository
public class UserRepository {

	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public User createUser(String emailAddress, String username, String password, String idCode, String displayName, String galleryName) {
		Gallery gallery = new Gallery(galleryName);
		User usr = new User(emailAddress, username, password, idCode, displayName, gallery);
		
		
		
		return usr;
		
	}
	
}
