package Utilities;

import Model.LogisticsSystem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;

public class LogisticsSystemUtility {

    private EntityManagerFactory entityManagerFactory = null;

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public LogisticsSystemUtility(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    //region CRUD operations

    private void create(LogisticsSystem accountingSystem) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(accountingSystem));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void edit(LogisticsSystem accountingSystem) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            entityManager.merge(accountingSystem);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    //endregion

    //region Getters

    public LogisticsSystem getLogisticsSystem() {
        EntityManager entityManager = getEntityManager();

        try {
            LogisticsSystem logisticsSystem = entityManager.find(LogisticsSystem.class, (long) 1);

            if (logisticsSystem == null) {
                create(getDefaultLogisticsSystem());
                return getDefaultLogisticsSystem();
            } else {
                return logisticsSystem;
            }
        } finally {
            entityManager.close();
        }
    }


    private LogisticsSystem getDefaultLogisticsSystem() {
        return new LogisticsSystem(
                (long) 1,
                "VGTU",
                LocalDate.of(2022, 10, 20),
                "1.0.0"
        );
    }

    //endregion

}
