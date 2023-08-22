package Utilities;
import Model.CustomerOrder;
import Model.Driver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class DriverUtility {

    private EntityManagerFactory entityManagerFactory = null;

    private EntityManager getEntityManager() {

        return entityManagerFactory.createEntityManager();
    }

    public DriverUtility(EntityManagerFactory entityManagerFactory) {

        this.entityManagerFactory = entityManagerFactory;
    }

    public Driver getDriver(Long id) {
        for (Driver driver : getAllDrivers()) {
            if (driver.getId()==id) {
                return driver;
            }
        }
        return null;
    }

    public Driver getDriver(String email) {
        for (Driver driver : getAllDrivers()) {
            if (driver.getEmail().equals(email)) {
                return driver;
            }
        }
        return null;
    }
    public void create(Driver driver) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(driver));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    public List<Driver> getAllDrivers() {
        EntityManager entityManager = getEntityManager();

        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Driver.class));
            Query query = entityManager.createQuery(criteriaQuery);

            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void edit(Driver driver) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            entityManager.merge(driver);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void destroy(String email) throws Exception {
        EntityManager entityManager = null;

        try {
            for (Driver driver : getAllDrivers()) {
                if (driver.getEmail().equals(email)) {
                    entityManager = getEntityManager();
                    entityManager.getTransaction().begin();
                    entityManager.remove(entityManager.merge(driver));
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
