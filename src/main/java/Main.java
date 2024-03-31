import entity.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import service.ClientCrudServiceImpl;
import service.PlanetCrudServiceImpl;

import java.util.Scanner;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        ClientCrudServiceImpl clientService = new ClientCrudServiceImpl(sessionFactory);
        PlanetCrudServiceImpl planetService = new PlanetCrudServiceImpl(sessionFactory);

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. Add Client");
            System.out.println("2. Print All Clients");
            System.out.println("3. Print All Planets");
            System.out.println("4. Delete Client");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addClient(clientService, scanner);
                    break;
                case 2:
                    clientService.printAllClients();
                    break;
                case 3:
                    planetService.printAllPlanets();
                    break;
                case 4:
                    deleteClient(clientService, scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    logger.warn("Invalid choice entered: {}", choice);
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        HibernateUtil.shutdown();
    }

    private static void addClient(ClientCrudServiceImpl clientService, Scanner scanner) {
        scanner.nextLine(); // consume newline
        System.out.print("Enter client name: ");
        String name = scanner.nextLine();
        clientService.addClient(name);
    }

    private static void deleteClient(ClientCrudServiceImpl clientService, Scanner scanner) {
        System.out.print("Enter client ID to delete: ");
        long id = scanner.nextLong();
        Client clientToDelete = clientService.findById(id);
        if (clientToDelete != null) {
            clientService.delete(clientToDelete);
            System.out.println("Client with ID " + id + " deleted successfully.");
        } else {
            System.out.println("Client with ID " + id + " not found.");
        }
    }
}
