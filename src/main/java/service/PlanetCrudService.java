package service;

import entity.Planet;

import java.util.List;

/**
 * Інтерфейс, який описує операції CRUD для об'єктів планет.
 */
public interface PlanetCrudService {

    /**
     * Зберігає або оновлює об'єкт планети в базі даних.
     *
     * @param planet об'єкт планети для збереження або оновлення
     */
    void saveOrUpdate(Planet planet);

    /**
     * Оновлює існуючу планету з новими даними.
     *
     * @param planet Планета з новими даними для оновлення.
     */
    void update(Planet planet);

    /**
     * Видаляє об'єкт планети з бази даних.
     *
     * @param planet об'єкт планети для видалення
     */
    void delete(Planet planet);

    /**
     * Знаходить об'єкт планети за її унікальним ідентифікатором.
     *
     * @param id унікальний ідентифікатор планети
     * @return об'єкт планети, якщо знайдено; null, якщо не знайдено
     */
    Planet findById(String id);

    /**
     * Отримує всі планети з бази даних.
     *
     * @return список всіх планет
     */
    List<Planet> getAllPlanets();

    /**
     * Виводить весь список планет з бази даних.
     */
    void printAllPlanets();
}