package Utilities;


import Model.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class TruckUtility {

    private EntityManagerFactory entityManagerFactory = null;

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public TruckUtility(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    public void create(Truck truck) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(truck));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    public Truck getTruck(Long id) {
        for (Truck truck : getAllTrucks()) {
            if (truck.getId() == id) {
                return truck;
            }
        }
        return null;
    }
    public Truck getTruck(String name) {
        for (Truck truck : getAllTrucks()) {
            if (truck.getName().equals(name)) {
                return truck;
            }
        }
        return null;
    }



    public List<Truck> getAllTrucks() {
        EntityManager entityManager = getEntityManager();

        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Truck.class));
            Query query = entityManager.createQuery(criteriaQuery);

            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void destroy(String name) throws Exception {
        EntityManager entityManager = null;

        try {
            for (Truck truck : getAllTrucks()) {
                if (truck.getName().equals(name)) {
                    entityManager = getEntityManager();
                    entityManager.getTransaction().begin();
                    entityManager.remove(entityManager.merge(truck));
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
