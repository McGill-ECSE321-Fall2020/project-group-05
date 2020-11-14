/**
 * @author Riad El Mahmoudy
 */
package com.ecse321.visart.dto;

import com.ecse321.visart.model.Tag;
import com.ecse321.visart.model.Tag.TagType;

public class TagDto {

  // Private Fields
  private String keyword;
  private String idCode;
  private TagType tagType;
  private String tagListingId;

  // Constructors
  public TagDto(Tag t) {
    this(t.getKeyword(), t.getIdCode(), t.getType(), tagGetListing(t));
  }

  public static String tagGetListing(Tag t) {
    if (t.getListing() == null)
      return "";
    return t.getListing().getIdCode();
  }
  
  public TagDto(String keyword, String idCode, TagType tagType, String tagListingId) {
    super();
    this.keyword = keyword;
    this.idCode = idCode;
    this.tagType = tagType;
    this.tagListingId = tagListingId;

  }

  // Getters and Setters
  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getTagIdCode() {
    return idCode;
  }

  public void setTagIdCode(String tagIdCode) {
    this.idCode = tagIdCode;
  }

  public TagType getTagType() {
    return tagType;
  }

  public void setTagType(TagType tagType) {
    this.tagType = tagType;
  }

  public String getTagListing() {
    return tagListingId;
  }

  public void setTagListing(String tagListingId) {
    this.tagListingId = tagListingId;
  }

  public String getIdCode() {
    return idCode;
  }

  public void setIdCode(String idCode) {
    this.idCode = idCode;
  }

}
