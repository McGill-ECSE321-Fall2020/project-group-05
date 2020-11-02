package com.ecse321.visart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  /**
   * 
   * @return
   */
  @GetMapping(value = { "/artpiece/get_all", "/artpiece/get_all/" })
  public List<ArtPieceDto> getAllArtPiece() {
    return service.getAllArtPieces().stream().map(ap -> new ArtPieceDto(ap))
        .collect(Collectors.toList());
  }

  /**
   * 
   * @param  aIdCode
   * @return
   */
  @GetMapping(value = { "/artpiece/get/{idCode}", "/artpiece/get/{idCode}/" })
  public ArtPieceDto getArtOrder(@PathVariable("idCode") String aIdCode) {
    return new ArtPieceDto(service.getArtPiece(aIdCode));
  }

  /**
   * 
   * @param  aBasicLocation
   * @param  aAddressLocation
   * @param  aArtListingId
   * @return
   */
  @PostMapping(value = { "/artpiece/create", "/artpiece/create/" })
  public ArtPieceDto createArtPiece(@RequestBody MultiValueMap<String, String> map) {
    String aBasicLocation = map.getFirst("pieceLocation");
    String aAddressLocation = map.getFirst("aAddressLocation");
    String aArtListingId = map.getFirst("aArtListingId");

    PieceLocation basicLocation = PieceLocation.valueOf(aBasicLocation);
    return new ArtPieceDto(
        service.createArtPiece(basicLocation, aAddressLocation, aArtListingId));
  }

  /**
   * 
   * @param  aIdCode
   * @param  aBasicLocation
   * @param  aAddressLocation
   * @param  aArtListingId
   * @return
   */
  @PostMapping(value = { "/artpiece/update/{aIdCode}", "/artpiece/update/{aIdCode}/" })
  public ArtPieceDto updateArtPiece(@PathVariable("aIdCode") String aIdCode,
      @RequestBody MultiValueMap<String, String> map) {
    String aBasicLocation = map.getFirst("pieceLocation");
    String aAddressLocation = map.getFirst("aAddressLocation");
    String aArtListingId = map.getFirst("aArtListingId");

    PieceLocation basicLocation = PieceLocation.valueOf(aBasicLocation);
    return new ArtPieceDto(
        service.updateArtPiece(basicLocation, aAddressLocation, aIdCode, aArtListingId));
  }

  /**
   * 
   * @param  artOrderId
   * @param  artPieceId
   * @return
   */
  @PostMapping(value = { "/artpiece/add_order/{aIdCode}", "/artpiece/add_order/{aIdCode}/" })
  public ArtPieceDto addArtOrder(@PathVariable(value = "aIdCode") String artPieceId,
      @RequestBody MultiValueMap<String, String> map) {
    String artOrderId = map.getFirst("artOrderId");
    ArtPiece artPiece = service.getArtPiece(artPieceId);
    artPiece.setArtOrder((serviceO.getArtOrder(artOrderId)));
    return new ArtPieceDto(artPiece);

  }

  @PostMapping(value = { "/delete_artPiece/{id}", "/delete_artPiece/{id}/" })
  public Boolean deleteArtPiece(@PathVariable("id") String idCode) {
    return service.deleteArtPiece(idCode);
  }

}
