package com.ecse321.visart.dto;

import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.ArtPiece.PieceLocation;

public class ArtOrderDto {

	private boolean isDelivered;
	private PieceLocation targetLocation;
	private String targetAddress;
	private String deliveryTracker;
	private ArtPiece artPiece;

	public ArtOrderDto(boolean isDelivered, PieceLocation targetLocation, String targetAddress, String deliveryTracker,
			ArtPiece artPiece, String idCode) {
		super();
		this.isDelivered = isDelivered;
		this.targetLocation = targetLocation;
		this.targetAddress = targetAddress;
		this.deliveryTracker = deliveryTracker;
		this.artPiece = artPiece;

	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	public PieceLocation getTargetLocation() {
		return targetLocation;
	}

	public void setTargetLocation(PieceLocation targetLocation) {
		this.targetLocation = targetLocation;
	}

	public String getTargetAddress() {
		return targetAddress;
	}

	public void setTargetAddress(String targetAddress) {
		this.targetAddress = targetAddress;
	}

	public String getDeliveryTracker() {
		return deliveryTracker;
	}

	public void setDeliveryTracker(String deliveryTracker) {
		this.deliveryTracker = deliveryTracker;
	}

	public ArtPiece getArtPiece() {
		return artPiece;
	}

	public void setArtPiece(ArtPiece artPiece) {
		this.artPiece = artPiece;
	}

}
