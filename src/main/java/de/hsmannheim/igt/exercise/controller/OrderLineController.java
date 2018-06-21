package de.hsmannheim.igt.exercise.controller;

import de.hsmannheim.igt.exercise.config.DatabaseConfig;
import de.hsmannheim.igt.exercise.models.OrderLine;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.*;
import java.util.Collection;

@Controller
@RequestMapping("/orderline")
public class OrderLineController {

    TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            DatabaseConfig.CURRENT_DATABASE);

    private static Logger logger = Logger.getRootLogger();

    public OrderLine createOrderLine(OrderLine orderLine) {

        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();
            em.persist(orderLine);
            logger.info("order is persisted in DB.");
            em.flush();
            em.close();
            tm.commit();

        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
        return orderLine;
    }

    public Collection<OrderLine> getAllOrderLines() {
        Collection<OrderLine> orderLines = null;

        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            Query query = em.createQuery("SELECT e FROM OrderLine e");
            orderLines = (Collection<OrderLine>) query.getResultList();
            logger.info("Found Orderlines: " + orderLines);

            em.flush();
            em.close();
            tm.commit();

        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
        return orderLines;
    }

    public OrderLine getOrderLine(String OrderLineID) {
        OrderLine order = null;

        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            order = em.find(OrderLine.class, OrderLineID);
            logger.info("Found OrderLine: " + order.toString());

            em.flush();
            em.close();
            tm.commit();

        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
        return order;
    }

    public void updateOrderLine(String OrderLineID, OrderLine newOrderLine) {

        try {
            tm.begin();
            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            OrderLine order = em.find(OrderLine.class, OrderLineID);
            logger.info("Found OrderLine: " + order.toString());
            logger.info("Updating OrderLine...");
            order = newOrderLine;
            em.merge(order);
            em.flush();
            em.close();
            tm.commit();
            logger.info("Update successfully persisted.");

        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderLine(String OrderLineID) {

        try {
            tm.begin();
            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            OrderLine dist = em.find(OrderLine.class, OrderLineID);
            logger.info("Found OrderLine: " + dist.toString());
            logger.info("Deleting OrderLine...");

            em.remove(dist);

            em.flush();
            em.close();
            tm.commit();

            logger.info("OrderLine successfully removed.");

        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
    }

}
