package com.ecse321.visart.dto;

import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.model.ArtOrder;

public class ArtOrderDto {

  private boolean isDelivered;
  private PieceLocation targetLocation;
  private String targetAddress;
  private String deliveryTracker;
  private String artPieceId;
  private String idCode;

  public ArtOrderDto(boolean isDelivered, PieceLocation targetLocation, String targetAddress,
      String deliveryTracker,
      String artPieceId, String idCode) {
    super();
    this.isDelivered = isDelivered;
    this.targetLocation = targetLocation;
    this.targetAddress = targetAddress;
    this.deliveryTracker = deliveryTracker;
    this.artPieceId = artPieceId;
    this.idCode = idCode;

  }

  public ArtOrderDto(ArtOrder order) {
    isDelivered = order.getIsDelivered();
    targetLocation = order.getTargetLocation();
    targetAddress = order.getTargetAddress();
    deliveryTracker = order.getDeliveryTracker();
    artPieceId = order.getArtPiece().getIdCode();
    idCode = order.getIdCode();
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

  public String getArtPiece() {
    return artPieceId;
  }

  public void setArtPiece(String artPieceId) {
    this.artPieceId = artPieceId;
  }

  public String getIdCode() {
    return idCode;
  }

  public void setIdCode(String idCode) {
    this.idCode = idCode;
  }

}
