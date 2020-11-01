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
import com.ecse321.visart.dto.TicketDto;
import com.ecse321.visart.service.TicketService;

@CrossOrigin(origins = "*")
@RestController
public class TicketRestController {

  @Autowired
  private TicketService ticService;

  @GetMapping(value = { "/tickets", "/tickets/" })
  public List<TicketDto> getAllTickets() {
    return ticService.getAllTickets().stream().map(u -> new TicketDto(u))
        .collect(Collectors.toList());
  }

  @PostMapping(value = { "/tickets/{aIdCode}", "/tickets/{aIdCode}/" })
  public TicketDto createTicket(@PathVariable("aIdCode") String aIdCode) {
    return new TicketDto(ticService.createTicket(false, 0.00, aIdCode, null, null, null));
  }
  
  @PostMapping(value = { "/tag_update/{aIdCode}", "/tag_update/{aIdCode}/" })
  public TicketDto updateTag(@PathVariable("aIdCode") String aIdCode) {
	    return new TicketDto(ticService.updateTicket(false, 0.00, aIdCode, null, null, null));
  }
  
  @PostMapping(value = {"/delete_tag/{id}","delete_tag/{id}/"})
  public Boolean deleteTicket(@PathVariable("id") String idCode) {
    return ticService.deleteTicket(idCode);
  }

}
