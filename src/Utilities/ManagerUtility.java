package Utilities;
import Model.Manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class ManagerUtility {

    private EntityManagerFactory entityManagerFactory = null;

    private EntityManager getEntityManager() {

        return entityManagerFactory.createEntityManager();
    }

    public ManagerUtility(EntityManagerFactory entityManagerFactory) {

        this.entityManagerFactory = entityManagerFactory;
    }

    public Manager getManager(String email) {
        for (Manager manager : getAllManagers()) {
            if (manager.getEmail().equals(email)) {
                return manager;
            }
        }
        return null;
    }
    public void edit(Manager manager) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            entityManager.merge(manager);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    public Manager getManager(Long id) {
        for (Manager manager : getAllManagers()) {
            if (manager.getId() == id) {
                return manager;
            }
        }
        return null;
    }

    public void create(Manager manager) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(manager));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }



    public List<Manager> getAllManagers() {
        EntityManager entityManager = getEntityManager();

        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Manager.class));
            Query query = entityManager.createQuery(criteriaQuery);

            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
}
