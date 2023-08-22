package Utilities;

import Model.Manager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestHibernate {
    public static void main(String[] args) {



        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("LogisticsSystem");
        Manager manager = new Manager("3", "3", "3", "3", "3", "3","3");
        ManagerUtility managerUtility=new ManagerUtility(entityManagerFactory);
        managerUtility.create(manager);

/*
        SessionFactory factory = null;
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Manager manager = new Manager("1", "cust2", "cust2", "cust2", "cust2", "cust2","cust2","4");
            session.save(manager);
            Driver driver = new Driver("2", "2", "cust2", "cust2", "cust2", "cust2","cust2","4");
            session.save(driver);
            LocalDate date= LocalDate.of(2033,04,10);
            Reply reply = new Reply("x", "x", date);
            session.save(reply);
            long num =10000;
            LogisticsSystem logisticsSystem = new LogisticsSystem(num, "x",date,"a");
            session.save(logisticsSystem);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }*/
    }
}