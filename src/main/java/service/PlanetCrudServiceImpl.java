package service;

import entity.Planet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PlanetCrudServiceImpl implements PlanetCrudService {
    private static final Logger logger = LogManager.getLogger(PlanetCrudServiceImpl.class);
    private final SessionFactory sessionFactory;

    public PlanetCrudServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdate(Planet planet) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(planet);
            transaction.commit();
            logger.info("Planet saved/updated successfully: {}", planet);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error saving/updating planet: {}", e.getMessage(), e);
        }
    }

    @Override
    public void delete(Planet planet) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(planet);
            transaction.commit();
            logger.info("Planet deleted successfully: {}", planet);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error deleting planet: {}", e.getMessage(), e);
        }
    }

    @Override
    public Planet findById(String id) {
        try (Session session = sessionFactory.openSession()) {
            Planet planet = session.get(Planet.class, id);
            if (planet != null) {
                logger.info("Planet found by id {}: {}", id, planet);
            } else {
                logger.info("Planet not found by id: {}", id);
            }
            return planet;
        } catch (Exception e) {
            logger.error("Error finding planet by id {}: {}", id, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<Planet> getAllPlanets() {
        List<Planet> planets = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Planet> query = session.createQuery("FROM Planet", Planet.class);
            planets = query.list();
            logger.info("Retrieved all planets: {}", planets);
        } catch (Exception e) {
            logger.error("Error getting all planets: {}", e.getMessage(), e);
        }
        return planets;
    }

    @Override
    public void printAllPlanets() {
        List<Planet> planets = getAllPlanets();
        if (planets != null) {
            for (Planet planet : planets) {
                System.out.println(planet);
            }
        } else {
            logger.info("No planets found in the database.");
        }
    }
}