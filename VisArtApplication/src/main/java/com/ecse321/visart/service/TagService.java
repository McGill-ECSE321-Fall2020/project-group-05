/**
 * @author Riad El Mahmoudy
 */
package com.ecse321.visart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.model.Tag.TagType;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.TagRepository;

import javassist.compiler.ast.Keyword;

@Service
public class TagService {

  @Autowired
  TagRepository tagRepo;

  @Autowired
  EntityRepository entityRepo;

  @Transactional
  public Tag createTag(TagType aType, String aKeyword, ArtListing aListing) {
	  String aIdCode = EntityRepository.getUniqueKey();
    if (aIdCode == null || aIdCode.length()<1) {
      throw new IllegalArgumentException("Tag id code cannot be empty!");
    }
    
    else if (aType == null) {
        throw new IllegalArgumentException("Tag type cannot be empty!");
    }
    
    else if (aKeyword == null || aKeyword.length()<1) {
          throw new IllegalArgumentException("Tag keyword cannot be empty!");
    }
    
    else if (aListing == null) {
          throw new IllegalArgumentException("Tag listing cannot be empty!");
    }
    
    else if (aKeyword.length()>512) {
    	  throw new IllegalArgumentException("Tag keyword is too long!");
    }
    
    return tagRepo.createTag(aType, aKeyword, aIdCode, aListing);
  }

  @Transactional
  public Tag getTag(String aIdCode) {
    return tagRepo.getTag(aIdCode);
  }
  
  @Transactional
  public List<Tag> getTagByType(TagType type) {
    return entityRepo.findEntityByAttribute("type",Tag.class, ""+type.ordinal());
  }
  
  @Transactional
  public List<Tag> getTagByKeyword(String keyword) {
    return entityRepo.findEntityByAttribute("keyword",Tag.class,""+keyword);
  }

  @Transactional
  public List<Tag> getAllTags() {
    return entityRepo.getAllEntities(Tag.class);
  }
  
  @Transactional
  public Tag updateTag(TagType aType, String aKeyword, ArtListing aListing) {
	  String aIdCode = EntityRepository.getUniqueKey();
	if (aIdCode == null || aIdCode.length()<1) {
	      throw new IllegalArgumentException("Tag id code cannot be empty!");
	    }
	    
	else if (aType == null) {
	        throw new IllegalArgumentException("Tag type cannot be empty!");
	    }
	    
	else if (aKeyword == null || aKeyword.length()<1) {
	          throw new IllegalArgumentException("Tag keyword cannot be empty!");
	    }
	    
	else if (aListing == null) {
	          throw new IllegalArgumentException("Tag listing cannot be empty!");
	    }
	    
	else if (aKeyword.length()>512) {
	    	  throw new IllegalArgumentException("Tag keyword is too long!");
	    }
	    
	    
	Tag tag = tagRepo.getTag(aIdCode);
	tag.setKeyword(aKeyword);
	tag.setType(aType);
	tag.setListing(aListing);
	
	tagRepo.updateTag(tag);
	return tagRepo.getTag(aIdCode);
	  }
  
  /*@Transactional
  public boolean deleteTag(Tag tag) {

    return tagRepo.deleteTag(tag.getIdCode());
  }*/
  
  @Transactional
  public Boolean deleteTag(String aIdCode) {
    if(aIdCode != null && !aIdCode.contentEquals("")) {
      return tagRepo.deleteTag(aIdCode);
    } else {
      return false;
    }
  }
}
