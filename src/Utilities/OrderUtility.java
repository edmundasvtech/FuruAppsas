package Utilities;




import Model.CustomerOrder;
import Model.Driver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class OrderUtility {

    private EntityManagerFactory entityManagerFactory = null;

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public OrderUtility(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    public void create(CustomerOrder customerOrder) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(customerOrder));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    public CustomerOrder getOrder(Long id) {
        for (CustomerOrder customerOrder : getAllOrders()) {
            if (customerOrder.getId() == id) {
                return customerOrder;
            }
        }
        return null;
    }
    public CustomerOrder getOrder(String name) {
        for (CustomerOrder customerOrder : getAllOrders()) {
            if (customerOrder.getProductName().equals(name)) {
                return customerOrder;
            }
        }
        return null;
    }

    public List<CustomerOrder> getAllOrders() {
        EntityManager entityManager = getEntityManager();

        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(CustomerOrder.class));
            Query query = entityManager.createQuery(criteriaQuery);

            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void destroy(String name) throws Exception {
        EntityManager entityManager = null;

        try {
            for (CustomerOrder customerOrder : getAllOrders()) {
                if (customerOrder.getClient().equals(name)) {
                    entityManager = getEntityManager();
                    entityManager.getTransaction().begin();
                    entityManager.remove(entityManager.merge(customerOrder));
                    entityManager.getTransaction().commit();
                }
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void edit(CustomerOrder customerOrder) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            entityManager.merge(customerOrder);
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
