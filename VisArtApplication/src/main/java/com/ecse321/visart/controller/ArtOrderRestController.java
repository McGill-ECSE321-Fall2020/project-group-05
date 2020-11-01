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
  private ArtPieceService servicep;
  private ArtListingService servicel;

  @GetMapping(value = { "/ArtOrders", "/ArtOrders/" })
  public List<ArtOrderDto> getAllArtOrders() {
    return service.getAllArtOrders().stream().map(ao -> new ArtOrderDto(ao))
        .collect(Collectors.toList());
  }

  @PostMapping(value = { "/ArtOrders/{aIdCode}", "/ArtOrders/{aIdCode}/" })
  public ArtOrderDto createArtOrder(@PathVariable("aIdCode") String aIdCode,
      @RequestParam(value = "aIsDelivered") String aIsDelivered,
      @RequestParam(value = "PieceLocation") String aTargetLocation,
      @RequestParam(value = "aTargetAddress") String aTargetAddress,
      @RequestParam(value = "aDeliveryTracker") String aDeliveryTracker,
      @RequestParam(value = "ArtPieceDto") String ArtPieceDto) {

    boolean IsDelivered = Boolean.parseBoolean(aIsDelivered);
    ArtPiece ap = servicep.getArtPiece(ArtPieceDto);
    PieceLocation TargetLocation = PieceLocation.valueOf(aTargetLocation);
    return new ArtOrderDto(service.createArtOrder(IsDelivered, TargetLocation, aTargetAddress,
        aDeliveryTracker, aIdCode, ap));
  }
}
