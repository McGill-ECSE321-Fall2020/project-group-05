/** 
 *@author Nikola Milekic 
 *@author Daniel Bucci
 */

package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Gallery;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.UserRole;

@Repository
public class CustomerRepository {
	
	@Autowired
	EntityManager entityManager;
	ArtistRepository aRepository;
	
	@Transactional
	public Customer createCustomer(String aIdCode, String aEmailAddress, String aDisplayname, String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription) {
		User usr = new User(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword, aProfilePicLink, aProfileDescription);
		Customer customer = new Customer(aIdCode, usr);
		entityManager.persist(usr);
		entityManager.persist(customer);
		return customer;
	}
	
	@Transactional
	public Customer getCustomer(String aIdCode) {
		return entityManager.find(Customer.class, aIdCode);
	}
	
	@Transactional
	public void updateCustomer(Customer customer) {
		entityManager.merge(customer.getUser());
		entityManager.merge(customer);
	}
	
	@Transactional
	public boolean deleteCustomer(Customer customer) {
		Customer entity = entityManager.find(Customer.class, customer.getIdCode());
		User usr = entityManager.find(User.class, customer.getIdCode());
		if (entityManager.contains(entity)) {
			entityManager.remove(entityManager.merge(entity));
			entityManager.remove(entityManager.merge(usr));
		} else {
			entityManager.remove(entity);
			entityManager.remove(usr);
		}
		if (customer.getArtist()!=null) {
			aRepository.deleteArtist(customer.getArtist());
		}
		return (!entityManager.contains(entity) && !entityManager.contains(customer.getArtist()) && !entityManager.contains(usr));
		
	}

}
