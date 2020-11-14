/**
 * @author Riad El Mahmoudy
 */
package com.ecse321.visart.dto;

import com.ecse321.visart.model.Ticket;

public class TicketDto {

  // Private fields
  private boolean isPaymentConfirmed;
  private double paymentAmount;
  private String idCode;
  private String ticketOrderId;
  private String ticketCustomerId;
  private String ticketArtistId;

  // Set after the creation
  private String ticketArtListingId;
  private String ticketArtPieceId;

  // Constructors

  public TicketDto(Ticket t) {
    this(t.getIsPaymentConfirmed(), t.getPaymentAmount(), t.getIdCode(), ticketGetOrder(t),
        ticketGetCustomer(t), ticketGetArtist(t));
    try {
      setTicketArtPieceId(t.getOrder().getArtPiece().getIdCode());
      setTicketArtListingId(t.getOrder().getArtPiece().getArtListing().getIdCode());
    } catch (NullPointerException e) {

    }

  }

  public static String ticketGetOrder(Ticket t) {
    if (t.getOrder() == null)
      return "";
    return t.getOrder().getIdCode();
  }

  public static String ticketGetCustomer(Ticket t) {
    if (t.getCustomer() == null)
      return "";
    return t.getCustomer().getIdCode();
  }

  public static String ticketGetArtist(Ticket t) {
    if (t.getArtist() == null)
      return "";
    return t.getArtist().getIdCode();
  }

  public TicketDto(boolean isPaymentConfirmed, double paymentAmount, String idCode,
      String ticketOrderId,
      String ticketCustomerId, String ticketArtistId) {
    super();
    this.isPaymentConfirmed = isPaymentConfirmed;
    this.paymentAmount = paymentAmount;
    this.idCode = idCode;
    this.ticketOrderId = ticketOrderId;
    this.ticketCustomerId = ticketCustomerId;
    this.ticketArtistId = ticketArtistId;
  }

  // Getters and Setters
  public boolean isPaymentConfirmed() {
    return isPaymentConfirmed;
  }

  public void setPaymentConfirmed(boolean isPaymentConfirmed) {
    this.isPaymentConfirmed = isPaymentConfirmed;
  }

  public double getPaymentAmount() {
    return paymentAmount;
  }

  public void setPaymentAmount(double paymentAmount) {
    this.paymentAmount = paymentAmount;
  }

  public String getTicketIdCode() {
    return idCode;
  }

  public void setTicketIdCode(String ticketIdCode) {
    this.idCode = ticketIdCode;
  }

  public String getTicketOrder() {
    return ticketOrderId;
  }

  public void setTicketOrder(String ticketOrderId) {
    this.ticketOrderId = ticketOrderId;
  }

  public String getTicketCustomer() {
    return ticketCustomerId;
  }

  public void setTicketCustomer(String ticketCustomerId) {
    this.ticketCustomerId = ticketCustomerId;
  }

  public String getTicketArtist() {
    return ticketArtistId;
  }

  public void setTicketArtist(String ticketArtist) {
    this.ticketArtistId = ticketArtist;
  }

  public String getIdCode() {
    return idCode;
  }

  public void setIdCode(String idCode) {
    this.idCode = idCode;
  }

  public String getTicketArtListingId() {
    return ticketArtListingId;
  }

  public void setTicketArtListingId(String ticketArtListingId) {
    this.ticketArtListingId = ticketArtListingId;
  }

  public String getTicketArtPieceId() {
    return ticketArtPieceId;
  }

  public void setTicketArtPieceId(String ticketArtPieceId) {
    this.ticketArtPieceId = ticketArtPieceId;
  }

}
