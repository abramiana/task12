package service;

import entity.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Реалізація CRUD-операцій для сутності клієнта (Client).
 */
public class ClientCrudServiceImpl implements ClientCrudService {
    private static final Logger logger = LogManager.getLogger(ClientCrudServiceImpl.class);
    private final SessionFactory sessionFactory;

    public ClientCrudServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdate(Client client) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(client);
            transaction.commit();
            logger.info("Client saved/updated successfully: {}", client);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error saving/updating client: {}", e.getMessage(), e);
        }
    }

    @Override
    public void update(Client client) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Отримуємо клієнта з бази даних за його ідентифікатором
            Client existingClient = session.get(Client.class, client.getId());
            if (existingClient != null) {
                // Оновлюємо поля існуючого клієнта з новими значеннями
                existingClient.setName(client.getName());

                // Зберігаємо оновленого клієнта у базі даних
                session.saveOrUpdate(existingClient);
                transaction.commit();
                logger.info("Client updated successfully: {}", existingClient);
            } else {
                logger.error("Client not found with ID: {}", client.getId());
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error updating client: {}", e.getMessage(), e);
        }
    }


    @Override
    public void delete(Client client) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(client);
            transaction.commit();
            logger.info("Client deleted successfully: {}", client);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error deleting client: {}", e.getMessage(), e);
        }
    }

    @Override
    public Client findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Client client = session.get(Client.class, id);
            if (client != null) {
                logger.info("Client found by id {}: {}", id, client);
            } else {
                logger.info("Client not found by id: {}", id);
            }
            return client;
        } catch (Exception e) {
            logger.error("Error finding client by id {}: {}", id, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Client> query = session.createQuery("SELECT c FROM Client c", Client.class);
            clients = query.getResultList();
            logger.info("Retrieved all clients: {}", clients);
        } catch (Exception e) {
            logger.error("Error getting all clients: {}", e.getMessage(), e);
        }
        return clients;
    }


    @Override
    public void printAllClients() {
        List<Client> clients = getAllClients();
        if (clients != null) {
            for (Client client : clients) {
                System.out.println(client);
            }
        } else {
            logger.info("No clients found in the database.");
        }
    }

    @Override
    public void addClient(String name) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Client client = new Client();
            client.setName(name);
            session.save(client);
            transaction.commit();
            logger.info("Client added successfully: {}", client);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error adding client: {}", e.getMessage(), e);
        }
    }
}