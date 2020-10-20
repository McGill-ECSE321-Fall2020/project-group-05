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
	
	
}
