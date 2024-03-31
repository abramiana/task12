package util;

import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.flywaydb.core.Flyway;

public class HibernateUtil {

    private static final Logger logger = LogManager.getLogger(HibernateUtil.class);

    private static final String URL = "jdbc:mysql://localhost:3306/spacetravel";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "nenosa55";

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Запускаємо міграцію з Flyway перед створенням SessionFactory
                migrateDatabase();

                StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                        .configure()
                        .applySetting("hibernate.connection.url", URL)
                        .applySetting("hibernate.connection.username", USERNAME)
                        .applySetting("hibernate.connection.password", PASSWORD)
                        .applySetting(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL8Dialect")
                        .applySetting(AvailableSettings.SHOW_SQL, "true")
                        .applySetting(AvailableSettings.HBM2DDL_AUTO, "update")
                        .applySetting(AvailableSettings.POOL_SIZE, "5")
                        .build();

                Metadata metadata = new MetadataSources(standardRegistry)
                        .addAnnotatedClass(Client.class)
                        .addAnnotatedClass(Planet.class)
                        .addAnnotatedClass(Ticket.class)
                        .getMetadataBuilder()
                        .build();

                sessionFactory = metadata.getSessionFactoryBuilder().build();

                logger.info("SessionFactory has been initialized successfully.");
            } catch (Exception e) {
                logger.error("Error initializing SessionFactory: {}", e.getMessage(), e);
                if (sessionFactory != null) {
                    sessionFactory.close();
                }
            }
        }
        return sessionFactory;
    }

    private static void migrateDatabase() {
        Flyway flyway = Flyway.configure()
                .dataSource(URL, USERNAME, PASSWORD)
                .locations("classpath:db/migration")
                .load();
        flyway.migrate();
        logger.info("Database migration has been completed successfully.");
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            logger.info("SessionFactory has been closed.");
        }
    }
}