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
import org.springframework.web.bind.annotation.RequestParam;
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

  @PostMapping(value = { "/create_ticket/{aIdCode}", "/create_ticket/{aIdCode}/" })
  public TicketDto createTicket(@PathVariable("aIdCode") String aIdCode,
		  @RequestParam(value = "aIsPaymentConfirmed") String aIsPaymentConfirmed,
	      @RequestParam(value = "aPaymentAmount") String aPaymentAmount,
	      @RequestParam(value = "aOrder") String aOrderDto,
	      @RequestParam(value = "aCustomer") String aCustomerDto,
	      @RequestParam(value = "aArtist") String aArtistDto){
	boolean aIsPaymentConfirmedBool = Boolean.parseBoolean(aIsPaymentConfirmed);
	double aPaymentAmountDbl = Double.parseDouble(aPaymentAmount);
    return new TicketDto(ticService.createTicket(aIsPaymentConfirmedBool, aPaymentAmountDbl, 
    		 aOrderDto, aCustomerDto, aArtistDto));
  }
  
  @PostMapping(value = { "/ticket_tag_update/{aIdCode}", "/ticket_tag_update/{aIdCode}/" })
  public TicketDto updateTicketTag(@PathVariable("aIdCode") String aIdCode,	  
		  @RequestParam(value = "aIsPaymentConfirmed") String aIsPaymentConfirmed,
	      @RequestParam(value = "aPaymentAmount") String aPaymentAmount,
	      @RequestParam(value = "aOrder") String aOrderDto,
	      @RequestParam(value = "aCustomer") String aCustomerDto,
	      @RequestParam(value = "aArtist") String aArtistDto){
	boolean aIsPaymentConfirmedBool = Boolean.parseBoolean(aIsPaymentConfirmed);
	double aPaymentAmountDbl = Double.parseDouble(aPaymentAmount);
    return new TicketDto(ticService.updateTicket(aIsPaymentConfirmedBool, aPaymentAmountDbl, 
    		 aIdCode, aOrderDto, aCustomerDto, aArtistDto));
  }
  
  
  @PostMapping(value = {"/delete_ticket_tag/{id}","delete_ticket_tag/{id}/"})
  public Boolean deleteTicket(@PathVariable("id") String idCode) {
    return ticService.deleteTicket(idCode);
  }

}
