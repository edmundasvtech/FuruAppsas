package Utilities;

import Model.Reply;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class MessageUtility {

    private EntityManagerFactory entityManagerFactory = null;

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public MessageUtility(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    public void create(Reply reply) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(reply));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void writeReply(Reply parentReply, Reply subReply) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            subReply.setParentReply(parentReply);
            parentReply.getReplies().add(subReply);

            entityManager.persist(subReply);
            entityManager.merge(parentReply);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public Reply getReply(long id) {
        for (Reply reply : getAllReplies()) {
            if (reply.getId() == id) {
                return reply;
            }
        }
        return null;
    }
    public Reply getReply(String name) {
        for (Reply reply : getAllReplies()) {
            if (reply.getName().equals(name)) {
                return reply;
            }
        }
        return null;
    }
    public List<Reply> getRootReplies() {
        List<Reply> replies = getAllReplies();
        List<Reply> rootReplies = new ArrayList<>();

        for (Reply reply : replies) {
            if (reply.getParentReply() == null) {
                rootReplies.add(reply);
            }
        }
        return rootReplies;
    }
    public void addSubReply(Reply parentReply, Reply subReply) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            subReply.setParentReply(parentReply);
            parentReply.getReplies().add(subReply);

            entityManager.persist(subReply);
            entityManager.merge(parentReply);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Reply> getAllReplies() {
        EntityManager entityManager = getEntityManager();

        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Reply.class));
            Query query = entityManager.createQuery(criteriaQuery);

            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
}
