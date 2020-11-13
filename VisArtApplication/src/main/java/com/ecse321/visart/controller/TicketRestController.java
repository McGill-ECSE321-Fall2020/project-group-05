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

import com.ecse321.visart.dto.TicketDto;
import com.ecse321.visart.service.TicketService;

@CrossOrigin(origins = "*")
@RestController
public class TicketRestController {

  @Autowired
  private TicketService ticService;

  /**
   * 
   * @return
   */
  @GetMapping(value = { "/tickets/get_all", "/tickets/get_all/" })
  public List<TicketDto> getAllTickets() {
    return ticService.getAllTickets().stream().map(u -> new TicketDto(u))
        .collect(Collectors.toList());
  }

  /**
   * 
   * @param  aIdCode
   * @param  map
   * @return
   */
  @PostMapping(value = { "/tickets/create", "/tickets/create/" })
  public TicketDto createTicket(@RequestBody MultiValueMap<String, String> map) {
    String aIsPaymentConfirmed = map.getFirst("aIsPaymentConfirmed");
    String aPaymentAmount = map.getFirst("aPaymentAmount");
    String aOrderDto = map.getFirst("aOrder");
    String aCustomerDto = map.getFirst("aCustomer");
    String aArtistDto = map.getFirst("aArtist");
    boolean aIsPaymentConfirmedBool = Boolean.parseBoolean(aIsPaymentConfirmed);
    double aPaymentAmountDbl = Double.parseDouble(aPaymentAmount);
    return new TicketDto(ticService.createTicket(aIsPaymentConfirmedBool, aPaymentAmountDbl,
        aOrderDto, aCustomerDto, aArtistDto));
  }

  /**
   * 
   * @param  aIdCode
   * @param  map
   * @return
   */
  @PostMapping(value = { "/tickets/update/{aIdCode}", "/tickets/update/{aIdCode}/" })
  public TicketDto updateTicket(@PathVariable("aIdCode") String aIdCode,
      @RequestBody MultiValueMap<String, String> map) {
    String aIsPaymentConfirmed = map.getFirst("aIsPaymentConfirmed");
    String aPaymentAmount = map.getFirst("aPaymentAmount");
    String aOrderDto = map.getFirst("aOrder");
    String aCustomerDto = map.getFirst("aCustomer");
    String aArtistDto = map.getFirst("aArtist");
    
    Boolean aIsPaymentConfirmedBool = null;
    if (aIsPaymentConfirmed != null)
      aIsPaymentConfirmedBool = Boolean.parseBoolean(aIsPaymentConfirmed);
    
    Double aPaymentAmountDbl = null;
    if (aPaymentAmount != null)
      aPaymentAmountDbl = Double.parseDouble(aPaymentAmount);
    return new TicketDto(ticService.updateTicket(aIsPaymentConfirmedBool, aPaymentAmountDbl,
        aIdCode, aOrderDto, aCustomerDto, aArtistDto));
  }

  /**
   * 
   * @param  idCode
   * @return
   */
  @PostMapping(value = { "/tickets/delete/{id}", "/tickets/delete/{id}/" })
  public Boolean deleteTicket(@PathVariable("id") String idCode) {
    return ticService.deleteTicket(idCode);
  }

  /**
   * 
   * @param  idCode
   * @return
   */
  @GetMapping(value = { "/tickets/get/{id}", "/tickets/get/{id}/" })
  public TicketDto getTicket(@PathVariable("id") String idCode) {
    return new TicketDto(ticService.getTicket(idCode));
  }

}
