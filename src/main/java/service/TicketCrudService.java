package service;

import entity.Ticket;

import java.util.List;

/**
 * Інтерфейс для взаємодії з об'єктами квитків (Ticket).
 */
public interface TicketCrudService {

    /**
     * Зберігає або оновлює об'єкт квитка.
     *
     * @param ticket Об'єкт квитка для збереження або оновлення.
     */
    void saveOrUpdate(Ticket ticket);

    /**
     * Видаляє об'єкт квитка.
     *
     * @param ticket Об'єкт квитка для видалення.
     */
    void delete(Ticket ticket);

    /**
     * Знаходить об'єкт квитка за його ідентифікатором.
     *
     * @param id Ідентифікатор квитка.
     * @return Об'єкт квитка, якщо знайдено, або null, якщо не знайдено.
     */
    Ticket findById(Long id);

    /**
     * Отримує всі квитки з бази даних.
     *
     * @return Список всіх квитків.
     */
    List<Ticket> getAllTickets();

    /**
     * Виводить усі квитки в консоль.
     */
    void printAllTickets();

    /**
     * Перевіряє коректність об'єкта квитка перед збереженням або оновленням.
     *
     * @param ticket Об'єкт квитка для перевірки.
     * @return true, якщо об'єкт квитка коректний, або false, якщо некоректний.
     */
    boolean validateTicket(Ticket ticket);

    /**
     * Оновлює об'єкт квитка з новими даними.
     *
     * @param ticket Об'єкт квитка для оновлення.
     */
    void update(Ticket ticket);
}