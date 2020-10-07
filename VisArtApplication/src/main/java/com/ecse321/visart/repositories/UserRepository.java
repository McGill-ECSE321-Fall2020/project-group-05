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
	public User getUser(String idCode) {
		return entityManager.find(User.class, idCode);
	}
	
	
}
