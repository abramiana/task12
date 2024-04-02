package service;

import entity.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Реалізація сервісу для взаємодії з об'єктами квитків (Ticket) у базі даних.
 */

public class TicketCrudServiceImpl implements TicketCrudService {
    private static final Logger logger = LogManager.getLogger(TicketCrudServiceImpl.class);
    private final SessionFactory sessionFactory;

    public TicketCrudServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdate(Ticket ticket) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(ticket);
            transaction.commit();
            logger.info("Ticket saved/updated successfully: {}", ticket);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error saving/updating ticket: {}", e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Ticket ticket) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(ticket);
            transaction.commit();
            logger.info("Ticket deleted successfully: {}", ticket);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error deleting ticket: {}", e.getMessage(), e);
        }
    }

    @Override
    public Ticket findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Ticket ticket = session.get(Ticket.class, id);
            if (ticket != null) {
                logger.info("Ticket found by id {}: {}", id, ticket);
            } else {
                logger.info("Ticket not found by id: {}", id);
            }
            return ticket;
        } catch (Exception e) {
            logger.error("Error finding ticket by id {}: {}", id, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Ticket> query = session.createQuery("FROM Ticket", Ticket.class);
            tickets = query.list();
            logger.info("Retrieved all tickets: {}", tickets);
        } catch (Exception e) {
            logger.error("Error getting all tickets: {}", e.getMessage(), e);
        }
        return tickets;
    }

    @Override
    public void printAllTickets() {
        List<Ticket> tickets = getAllTickets();
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                System.out.println(ticket);
            }
        } else {
            logger.info("No tickets found in the database.");
        }
    }

    @Override
    public boolean validateTicket(Ticket ticket) {
        if (ticket == null) {
            logger.error("Ticket is null.");
            return false;
        }
        if (ticket.getClient() == null || ticket.getFromPlanet() == null || ticket.getToPlanet() == null) {
            logger.error("Ticket's client or starting/ending planet is null.");
            return false;
        }
        return true;
    }

    @Override
    public void update(Ticket ticket) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Отримуємо квиток з бази даних за його ідентифікатором
            Ticket existingTicket = session.get(Ticket.class, ticket.getId());
            if (existingTicket != null) {
                // Оновлюємо поля існуючого квитка з новими значеннями
                existingTicket.setClient(ticket.getClient());
                existingTicket.setFromPlanet(ticket.getFromPlanet());
                existingTicket.setToPlanet(ticket.getToPlanet());

                // Зберігаємо оновлений квиток у базі даних
                session.saveOrUpdate(existingTicket);
                transaction.commit();
                logger.info("Ticket updated successfully: {}", existingTicket);
            } else {
                logger.error("Ticket not found with ID: {}", ticket.getId());
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error updating ticket: {}", e.getMessage(), e);
        }
    }
}