
/**
 * @author Riad El Mahmoudy
 */

package com.ecse321.visart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecse321.visart.dto.TagDto;
import com.ecse321.visart.model.Tag.TagType;
import com.ecse321.visart.service.TagService;

@CrossOrigin(origins = "*")
@RestController
public class TagRestController {

  @Autowired
  private TagService tagService;

  /**
   * 
   * @return
   */
  @GetMapping(value = { "/tags/get_all", "/tags/get_all/" })
  public List<TagDto> getAllTags() {
    return tagService.getAllTags().stream().map(u -> new TagDto(u)).collect(Collectors.toList());
  }

  /**
   * 
   * @param  aIdCode
   * @return
   */
  @GetMapping(value = { "/tags/get/{idCode}", "/tags/get/{idCode}/" })
  public TagDto getManager(@PathVariable("idCode") String aIdCode) {
    return new TagDto(tagService.getTag(aIdCode));
  }

  /**
   * 
   * @param  typeString
   * @return
   */
  @GetMapping(value = { "/tags/get_by_type/{type}", "/tags/get_by_type/{type}/" })
  public List<TagDto> getTagsByType(@PathVariable("type") String typeString) {
    TagType type = TagType.fromString(typeString);
    return tagService.getTagByType(type).stream().map(u -> new TagDto(u))
        .collect(Collectors.toList());
  }

  /**
   * 
   * @param  keyword
   * @return
   */
  @GetMapping(value = { "/tags/get_by_keyword/{keyword}", "/tags/get_by_keyword/{keyword}/" })
  public List<TagDto> getTagsByKeyword(@PathVariable("keyword") String keyword) {
    return tagService.getTagByKeyword(keyword).stream().map(u -> new TagDto(u))
        .collect(Collectors.toList());
  }

  /**
   * 
   * @param  aIdCode
   * @param  aListingDto
   * @param  keyword
   * @param  type
   * @return
   */
  @PostMapping(value = { "/tags/create", "/tags/create/" })
  public TagDto createTag(@RequestBody MultiValueMap<String, String> map) {
    String aListingDto = map.getFirst("aListing");
    String keyword = map.getFirst("aKeyword");
    String type = map.getFirst("aType");
    return new TagDto(tagService.createTag(type, keyword, aListingDto));
  }

  /**
   * 
   * @param  aIdCode
   * @param  map
   * @return
   */
  @PostMapping(value = { "/tags/update/{aIdCode}", "/tags/update/{aIdCode}/" })
  public TagDto updateTag(@PathVariable("aIdCode") String aIdCode,
      @RequestBody MultiValueMap<String, String> map) {
    String aListingDto = map.getFirst("aListing");
    String keyword = map.getFirst("aKeyword");
    String type = map.getFirst("aType");
    return new TagDto(tagService.updateTag(type, keyword, aIdCode, aListingDto));
  }

  @PostMapping(value = { "/tags/delete/{id}", "/tags/delete/{id}/" })
  public Boolean deleteTag(@PathVariable("id") String idCode) {
    return tagService.deleteTag(idCode);
  }

}