package de.hsmannheim.igt.exercise.controller;

import de.hsmannheim.igt.exercise.config.DatabaseConfig;
import de.hsmannheim.igt.exercise.models.Stock;
import de.hsmannheim.igt.exercise.models.Warehouse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.*;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController {

    TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            DatabaseConfig.CURRENT_DATABASE);

    private static Logger logger = Logger.getRootLogger();

    public Warehouse createWarehouse(Warehouse w) {

        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            em.persist(w);
            logger.info("warehouse is persisted in DB.");
            em.flush();
            em.close();
            tm.commit();
        } catch (Exception e) {

        }
        return w;

    }

    public Warehouse getWarehouse(String WarehouseID) {
        Warehouse warehouse = null;
        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            warehouse = em.find(Warehouse.class, WarehouseID);
            logger.info("Found Warehouse: " + warehouse.toString());

            em.flush();
            em.close();
            tm.commit();

        } catch (Exception e) {

        }
        return warehouse;


    }

    public Warehouse updateWarehouse(String WarehouseID, Stock newStock) {
        Warehouse newWarehouse = getWarehouse(WarehouseID);
        Stock tempStock = new Stock();
        tempStock.setST_ITEM_AMOUNT(newStock.getST_ITEM_AMOUNT());
        tempStock.setST_ITEM_ID(newStock.getST_ITEM_ID());
        boolean added = false;
        for (int i = 0; i < newWarehouse.getW_ITEMS().size(); i++) {
            if (newWarehouse.getW_ITEMS().get(i).getST_ITEM_ID().equals(newStock.getST_ITEM_ID())) {
                newWarehouse.getW_ITEMS().get(i).setST_ITEM_AMOUNT(newStock.getST_ITEM_AMOUNT());
               newWarehouse= updateWarehouse(WarehouseID, newWarehouse);
                added = true;
            }
        }
        if (!added) {
            List<Stock> inventories = newWarehouse.getW_ITEMS();
            inventories.add(newStock);
            newWarehouse.setW_ITEMS(inventories);
           newWarehouse =  updateWarehouse(WarehouseID, newWarehouse);
        }
        return newWarehouse;
    }


    public Warehouse updateWarehouse(String WarehouseID, Warehouse newWarehouse) {
        try {
            tm.begin();
            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            Warehouse warehouse = em.find(Warehouse.class, WarehouseID);
            logger.info("Found Warehouse: " + warehouse.toString());
            logger.info("Updating Warehouse...");
            warehouse.getW_ITEMS().clear();
            warehouse = newWarehouse;

            em.merge(warehouse);

            em.flush();
            em.close();
            tm.commit();

            logger.info("Update successfully persisted.");

        } catch (Exception e) {

        }
        return newWarehouse;
    }

    public void deleteWarehouse(String WarehouseID) {

        try {
            tm.begin();
            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            Warehouse dist = em.find(Warehouse.class, WarehouseID);
            logger.info("Found Warehouse: " + dist.toString());
            logger.info("Deleting Warehouse...");

            em.remove(dist);

            em.flush();
            em.close();
            tm.commit();

            logger.info("Warehouse successfully removed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Collection<Warehouse> getAllWareHouses() {
        Collection<Warehouse> warehouses = null;

        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            Query query = em.createQuery("SELECT e FROM Warehouse e");
            warehouses = (Collection<Warehouse>) query.getResultList();
            logger.info("Found Warehouses: " + warehouses);


            em.flush();
            em.close();
            tm.commit();


        } catch (Exception e) {
            e.printStackTrace();
        }


        return warehouses;
    }
}
