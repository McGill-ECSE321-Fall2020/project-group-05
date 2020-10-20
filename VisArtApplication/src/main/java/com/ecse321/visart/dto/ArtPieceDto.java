package com.ecse321.visart.dto;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtPiece.PieceLocation;

public class ArtPieceDto {

	private PieceLocation basicLocation;
	private String addressLocation;
	private ArtListingDto artListing;

	public ArtPieceDto(PieceLocation basicLocation, String addressLocation, ArtListingDto artListing) {
		super();
		this.basicLocation = basicLocation;
		this.addressLocation = addressLocation;
		this.artListing = artListing;
	}

	public PieceLocation getBasicLocation() {
		return basicLocation;
	}

	public void setBasicLocation(PieceLocation basicLocation) {
		this.basicLocation = basicLocation;
	}

	public String getAddressLocation() {
		return addressLocation;
	}

	public void setAddressLocation(String addressLocation) {
		this.addressLocation = addressLocation;
	}

	public ArtListingDto getArtListing() {
		return artListing;
	}

	public void setArtListing(ArtListingDto artListing) {
		this.artListing = artListing;
	}
}
