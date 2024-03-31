package service;

import entity.Client;

import java.util.List;

/**
 * Інтерфейс, який описує операції CRUD для об'єктів клієнтів.
 */
public interface ClientCrudService {

    /**
     * Зберігає або оновлює об'єкт клієнта в базі даних.
     *
     * @param client об'єкт клієнта для збереження або оновлення
     */
    void saveOrUpdate(Client client);

    /**
     * Видаляє об'єкт клієнта з бази даних.
     *
     * @param client об'єкт клієнта для видалення
     */
    void delete(Client client);

    /**
     * Знаходить об'єкт клієнта за його унікальним ідентифікатором.
     *
     * @param id унікальний ідентифікатор клієнта
     * @return об'єкт клієнта, якщо знайдено; null, якщо не знайдено
     */
    Client findById(Long id);

    /**
     * Отримує всіх клієнтів з бази даних.
     *
     * @return список всіх клієнтів
     */
    List<Client> getAllClients();

    /**
     * Виводить весь список клієнтів з бази даних.
     */
    void printAllClients();

    /**
     * Додає нового клієнта з заданим ім'ям до бази даних.
     *
     * @param name ім'я нового клієнта
     */
    void addClient(String name);
}