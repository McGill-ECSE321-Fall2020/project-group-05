package com.ecse321.visart.dto;

import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.ArtPiece.PieceLocation;

public class ArtPieceDto {

	private PieceLocation basicLocation;
	private String addressLocation;
	private String artListingId;
	private String artOrderId;
	private String idCode;

	public ArtPieceDto(PieceLocation basicLocation, String addressLocation, String artListingId, String idCode) {
		super();
		this.basicLocation = basicLocation;
		this.addressLocation = addressLocation;
		this.artListingId = artListingId;
		this.idCode = idCode;
	}

	public ArtPieceDto(ArtPiece artPiece) {
		basicLocation = artPiece.getBasicLocation();
		addressLocation = artPiece.getAddressLocation();
		artListingId = artPiece.getArtListing().getIdCode();
		artOrderId = artPiece.getArtOrder().getIdCode();
		idCode = artPiece.getIdCode();
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

	public String getArtListing() {
		return artListingId;
	}

	public void setArtListing(String artListingId) {
		this.artListingId = artListingId;
	}

	public String getArtOrder() {
		return artOrderId;
	}

	public void setArtOrder(String artOrder) {
		this.artOrderId = artOrder;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
}
