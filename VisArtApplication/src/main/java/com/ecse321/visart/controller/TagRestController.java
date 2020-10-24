
/**
 * @author Riad El Mahmoudy
 */

package com.ecse321.visart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecse321.visart.dto.TagDto;
import com.ecse321.visart.dto.UserDto;
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.service.TagService;

@CrossOrigin(origins = "*")
@RestController
public class TagRestController {

  @Autowired
  private TagService tagService;

  @GetMapping(value = { "/tags", "/tags/" })
  public List<TagDto> getAllTags() {
    return tagService.getAllTags().stream().map(u -> new TagDto(u)).collect(Collectors.toList());
  }

  @PostMapping(value = { "/tags/{aIdCode}", "/tags/{aIdCode}/" })
  public TagDto createTag(@PathVariable("aIdCode") String aIdCode) {
    return new TagDto(tagService.createTag(null, "", aIdCode, null));
  }

}