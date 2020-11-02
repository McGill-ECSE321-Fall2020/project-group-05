package com.ecse321.visart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecse321.visart.dto.ArtListingDto;
import com.ecse321.visart.dto.ArtOrderDto;
import com.ecse321.visart.dto.ArtPieceDto;
import com.ecse321.visart.dto.ManagerDto;
import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.service.ArtListingService;
import com.ecse321.visart.service.ArtOrderService;
import com.ecse321.visart.service.ArtPieceService;
import com.ecse321.visart.service.ArtistService;

public class ArtPieceRestController {
  @Autowired
  private ArtPieceService service;
  
  @Autowired
  private ArtOrderService serviceO;

  @GetMapping(value = { "/artPieces", "/artPieces/" })
  public List<ArtPieceDto> getAllArtPiece() {
    return service.getAllArtPieces().stream().map(ap -> new ArtPieceDto(ap))
        .collect(Collectors.toList());
  }

  @GetMapping(value = { "/get_artPiece", "/get_artPiece/" })
  public ArtPieceDto getArtOrder(@RequestParam("idCode") String aIdCode) {
    return new ArtPieceDto(service.getArtPiece(aIdCode));
  }

  @PostMapping(value = { "/create_artPieces/", "/create_artPieces/" })
  public ArtPieceDto createArtPiece(
      @RequestParam(value = "pieceLocation") String aBasicLocation,
      @RequestParam(value = "aAddressLocation") String aAddressLocation,
      @RequestParam(value = "aArtListingId") String aArtListingId) {

    PieceLocation basicLocation = PieceLocation.valueOf(aBasicLocation);
    return new ArtPieceDto(
        service.createArtPiece(basicLocation, aAddressLocation, aArtListingId));
  }

  @PostMapping(value = { "/update_artPieces/{aIdCode}", "/update_artPieces/{aIdCode}/" })
  public ArtPieceDto updateArtPiece(@PathVariable("aIdCode") String aIdCode,
      @RequestParam(value = "pieceLocation") String aBasicLocation,
      @RequestParam(value = "aAddressLocation") String aAddressLocation,
      @RequestParam(value = "aArtListingId") String aArtListingId) {

    PieceLocation basicLocation = PieceLocation.valueOf(aBasicLocation);
    return new ArtPieceDto(
        service.updateArtPiece(basicLocation, aAddressLocation, aIdCode, aArtListingId));
  }
  
  
  @PostMapping(value = { "/artPiece_addOrder/{artOrdertId}", "/artPiece_addOrder/{artOrderId}" })
  public ArtPieceDto addArtOrder(@PathVariable("artOrderId") String artOrderId,
      @RequestParam(value = "artPieceId") String artPieceId) {
    ArtPiece artPiece = service.getArtPiece(artPieceId);
    artPiece.setArtOrder((serviceO.getArtOrder(artOrderId)));
    return new ArtPieceDto(artPiece);

  
  }

  @PostMapping(value = { "/delete_artPiece/{id}", "/delete_artPiece/{id}/" })
  public Boolean deleteArtPiece(@PathVariable("id") String idCode) {
    return service.deleteArtPiece(idCode);
  }

}
