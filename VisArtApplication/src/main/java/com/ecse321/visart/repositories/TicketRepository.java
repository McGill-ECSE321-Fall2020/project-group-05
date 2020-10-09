package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Ticket;

/**
 * 
 * @author anwar
 *
 */
@Repository
public class TicketRepository {

  @Autowired
  EntityManager entityManager;

  /**
   * createTicket
   * 
   * This method creates a ticket instance for an art order
   * 
   * @param  aIsPaymentConfirmed boolean
   * @param  aPaymentAmount      price of the art listing
   * @param  aIdCode             database Id for the art order
   * @param  aOrder              Order number
   * @param  aCustomer           Name of the Customer buying the artwork
   * @param  aArtist             Name of the Artist selling the artwork
   * @return
   */
  @Transactional
  public Ticket createTicket(boolean aIsPaymentConfirmed, double aPaymentAmount, String aIdCode,
      ArtOrder aOrder, Customer aCustomer, Artist aArtist) {
    Ticket tic = new Ticket(aIsPaymentConfirmed, aPaymentAmount, aIdCode, aOrder, aCustomer,
        aArtist);
    entityManager.persist(tic);
    return tic;
  }

  /**
   * getTicket
   * 
   * This method retrieves a ticket instance for an art order from the database
   * 
   * @param  aIdCode database Id for the art order
   * @return         retrieves ticket information
   */
  @Transactional
  public Ticket getTicket(String aIdCode) {
    return entityManager.find(Ticket.class, aIdCode);
  }

  /**
   * deleteTicket
   * 
   * This method deletes a ticket instance of an art order from the database
   * 
   * @param  t
   * @return
   */
  @Transactional
  public boolean deleteTicket(Ticket t) {
    Ticket entity = entityManager.find(Ticket.class, t.getIdCode());
    if (entityManager.contains(entity)) {
      entityManager.remove(entityManager.merge(entity));
    } else {
      entityManager.remove(entity);
    }
    return !entityManager.contains(entity);
  }

}
