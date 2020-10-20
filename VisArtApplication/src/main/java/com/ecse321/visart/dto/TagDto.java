package com.ecse321.visart.dto;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Tag.TagType;

public class TagDto {
	private String keyword;
	private String tagIdCode;
	private TagType tagType;
	private ArtListingDto tagListing;
	
	//constructor
	public TagDto(String keyword, String tagIdCode, TagType tagType, ArtListing tagListing) {
		super();
		this.keyword = keyword;
		this.tagIdCode = tagIdCode;
		this.tagType = tagType;
		this.tagListing = tagListing;
		
		
	}
	
	
	
	
	
}
