package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
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
	public void updateManager(Manager manager) {
		entityManager.merge(manager);
	}
	
	
	@Transactional 
	public boolean deleteManager(Manager manager) {
		Manager entity = entityManager.find(Manager.class, manager.getIdCode());
		User usr = entityManager.find(User.class, manager.getIdCode());
		if(entityManager.contains(entity)){
			entityManager.remove(entityManager.merge(entity));
			entityManager.remove(entityManager.merge(usr));
		} else {
			entityManager.remove(entity);
			entityManager.remove(usr);
		}
		
		return !entityManager.contains(entity);
	}

}
