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

import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.ArtPiece.PieceLocation;

import com.ecse321.visart.dto.ArtOrderDto;
import com.ecse321.visart.dto.ArtPieceDto;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.service.ArtListingService;
import com.ecse321.visart.service.ArtOrderService;
import com.ecse321.visart.service.ArtPieceService;

@CrossOrigin(origins = "*")
@RestController

public class ArtOrderRestController {
  @Autowired
  private ArtOrderService service;

  @GetMapping(value = { "/artOrders", "/artOrders/" })
  public List<ArtOrderDto> getAllArtOrders() {
    return service.getAllArtOrders().stream().map(ao -> new ArtOrderDto(ao))
        .collect(Collectors.toList());
  }

  @GetMapping(value = { "/get_artOrder", "/get_artorder/" })
  public ArtOrderDto getArtOrder(@RequestParam("idCode") String aIdCode) {
    return new ArtOrderDto(service.getArtOrder(aIdCode));
  }

  @PostMapping(value = { "/create_artOrders", "/create_artOrders/" })
  public ArtOrderDto createArtOrder(
      @RequestParam(value = "aIsDelivered") String aIsDelivered,
      @RequestParam(value = "PieceLocation") String aTargetLocation,
      @RequestParam(value = "aTargetAddress") String aTargetAddress,
      @RequestParam(value = "aDeliveryTracker") String aDeliveryTracker,
      @RequestParam(value = "ArtPieceDto") String artPieceDto) {

    boolean IsDelivered = Boolean.parseBoolean(aIsDelivered);
    PieceLocation TargetLocation = PieceLocation.valueOf(aTargetLocation);
    return new ArtOrderDto(service.createArtOrder(IsDelivered, TargetLocation, aTargetAddress,
        aDeliveryTracker, artPieceDto));
  }

  @PostMapping(value = { "/update_artOrders/{aIdCode}", "/update_artOrders/{aIdCode}/" })
  public ArtOrderDto updateArtOrder(@PathVariable("aIdCode") String aIdCode,
      @RequestParam(value = "aIsDelivered") String aIsDelivered,
      @RequestParam(value = "PieceLocation") String aTargetLocation,
      @RequestParam(value = "aTargetAddress") String aTargetAddress,
      @RequestParam(value = "aDeliveryTracker") String aDeliveryTracker,
      @RequestParam(value = "ArtPieceDto") String artPieceDto) {

    boolean IsDelivered = Boolean.parseBoolean(aIsDelivered);
    PieceLocation TargetLocation = PieceLocation.valueOf(aTargetLocation);
    return new ArtOrderDto(
        service.updateArtOrder(aIdCode, IsDelivered, TargetLocation, aTargetAddress,
            aDeliveryTracker, artPieceDto));

  }

  @PostMapping(value = { "/delete_artOrder/{id}", "/delete_artOrder/{id}/" })
  public Boolean deleteArtOrder(@PathVariable("id") String idCode) {
    return service.deleteArtOrder(idCode);
  }

}
