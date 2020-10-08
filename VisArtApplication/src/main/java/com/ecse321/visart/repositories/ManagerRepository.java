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
	
	@Transactional
	public Manager changeManagerUsername(String aIdCode, String aUsername) {
		Manager manager = entityManager.find(Manager.class, aIdCode);
		
		entityManager.getTransaction().begin();
		manager.getUser().setUsername(aUsername);
		
		entityManager.getTransaction().commit();
		
		return manager;
	}
	
	@Transactional
	public Manager changeManagerDisplayname(String aIdCode, String aDisplayname) {
		Manager manager = entityManager.find(Manager.class, aIdCode);
		
		entityManager.getTransaction().begin();
		manager.getUser().setDisplayname(aDisplayname);
		
		entityManager.getTransaction().commit();
		
		return manager;
	}
	
	@Transactional
	public Manager changeManagerEmail(String aIdCode, String aEmail) {
		Manager manager = entityManager.find(Manager.class, aIdCode);
		
		entityManager.getTransaction().begin();
		manager.getUser().setEmailAddress(aEmail);
	
		entityManager.getTransaction().commit();
		
		return manager;
	}
	
	@Transactional
	public Manager changeManagerPassword(String aIdCode, String aPassword) {
		Manager manager = entityManager.find(Manager.class, aIdCode);
		
		entityManager.getTransaction().begin();
		manager.getUser().setPassword(aPassword);
	
		entityManager.getTransaction().commit();
		
		return manager;
	}
	
	
	@Transactional 
	public void deleteManager(String aIdCode) {
		Manager manager = entityManager.find(Manager.class, aIdCode);
		entityManager.remove(manager);
	}

}
