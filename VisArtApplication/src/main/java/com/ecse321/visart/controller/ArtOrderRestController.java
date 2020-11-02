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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.ArtPiece.PieceLocation;

import com.ecse321.visart.dto.ArtOrderDto;
import com.ecse321.visart.dto.ArtPieceDto;
import com.ecse321.visart.dto.ManagerDto;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.service.ArtListingService;
import com.ecse321.visart.service.ArtOrderService;
import com.ecse321.visart.service.ArtPieceService;
import com.ecse321.visart.service.TicketService;

@CrossOrigin(origins = "*")
@RestController

public class ArtOrderRestController {
  @Autowired
  private ArtOrderService service;

  @Autowired
  private TicketService servicet;
    
    
  @GetMapping(value = { "/artOrders", "/artOrders/" })
  public List<ArtOrderDto> getAllArtOrders() {
    return service.getAllArtOrders().stream().map(ao -> new ArtOrderDto(ao))
        .collect(Collectors.toList());
  }

  @GetMapping(value = { "/get_artOrder", "/get_artorder/" })
  public ArtOrderDto getArtOrder(@RequestParam("idCode") String aIdCode) {
    return new ArtOrderDto(service.getArtOrder(aIdCode));
  }

  @PostMapping(value = { "/create_artOrder", "/create_artOrder/" })
  public ArtOrderDto createArtOrder(
      @RequestParam(value = "aIsDelivered") String aIsDelivered,
      @RequestParam(value = "pieceLocation") String aTargetLocation,
      @RequestParam(value = "aTargetAddress") String aTargetAddress,
      @RequestParam(value = "aDeliveryTracker") String aDeliveryTracker,
      @RequestParam(value = "artPieceDto") String artPieceDto) {

    boolean IsDelivered = Boolean.parseBoolean(aIsDelivered);
    PieceLocation TargetLocation = PieceLocation.valueOf(aTargetLocation);
    return new ArtOrderDto(service.createArtOrder(IsDelivered, TargetLocation, aTargetAddress,
        aDeliveryTracker, artPieceDto));
  }

  @PostMapping(value = { "/update_artOrder/{aIdCode}", "/update_artOrder/{aIdCode}/" })
  public ArtOrderDto updateArtOrder(@PathVariable("aIdCode") String aIdCode,
      @RequestParam(value = "aIsDelivered") String aIsDelivered,
      @RequestParam(value = "pieceLocation") String aTargetLocation,
      @RequestParam(value = "aTargetAddress") String aTargetAddress,
      @RequestParam(value = "aDeliveryTracker") String aDeliveryTracker,
      @RequestParam(value = "artPieceDto") String artPieceDto) {

    boolean IsDelivered = Boolean.parseBoolean(aIsDelivered);
    PieceLocation TargetLocation = PieceLocation.valueOf(aTargetLocation);
    return new ArtOrderDto(
        service.updateArtOrder(aIdCode, IsDelivered, TargetLocation, aTargetAddress,
            aDeliveryTracker, artPieceDto));

  }

  @PostMapping(value = { "/artOrder_addTicket/{ticketId}", "/artOrder_addTicket/{ticketId}" })
  public ArtOrderDto addTicket(@RequestParam(value = "artOrderId") String artOrderId,
      @PathVariable("ticketId") String ticketId) {
    ArtOrder artOrder = service.getArtOrder(artOrderId);
    artOrder.setTicket(servicet.getTicket(ticketId));
    return new ArtOrderDto(artOrder);

  
  }
  

  @PostMapping(value = { "/delete_artOrder/{id}", "/delete_artOrder/{id}/" })
  public Boolean deleteArtOrder(@PathVariable("id") String idCode) {
    return service.deleteArtOrder(idCode);
  }

}
