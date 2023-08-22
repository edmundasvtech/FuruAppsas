package Utilities;


import Model.CustomerOrder;
import Model.Trip;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class TripUtility {


    private EntityManagerFactory entityManagerFactory = null;

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public TripUtility(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void create(Trip trip) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(trip));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    public Trip getTrip(Long id) {
        for (Trip trip : getAllTrips()) {
            if (trip.getId() == id) {
                return trip;
            }
        }
        return null;
    }
    public Trip getTrip(String name) {
        for (Trip trip : getAllTrips()) {
            if (trip.getName().equals(name)) {
                return trip;
            }
        }
        return null;
    }



    public List<Trip> getAllTrips() {
        EntityManager entityManager = getEntityManager();

        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Trip.class));
            Query query = entityManager.createQuery(criteriaQuery);

            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    public void edit(Trip trip) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            entityManager.merge(trip);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    public void destroy(String name) throws Exception {
        EntityManager entityManager = null;

        try {
            for (Trip trip : getAllTrips()) {
                if (trip.getName().equals(name)) {
                    entityManager = getEntityManager();
                    entityManager.getTransaction().begin();
                    entityManager.remove(entityManager.merge(trip));
                    entityManager.getTransaction().commit();
                }
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
