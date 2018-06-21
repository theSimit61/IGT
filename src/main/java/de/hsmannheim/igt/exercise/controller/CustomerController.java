package de.hsmannheim.igt.exercise.controller;


import de.hsmannheim.igt.exercise.config.DatabaseConfig;
import de.hsmannheim.igt.exercise.models.Customer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.*;
import javax.persistence.Query;
import java.util.Collection;



@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
   // TransactionManager tm2 = com.arjuna.ats.jta.TransactionManager.transactionManager();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            DatabaseConfig.CURRENT_DATABASE);
    
    private static Logger logger = Logger.getRootLogger();

    public Customer createCustomer(Customer newCustomer) {

        try {
            tm.begin();
           // tm2.begin();
            logger.info("TA begins");

            EntityManager em = emf.createEntityManager();

            em.persist(newCustomer);
            logger.info("Customer was persisted in DB.");

            em.flush();
            em.close();

            tm.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCustomer;
    }

    public Customer getCustomer(String customerID) {

        Customer cust = null;

        try {
            tm.begin();
            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();
            cust = em.find(Customer.class, customerID);
            logger.info("Found customer: " + cust.toString());
            em.flush();
            em.close();
            tm.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cust;

    }

    public void updateCustomer(String customerID, Customer newCustomer) {

        try {
            tm.begin();
            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            Customer cust = em.find(Customer.class, customerID);
            logger.info("Found customer: " + cust.toString());
            logger.info("Updating customer...");
            cust = newCustomer;

            em.merge(cust);

            em.flush();
            em.close();
            tm.commit();

            logger.info("Update successfully persisted.");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean deleteCustomer(String customerID) {

        try {
            tm.begin();
            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            Customer cust = em.find(Customer.class, customerID);
            logger.info("Found customer: " + cust.toString());
            logger.info("Deleting customer...");

            em.remove(cust);

            em.flush();
            em.close();
            tm.commit();

            logger.info("Customer successfully removed.");
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public Collection<Customer> getAllCustomer() {
        Collection<Customer> customer = null;

        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            Query query = em.createQuery("SELECT e FROM Customer e");
            customer = (Collection<Customer>) query.getResultList();
            logger.info("Found Districts: " + customer);

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

        return customer;
    }


}
