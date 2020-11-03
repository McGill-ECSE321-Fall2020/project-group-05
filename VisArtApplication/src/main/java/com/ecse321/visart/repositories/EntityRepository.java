package com.ecse321.visart.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created a repository for generally accessing any entity class. Methods of
 * this class are generic to any entity class and perform similar action/query
 * for any entity. Methods in this class should always have a parameter for the
 * Class of the entity to operate with.
 * 
 * @author Ryan Au
 *
 */
@Repository
public class EntityRepository {

  @Autowired
  EntityManager entityManager;

  /**
   * getUniqueKey creates a unique key to utilize for id codes of entities in the
   * database.
   * 
   * @return a unique UUID string key
   */
  public static String getUniqueKey() {
    return UUID.randomUUID().toString();
  }

  /**
   * getAllEntities method retrieves all entities of the given type entityType
   * (ex. User.class) from the database.
   * 
   * @param  <T>        The type of entity to return in a List
   * @param  entityType the type of entity to retrieve
   * @return            a List of all the entities from the database
   */
  public <T> List<T> getAllEntities(Class<T> entityType) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> query = cb.createQuery(entityType);
    Root<T> entity = query.from(entityType);
    query.select(entity);

    return entityManager.createQuery(query).getResultList();
  }

  /**
   * Performs a SELECT query on the given attribute of the given type of entity.
   * It uses the java name of the attribute, not the column name in the database.
   * 
   * @param  <T>        the type of the entity to query
   * @param  <E>        the type of value to return
   * @param  attribute  specifies which column of data to retrieve
   * @param  entityType the entity class which we will get the data of
   * @param  resultType the Java type of the retrieved attribute from database
   * @return            list of the data retrieved from the database as strings
   */
  public <E, T> List<T> getAllFromColumn(String attribute, Class<E> entityType,
      Class<T> resultType) {

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> query = (CriteriaQuery<T>) cb.createQuery(resultType);
    Root<E> entity = query.from(entityType);

    query.select(entity.get(attribute));

    return entityManager.createQuery(query).getResultList();
  }

  /**
   * Overloaded findEntityByAttribute method that takes in just one searchValue
   * for the search. Can output multiple entities or none.
   * 
   * @param  <T>         the class type of entity to return
   * @param  attribute   entity class field name (java name)
   * @param  entityType  class type of entity
   * @param  searchValue string value to search for
   * @return             list of entities that match one of the search values
   */
  @Transactional
  public <T> List<T> findEntityByAttribute(String attribute, Class<T> entityType,
      String searchValue) {
    List<String> valueList = new ArrayList<String>();
    valueList.add(searchValue);
    return findEntityByAttribute(attribute, entityType, valueList);
  }

  /**
   * Overloaded findEntityByAttribute method that takes in a String array of
   * values to search for. Can output multiple entities or none.
   * 
   * @param  <T>        the class type of entity to return
   * @param  attribute  entity class field name (java name)
   * @param  entityType class type of entity
   * @param  values     string array of values to search for
   * @return            list of entities that match one of the search values
   */
  @Transactional
  public <T> List<T> findEntityByAttribute(String attribute, Class<T> entityType, String[] values) {
    List<String> valueList = Arrays.asList(values);
    return findEntityByAttribute(attribute, entityType, valueList);
  }

  /**
   * findEntityByAttribute method searches the database for the given entity,
   * based on a given attribute (representing a column), and a collection of
   * Strings as the values to search for.
   * <br/>
   * For example, to look for any Users with emailAddress person@mcgill.ca:
   * attribute="emailAddress",
   * entityType=User.class,
   * valueCollection={"person@mcgill.ca"}
   * 
   * @param  <T>             the class type of entity to return
   * @param  attribute       entity class field name (java name)
   * @param  entityType      class type of entity
   * @param  valueCollection collection of search values
   * @return                 list of entities that match one of the search values
   */
  @Transactional
  public <T> List<T> findEntityByAttribute(String attribute, Class<T> entityType,
      Collection<String> valueCollection) {
    Set<String> valueSet = new HashSet<String>(valueCollection);

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> query = cb.createQuery(entityType);
    Root<T> entity = query.from(entityType);

    Path<String> path = entity.get(attribute);

    List<Predicate> predicates = new ArrayList<>();
    for (String value : valueSet) {
      predicates.add(cb.like(path, value));
    }
    query.select(entity)
        .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

    return entityManager.createQuery(query)
        .getResultList();
  }
}
