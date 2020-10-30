package com.ecse321.visart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.repositories.ArtListingRepository;




@Service
public class ArtListingService {
  
  @Autowired
  ArtListingRepository ArtListingRepo;
  
  @Transactional
  public ArtListing createArtListing(PostVisibility aVisibility, String aDescription, String aTitle,
      String aIdCode, Artist aArtist) {
    
    
    return ArtListingRepo.createArtListing(aVisibility, aDescription, aTitle, aIdCode, aArtist);
  }

  @Transactional
  public ArtListing getArtListing(String aIdCode) {
    return ArtListingRepo.getArtListing(aIdCode);
  }
  
}
