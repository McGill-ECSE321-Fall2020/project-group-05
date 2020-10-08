package com.ecse321.visart.test;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EventRepository {

  @Autowired
  EntityManager entityManager;

  @Transactional
  public Event createEvent(String dbId, String name) {
    Event e = new Event();
    e.setDbId(dbId);
    e.setName(name);
    entityManager.persist(e);
    return e;
  }

  @Transactional
  public Event findEvent(String dbId) {
    return entityManager.find(Event.class, dbId);
  }
}
