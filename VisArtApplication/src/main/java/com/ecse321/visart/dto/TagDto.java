package com.ecse321.visart.dto;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.Tag.TagType;

public class TagDto {
	private String keyword;
	private String tagIdCode;
	private TagType tagType;
	private ArtListingDto tagListing;
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTagIdCode() {
		return tagIdCode;
	}

	public void setTagIdCode(String tagIdCode) {
		this.tagIdCode = tagIdCode;
	}

	public TagType getTagType() {
		return tagType;
	}

	public void setTagType(TagType tagType) {
		this.tagType = tagType;
	}

	public ArtListingDto getTagListing() {
		return tagListing;
	}

	public void setTagListing(ArtListingDto tagListing) {
		this.tagListing = tagListing;
	}

	//constructor
	public TagDto(String keyword, String tagIdCode, TagType tagType, ArtListing tagListing) {
		super();
		this.keyword = keyword;
		this.tagIdCode = tagIdCode;
		this.tagType = tagType;
		this.tagListing = tagListing;
		
		
	}
	
	
	
	
	
}
