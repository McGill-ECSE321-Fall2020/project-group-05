package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.model.Tag.TagType;




@Repository
public class TagRepository {
	
	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public Tag createTag(TagType aType, String aKeyword, String aIdCode, ArtListing aListing) {
		Tag tic = new Tag(aType, aKeyword, aIdCode, aListing);
		entityManager.persist(tic);
		return tic;
	}
	
	
	@Transactional
	public Tag getTag(String aIdCode) {
		return entityManager.find(Tag.class, aIdCode);
	}
	
}