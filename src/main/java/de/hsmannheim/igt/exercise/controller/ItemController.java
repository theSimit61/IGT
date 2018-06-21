package de.hsmannheim.igt.exercise.controller;

import de.hsmannheim.igt.exercise.config.DatabaseConfig;
import de.hsmannheim.igt.exercise.models.Item;
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
@RequestMapping("/item")
public class ItemController {

    TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            DatabaseConfig.CURRENT_DATABASE);

    private static Logger logger = Logger.getRootLogger();

    public Item createItem(Item i) {
        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            em.persist(i);
            logger.info("item is persisted in DB.");

            em.flush();
            em.close();
            tm.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public Collection<Item> getAllItems() {
        Collection<Item> items = null;
        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            Query query = em.createQuery("SELECT e FROM Item e");
            items = (Collection<Item>) query.getResultList();
            logger.info("Found Items: " + items);

            em.flush();
            em.close();
            tm.commit();

        }  catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public Item getItemByName(String name) {
        Item item = null;
        Collection<Item> items = getAllItems();
        for (Item d : items) {
            if (d.getI_NAME().equals(name)) {
                item = d;
            }
        }
        if (item == null) {
            throw new IllegalArgumentException("Item " + name + " not found!");
        }
        return item;
    }

    public Item getItem(String ItemID) {
        Item item = null;
        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            item = em.find(Item.class, ItemID);
            logger.info("Found Item: " + item.toString());

            em.flush();
            em.close();
            tm.commit();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;

    }

    public void updateItem(String ItemID, Item newItem) {

        try {
            tm.begin();
            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            Item item = em.find(Item.class, ItemID);
            logger.info("Found Item: " + item.toString());
            logger.info("Updating Item...");
            item = newItem;
            em.merge(item);

            em.flush();
            em.close();
            tm.commit();

            logger.info("Update successfully persisted.");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteItem(String ItemID) {

        try {
            tm.begin();
            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            Item item = em.find(Item.class, ItemID);
            if (item == null) {
                logger.info("Item not found! Nothing will be deleted.");
                return;
            }
            logger.info("Found Item: " + item.toString());
            logger.info("Deleting Item...");

            em.remove(item);

            em.flush();
            em.close();
            tm.commit();

            logger.info("Item successfully removed.");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
