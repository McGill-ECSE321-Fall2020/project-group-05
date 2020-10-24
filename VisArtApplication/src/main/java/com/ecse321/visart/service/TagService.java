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
import com.ecse321.visart.model.Ticket;
import com.ecse321.visart.model.Tag.TagType;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.TagRepository;

@Service
public class TagService {

  @Autowired
  TagRepository tagRepo;

  @Autowired
  EntityRepository entityRepo;

  @Transactional
  public Tag createTag(TagType aType, String aKeyword, String aIdCode, ArtListing aListing) {
    if (aIdCode == null || aIdCode == "") {
      throw new IllegalArgumentException("Tag id code cannot be empty!");
    }
    return tagRepo.createTag(aType, aKeyword, aIdCode, aListing);
  }

  @Transactional
  public Tag getTag(String aIdCode) {
    return tagRepo.getTag(aIdCode);
  }

  @Transactional
  public List<Tag> getAllTags() {
    return entityRepo.getAllEntities(Tag.class);
  }
}
