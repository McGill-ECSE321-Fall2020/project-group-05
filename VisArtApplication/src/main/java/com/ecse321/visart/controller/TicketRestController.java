/**
 * @author Riad El Mahmoudy
 */
package com.ecse321.visart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecse321.visart.dto.TicketDto;
import com.ecse321.visart.dto.UserDto;
import com.ecse321.visart.model.Ticket;
import com.ecse321.visart.service.TicketService;

@CrossOrigin(origins = "*")
@RestController
public class TicketRestController {
 
	@Autowired
	private TicketService ticService;
	
	@GetMapping(value = { "/tickets", "/tickets/" })
	public List<TicketDto> getAllTickets(){
		return ticService.getAllTickets().stream().map(u -> new TicketDto(u)).collect(Collectors.toList());
	}
}
