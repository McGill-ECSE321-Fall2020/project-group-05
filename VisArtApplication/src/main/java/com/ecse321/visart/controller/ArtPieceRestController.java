package com.ecse321.visart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecse321.visart.dto.ArtListingDto;
import com.ecse321.visart.dto.ArtPieceDto;
import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.service.ArtListingService;
import com.ecse321.visart.service.ArtPieceService;
import com.ecse321.visart.service.ArtistService;

public class ArtPieceRestController {
  @Autowired
  private ArtPieceService service;
  private ArtListingService servicel;
  private ArtistService servicea;

  @GetMapping(value = { "/ArtPieces", "/ArtPieces/" })
  public List<ArtPieceDto> getAllArtPiece() {
    return service.getAllArtPieces().stream().map(ap -> new ArtPieceDto(ap))
        .collect(Collectors.toList());
  }

  @PostMapping(value = { "/ArtPieces/{aIdCode}", "/ArtPieces/{aIdCode}/" })
  public ArtPieceDto createArtPiece(@PathVariable("aIdCode") String aIdCode,
      @RequestParam(value = "pieceLocation") String aBasicLocation,
      @RequestParam(value = "aAddressLocation") String aAddressLocation,
      @RequestParam(value = "ArtListingDto") String ArtListingDto) {

    ArtListing artListing = servicel.getArtListing(ArtListingDto);
    PieceLocation BasicLocation = PieceLocation.valueOf(aBasicLocation);
    return new ArtPieceDto(
        service.createArtPiece(BasicLocation, aAddressLocation, aIdCode, artListing));
  }
}
