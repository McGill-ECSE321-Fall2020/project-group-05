package com.ecse321.visart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  private ArtListingService service1;
  

  @GetMapping(value = { "/ArtOrders", "/ArtOrders/" })
  public List<ArtOrderDto> getAllArtOrders () {
    return service.getAllArtOrders().stream().map(ao -> new ArtOrderDto(ao)).collect(Collectors.toList());
  }

  @PostMapping(value = { "/ArtOrders/{aIdCode}", "/ArtOrders/{aIdCode}/" })
  public ArtOrderDto createArtOrder(@PathVariable("aIdCode") String aIdCode, boolean aIsDelivered, PieceLocation aTargetLocation, String aTargetAddress, String aDeliveryTracker, ArtPieceDto APDTO) {
   
    ArtPiece ap = servicep.createArtPiece(APDTO.getBasicLocation(), APDTO.getAddressLocation(), APDTO.getIdCode(), service1.getArtListing(APDTO.getArtListing()));
    return new ArtOrderDto(service.createArtOrder(aIsDelivered, aTargetLocation, aTargetAddress, aDeliveryTracker,aIdCode, ap));
  }
}
