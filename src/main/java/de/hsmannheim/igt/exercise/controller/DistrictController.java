package de.hsmannheim.igt.exercise.controller;

import de.hsmannheim.igt.exercise.config.DatabaseConfig;
import de.hsmannheim.igt.exercise.models.District;
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
@RequestMapping("/district")
public class DistrictController {

    TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            DatabaseConfig.CURRENT_DATABASE);

    private static Logger logger = Logger.getRootLogger();


    public District createDistrict(District newDist) {

        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            em.persist(newDist);
            logger.info("district is persisted in DB.");

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
        return newDist;

    }

    public District getDistrict(String DistrictID) {

        District district = null;

        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            district = em.find(District.class, DistrictID);
            logger.info("Found District: " + district.toString());

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

        return district;
    }

    public void updateDistrict(String DistrictID, District d) {

        try {
            tm.begin();
            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            District dist = em.find(District.class, DistrictID);
            logger.info("Found District: " + dist.toString());
            logger.info("Updating District...");
            dist = d;

            em.merge(dist);

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

    public boolean deleteDistrict(String DistrictID) {

        try {
            tm.begin();
            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            District dist = em.find(District.class, DistrictID);
            logger.info("Found District: " + dist.toString());
            logger.info("Deleting District...");

            em.remove(dist);

            em.flush();
            em.close();
            tm.commit();

            logger.info("District successfully removed.");
            return true;

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
        return false;
    }

    public Collection<District> getAllDistricts() {
        Collection<District> districts = null;

        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();

            Query query = em.createQuery("SELECT e FROM District e");
            districts = (Collection<District>) query.getResultList();
            logger.info("Found Districts: " + districts);

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

        return districts;
    }

    public District getDistrictByName(String name) {
        District district = null;
        Collection<District> districts = getAllDistricts();
        for (District d : districts) {
            if (d.getD_NAME().equals(name)) {
                district = d;
            }
        }
        if (district == null) {
            throw new IllegalArgumentException("District " + name + " not found!");
        }
        return district;
    }

}
