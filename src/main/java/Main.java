import entity.Client;
import entity.Planet;
import entity.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import service.ClientCrudServiceImpl;
import service.PlanetCrudServiceImpl;
import service.TicketCrudServiceImpl;
import util.HibernateUtil;

import java.util.Scanner;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        ClientCrudServiceImpl clientService = new ClientCrudServiceImpl(sessionFactory);
        PlanetCrudServiceImpl planetService = new PlanetCrudServiceImpl(sessionFactory);
        TicketCrudServiceImpl ticketService = new TicketCrudServiceImpl(sessionFactory);

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            printMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addClient(clientService, scanner);
                    break;
                case 2:
                    clientService.printAllClients();
                    break;
                case 3:
                    updateClient(clientService, scanner);
                    break;
                case 4:
                    deleteClient(clientService, scanner);
                    break;
                case 5:
                    addPlanet(planetService, scanner);
                    break;
                case 6:
                    planetService.printAllPlanets();
                    break;
                case 7:
                    updatePlanet(planetService, scanner);
                    break;
                case 8:
                    deletePlanet(planetService, scanner);
                    break;
                case 9:
                    addTicket(clientService, planetService, ticketService, scanner);
                    break;
                case 10:
                    ticketService.printAllTickets();
                    break;
                case 11:
                    updateTicket(ticketService, clientService, planetService, scanner);
                    break;
                case 12:
                    deleteTicket(ticketService, scanner);
                    break;
                case 13:
                    System.out.println("Exiting...");
                    break;
                default:
                    logger.warn("Invalid choice entered: {}", choice);
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 13);

        HibernateUtil.shutdown();
    }

    /**
     * Друкує головне меню програми на консоль.
     * Це меню дозволяє користувачеві вибрати операції,
     * які він хоче виконати, такі як додавання, виведення,
     * оновлення або видалення клієнтів, планет або квитків.
     */
    private static void printMenu() {
        System.out.println("------Client-------");
        System.out.println("1. Add Client");
        System.out.println("2. Print All Clients");
        System.out.println("3. Update Client");
        System.out.println("4. Delete Client");
        System.out.println("------Planet-------");
        System.out.println("5. Add Planet");
        System.out.println("6. Print All Planets");
        System.out.println("7. Update Planet");
        System.out.println("8. Delete Planet");
        System.out.println("------Ticket-------");
        System.out.println("9. Add Ticket");
        System.out.println("10. Print All Tickets");
        System.out.println("11. Update Ticket");
        System.out.println("12. Delete Ticket");
        System.out.println("13. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Додає нового клієнта до бази даних.
     *
     * @param clientService об'єкт служби для взаємодії з клієнтами
     * @param scanner       об'єкт для зчитування введених користувачем даних з консолі
     */
    private static void addClient(ClientCrudServiceImpl clientService, Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter client name: ");
        String name = scanner.nextLine();
        clientService.addClient(name);
        logger.info("New client added: {}", name);
    }

    /**
     * Оновлює інформацію про клієнта в базі даних за його ідентифікатором.
     *
     * @param clientService об'єкт служби для взаємодії з клієнтами
     * @param scanner       об'єкт для зчитування введених користувачем даних з консолі
     */
    private static void updateClient(ClientCrudServiceImpl clientService, Scanner scanner) {
        System.out.print("Enter client ID to update: ");
        long clientId = scanner.nextLong();
        Client clientToUpdate = clientService.findById(clientId);
        if (clientToUpdate != null) {
            scanner.nextLine();
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();

            // Оновлюємо дані клієнта з новим ім'ям
            clientToUpdate.setName(newName);

            // Викликаємо метод збереження оновленого клієнта
            clientService.saveOrUpdate(clientToUpdate);
            System.out.println("Client with ID " + clientId + " updated successfully.");
            logger.info("Client with ID {} updated successfully.", clientId);
        } else {
            logger.warn("Client with ID {} not found.", clientId);
            System.out.println("Client with ID " + clientId + " not found.");
        }
    }

    /**
     * Видаляє клієнта з бази даних за його ідентифікатором.
     *
     * @param clientService об'єкт служби для взаємодії з клієнтами
     * @param scanner       об'єкт для зчитування введених користувачем даних з консолі
     */
    private static void deleteClient(ClientCrudServiceImpl clientService, Scanner scanner) {
        System.out.print("Enter client ID to delete: ");
        long id = scanner.nextLong();
        Client clientToDelete = clientService.findById(id);
        if (clientToDelete != null) {
            clientService.delete(clientToDelete);
            System.out.println("Client with ID " + id + " deleted successfully.");
            logger.info("Client with ID {} deleted successfully.", id);
        } else {
            logger.warn("Client with ID {} not found.", id);
            System.out.println("Client with ID " + id + " not found.");
        }
    }

    /**
     * Додає нову планету до бази даних.
     *
     * @param planetService об'єкт служби для взаємодії з планетами
     * @param scanner       об'єкт для зчитування введених користувачем даних з консолі
     */
    private static void addPlanet(PlanetCrudServiceImpl planetService, Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter planet name: ");
        String name = scanner.nextLine();
        System.out.print("Enter planet ID: ");
        String id = scanner.nextLine();
        Planet planet = new Planet();
        planet.setId(id);
        planet.setName(name);
        planetService.saveOrUpdate(planet);
        System.out.println("Planet added successfully.");
        logger.info("New planet added: {}", name);
    }

    /**
     * Оновлює інформацію про існуючу планету в базі даних.
     *
     * @param planetService об'єкт служби для взаємодії з планетами
     * @param scanner       об'єкт для зчитування введених користувачем даних з консолі
     */

    private static void updatePlanet(PlanetCrudServiceImpl planetService, Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter planet ID to update: ");
        String id = scanner.nextLine();
        Planet planetToUpdate = planetService.findById(id);
        if (planetToUpdate != null) {
            System.out.print("Enter new name for the planet: ");
            String newName = scanner.nextLine();
            planetToUpdate.setName(newName);

            // Викликаємо метод оновлення планети в сервісі
            planetService.update(planetToUpdate);
            System.out.println("Planet with ID " + id + " updated successfully.");
            logger.info("Planet with ID {} updated successfully.", id);
        } else {
            logger.warn("Planet with ID {} not found.", id);
            System.out.println("Planet with ID " + id + " not found.");
        }
    }

    /**
     * Видаляє планету з бази даних за заданим ідентифікатором.
     *
     * @param planetService об'єкт служби для взаємодії з планетами
     * @param scanner       об'єкт для зчитування введених користувачем даних з консолі
     */
    private static void deletePlanet(PlanetCrudServiceImpl planetService, Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter planet ID to delete: ");
        String id = scanner.nextLine();
        Planet planetToDelete = planetService.findById(id);
        if (planetToDelete != null) {
            planetService.delete(planetToDelete);
            System.out.println("Planet with ID " + id + " deleted successfully.");
            logger.info("Planet with ID {} deleted successfully.", id);
        } else {
            logger.warn("Planet with ID {} not found.", id);
            System.out.println("Planet with ID " + id + " not found.");
        }
    }

    /**
     * Додає новий квиток у систему з вказаними клієнтом та планетами відправлення та прибуття.
     *
     * @param clientService об'єкт служби для взаємодії з клієнтами
     * @param planetService об'єкт служби для взаємодії з планетами
     * @param ticketService об'єкт служби для взаємодії з квитками
     * @param scanner       об'єкт для зчитування введених користувачем даних з консолі
     */
    private static void addTicket(ClientCrudServiceImpl clientService, PlanetCrudServiceImpl planetService,
                                  TicketCrudServiceImpl ticketService, Scanner scanner) {
        System.out.print("Enter client ID: ");
        long clientId = scanner.nextLong();
        Client client = clientService.findById(clientId);
        if (client == null) {
            logger.warn("Client with ID {} not found.", clientId);
            System.out.println("Client with ID " + clientId + " not found.");
            return;
        }

        scanner.nextLine();
        System.out.print("Enter starting planet ID: ");
        String startingPlanetId = scanner.nextLine();
        System.out.print("Enter ending planet ID: ");
        String endingPlanetId = scanner.nextLine();

        Planet startingPlanet = planetService.findById(startingPlanetId);
        if (startingPlanet == null) {
            logger.warn("Starting planet with ID {} not found.", startingPlanetId);
            System.out.println("Starting planet with ID " + startingPlanetId + " not found.");
            return;
        }

        Planet endingPlanet = planetService.findById(endingPlanetId);
        if (endingPlanet == null) {
            logger.warn("Ending planet with ID {} not found.", endingPlanetId);
            System.out.println("Ending planet with ID " + endingPlanetId + " not found.");
            return;
        }

        Ticket ticket = new Ticket();
        ticket.setClient(client);
        ticket.setFromPlanet(startingPlanet);
        ticket.setToPlanet(endingPlanet);
        ticketService.saveOrUpdate(ticket);
        logger.info("New ticket added for client with ID {}.", clientId);
        System.out.println("Ticket added successfully.");
    }

    /**
     * Оновлює існуючий квиток з новими даними про клієнта та планети відправлення та прибуття.
     *
     * @param ticketService об'єкт служби для взаємодії з квитками
     * @param clientService об'єкт служби для взаємодії з клієнтами
     * @param planetService об'єкт служби для взаємодії з планетами
     * @param scanner       об'єкт для зчитування введених користувачем даних з консолі
     */
    private static void updateTicket(TicketCrudServiceImpl ticketService, ClientCrudServiceImpl clientService,
                                     PlanetCrudServiceImpl planetService, Scanner scanner) {
        System.out.print("Enter ticket ID to update: ");
        long ticketId = scanner.nextLong();
        Ticket ticketToUpdate = ticketService.findById(ticketId);
        if (ticketToUpdate != null) {
            System.out.print("Enter new client ID: ");
            long newClientId = scanner.nextLong();
            Client newClient = clientService.findById(newClientId);
            if (newClient == null) {
                logger.warn("Client with ID {} not found.", newClientId);
                System.out.println("Client with ID " + newClientId + " not found.");
                return;
            }

            scanner.nextLine();
            System.out.print("Enter new starting planet ID: ");
            String newStartingPlanetId = scanner.nextLine();
            Planet newStartingPlanet = planetService.findById(newStartingPlanetId);
            if (newStartingPlanet == null) {
                logger.warn("Starting planet with ID {} not found.", newStartingPlanetId);
                System.out.println("Starting planet with ID " + newStartingPlanetId + " not found.");
                return;
            }

            System.out.print("Enter new ending planet ID: ");
            String newEndingPlanetId = scanner.nextLine();
            Planet newEndingPlanet = planetService.findById(newEndingPlanetId);
            if (newEndingPlanet == null) {
                logger.warn("Ending planet with ID {} not found.", newEndingPlanetId);
                System.out.println("Ending planet with ID " + newEndingPlanetId + " not found.");
                return;
            }

            // Оновлюємо дані квитка з новими значеннями
            ticketToUpdate.setClient(newClient);
            ticketToUpdate.setFromPlanet(newStartingPlanet);
            ticketToUpdate.setToPlanet(newEndingPlanet);

            // Зберігаємо оновлений квиток у базі даних
            ticketService.saveOrUpdate(ticketToUpdate);
            logger.info("Ticket with ID {} updated successfully.", ticketId);
            System.out.println("Ticket with ID " + ticketId + " updated successfully.");
        } else {
            logger.warn("Ticket with ID {} not found.", ticketId);
            System.out.println("Ticket with ID " + ticketId + " not found.");
        }
    }

    /**
     * Видаляє квиток за вказаним ідентифікатором.
     *
     * @param ticketService об'єкт служби для взаємодії з квитками
     * @param scanner       об'єкт для зчитування введених користувачем даних з консолі
     */
    private static void deleteTicket(TicketCrudServiceImpl ticketService, Scanner scanner) {
        System.out.print("Enter ticket ID to delete: ");
        long ticketId = scanner.nextLong();
        Ticket ticketToDelete = ticketService.findById(ticketId);
        if (ticketToDelete != null) {
            ticketService.delete(ticketToDelete);
            logger.info("Ticket with ID {} deleted successfully.", ticketId);
            System.out.println("Ticket with ID " + ticketId + " deleted successfully.");
        } else {
            logger.warn("Ticket with ID {} not found.", ticketId);
            System.out.println("Ticket with ID " + ticketId + " not found.");
        }
    }
}