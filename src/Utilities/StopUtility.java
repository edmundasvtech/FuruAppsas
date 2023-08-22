package Utilities;


import Model.Stop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class StopUtility {
    private EntityManagerFactory entityManagerFactory = null;

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public StopUtility(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void create(Stop stop) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(stop));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
