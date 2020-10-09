/** 

 */

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
 * CRUD Repository operations for a Ticket.
 * 
 * @author Nikola Milekic
 * @author Daniel Bucci
 *
 */
@Repository
public class TicketRepository {

  @Autowired
  EntityManager entityManager;

  /**
   * createTicket method creates a Ticket instance for an ArtOrder.
   * 
   * @param  aIsPaymentConfirmed whether payment is paid or not
   * @param  aPaymentAmount      price of the art piece
   * @param  aIdCode             database primary key for the Ticket
   * @param  aOrder              the ArtOrder to attach to
   * @param  aCustomer           the Customer instance buying the artwork
   * @param  aArtist             the Artist instance selling the artwork
   * @return                     a persisted Ticket instance from database
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
   * getTicket method retrieves a Ticket instance for an ArtOrder from the
   * database, given its primary key.
   * 
   * @param  aIdCode database primary key for the art order
   * @return         Ticket instance from database
   */
  @Transactional
  public Ticket getTicket(String aIdCode) {
    return entityManager.find(Ticket.class, aIdCode);
  }

  /**
   * Overloaded deleteTicket method deletes the given ticket instance from the
   * database.
   * 
   * @param  t the Ticket instance to remove from database
   * @return   true if successful delete
   */
  @Transactional
  public boolean deleteTicket(Ticket t) {
    return deleteTicket(t.getIdCode());
  }

  /**
   * deleteTicket method deletes the Ticket instance by its primary key from the
   * database.
   * 
   * @param  id the primary key of the Ticket to delete
   * @return    true if successful delete
   */
  @Transactional
  public boolean deleteTicket(String id) {
    Ticket entity = entityManager.find(Ticket.class, id);
    if (entityManager.contains(entity)) {
      entityManager.remove(entityManager.merge(entity));
    } else {
      entityManager.remove(entity);
    }
    return !entityManager.contains(entity);
  }

}
