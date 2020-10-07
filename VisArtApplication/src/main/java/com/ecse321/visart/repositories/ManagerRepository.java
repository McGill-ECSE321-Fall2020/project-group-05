package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Gallery;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.UserRole;

@Repository
public class ManagerRepository {
	
	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public Manager createManager(String aIdCode, String aEmailAddress, String aDisplayname, String aUsername, String aPassword) {
		User usr = new User(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword);
		Manager manager = new Manager(aIdCode, usr);
		entityManager.persist(usr);
		entityManager.persist(manager);
		return manager;
	}
	
	@Transactional 
	public Manager getManager(String aIdCode) {
		return entityManager.find(Manager.class, aIdCode);
	}
	
	

}
