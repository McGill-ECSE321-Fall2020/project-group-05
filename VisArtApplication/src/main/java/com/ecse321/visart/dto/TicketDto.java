/**
 * @author Riad El Mahmoudy
 */
package com.ecse321.visart.dto;

public class TicketDto {
	
   //Private fields
	private boolean isPaymentConfirmed;
	private double paymentAmount;
	private String ticketIdCode;
	private ArtOrderDto ticketOrder;
	private CustomerDto ticketCustomer;
	private ArtistDto ticketArtist;
	
	//Constructor
	public TicketDto(boolean isPaymentConfirmed, double paymentAmount, String ticketIdCode, ArtOrderDto ticketOrder,
			CustomerDto ticketCustomer, ArtistDto ticketArtist) {
		super();
		this.isPaymentConfirmed = isPaymentConfirmed;
		this.paymentAmount = paymentAmount;
		this.ticketIdCode = ticketIdCode;
		this.ticketOrder = ticketOrder;
		this.ticketCustomer = ticketCustomer;
		this.ticketArtist = ticketArtist;
	}

	
	//Getters and Setters
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
		return ticketIdCode;
	}

	public void setTicketIdCode(String ticketIdCode) {
		this.ticketIdCode = ticketIdCode;
	}

	public ArtOrderDto getTicketOrder() {
		return ticketOrder;
	}

	public void setTicketOrder(ArtOrderDto ticketOrder) {
		this.ticketOrder = ticketOrder;
	}

	public CustomerDto getTicketCustomer() {
		return ticketCustomer;
	}

	public void setTicketCustomer(CustomerDto ticketCustomer) {
		this.ticketCustomer = ticketCustomer;
	}

	public ArtistDto getTicketArtist() {
		return ticketArtist;
	}

	public void setTicketArtist(ArtistDto ticketArtist) {
		this.ticketArtist = ticketArtist;
	}
	
	
}
