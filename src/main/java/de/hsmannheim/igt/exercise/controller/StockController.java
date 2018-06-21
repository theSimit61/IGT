package de.hsmannheim.igt.exercise.controller;

import de.hsmannheim.igt.exercise.config.DatabaseConfig;
import de.hsmannheim.igt.exercise.models.Stock;
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
@RequestMapping("/stock")
public class StockController {
    TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            DatabaseConfig.CURRENT_DATABASE);

    private static Logger logger = Logger.getRootLogger();

    public Stock createStock (Stock st) {

        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            em.persist(st);
            logger.info("stock is persisted in DB.");

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
        return st;
    }

    public Stock getStock(String StockID) {
        Stock stock = null;
        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            stock = em.find(Stock.class, StockID);
            logger.info("Found Stock: " + stock.toString());

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

        return stock;

    }

    public void updateStock(String StockID, Stock newStock) {

        try {
            tm.begin();
            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            Stock stock = em.find(Stock.class, StockID);
            logger.info("Found Stock: " + stock.toString());
            logger.info("Updating Stock...");
            stock = newStock;
            em.merge(stock);

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
    public boolean deleteStock(String StockID) {

        try {
            tm.begin();
            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            Stock dist = em.find(Stock.class, StockID);
            logger.info("Found Stock: " + dist.toString());
            logger.info("Deleting Stock...");

            em.remove(dist);

            em.flush();
            em.close();
            tm.commit();

            logger.info("Stock successfully removed.");
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    public Collection<Stock> getAllStocks() {
        Collection<Stock> stock = null;

        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            Query query = em.createQuery("SELECT e FROM Stock e");
            stock = (Collection<Stock>) query.getResultList();
            logger.info("Found Stocks: " + stock);

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

        return stock;
    }
}
