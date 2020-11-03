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

import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece.PieceLocation;

import com.ecse321.visart.dto.ArtOrderDto;
import com.ecse321.visart.service.ArtOrderService;
import com.ecse321.visart.service.TicketService;

@CrossOrigin(origins = "*")
@RestController

public class ArtOrderRestController {
  @Autowired
  private ArtOrderService service;

  @Autowired
  private TicketService servicet;

  /**
   * 
   * @return
   */
  @GetMapping(value = { "/artorder/get_all", "/artorder/get_all/" })
  public List<ArtOrderDto> getAllArtOrders() {
    return service.getAllArtOrders().stream().map(ao -> new ArtOrderDto(ao))
        .collect(Collectors.toList());
  }

  /**
   * 
   * @param  aIdCode
   * @return
   */
  @GetMapping(value = { "/artorder/get/{idCode}", "/artorder/get/{idCode}/" })
  public ArtOrderDto getArtOrder(@PathVariable("idCode") String aIdCode) {
    return new ArtOrderDto(service.getArtOrder(aIdCode));
  }

  /**
   * 
   * @param  aIsDelivered
   * @param  aTargetLocation
   * @param  aTargetAddress
   * @param  aDeliveryTracker
   * @param  artPieceDto
   * @return
   */
  @PostMapping(value = { "/artorder/create", "/artorder/create/" })
  public ArtOrderDto createArtOrder(@RequestBody MultiValueMap<String, String> map) {

    String aIsDelivered = map.getFirst("aIsDelivered");
    String aTargetLocation = map.getFirst("pieceLocation");
    String aTargetAddress = map.getFirst("aTargetAddress");
    String aDeliveryTracker = map.getFirst("aDeliveryTracker");
    String artPieceDto = map.getFirst("artPieceId");

    Boolean IsDelivered = Boolean.parseBoolean(aIsDelivered);
    PieceLocation TargetLocation = PieceLocation.valueOf(aTargetLocation);
    return new ArtOrderDto(service.createArtOrder(IsDelivered, TargetLocation, aTargetAddress,
        aDeliveryTracker, artPieceDto));
  }

  /**
   * 
   * @param  aIdCode
   * @param  map
   * @return
   */
  @PostMapping(value = { "/artorder/update/{aIdCode}", "/artorder/update/{aIdCode}/" })
  public ArtOrderDto updateArtOrder(@PathVariable("aIdCode") String aIdCode,
      @RequestBody MultiValueMap<String, String> map) {
    String aIsDelivered = map.getFirst("aIsDelivered");
    String aTargetLocation = map.getFirst("pieceLocation");
    String aTargetAddress = map.getFirst("aTargetAddress");
    String aDeliveryTracker = map.getFirst("aDeliveryTracker");
    String artPieceDto = map.getFirst("artPieceId");

    Boolean IsDelivered = Boolean.parseBoolean(aIsDelivered);
    PieceLocation TargetLocation = PieceLocation.fromString(aTargetLocation);
    return new ArtOrderDto(
        service.updateArtOrder(aIdCode, IsDelivered, TargetLocation, aTargetAddress,
            aDeliveryTracker, artPieceDto));

  }

  /**
   * 
   * @param  artOrderId
   * @param  ticketId
   * @return
   */
  @PostMapping(value = {
      "/artorder/add_ticket/{artOrderId}/{ticketId}",
      "/artorder/add_ticket/{artOrderId}/{ticketId}/" })
  public ArtOrderDto addTicket(@PathVariable(value = "artOrderId") String artOrderId,
      @PathVariable("ticketId") String ticketId) { // TODO: deal with request body?
    ArtOrder artOrder = service.getArtOrder(artOrderId);
    artOrder.setTicket(servicet.getTicket(ticketId));
    return new ArtOrderDto(artOrder);

  }

  /**
   * 
   * @param  idCode
   * @return
   */
  @PostMapping(value = { "/artorder/delete/{id}", "/artorder/delete/{id}/" })
  public Boolean deleteArtOrder(@PathVariable("id") String idCode) {
    return service.deleteArtOrder(idCode);
  }

}
