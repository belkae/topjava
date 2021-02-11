package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealDaoMemory implements MealRepository {

    List<Meal> mealsPrepare = Arrays.asList(
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    private CopyOnWriteArrayList<Meal> meals = new CopyOnWriteArrayList(mealsPrepare);

    public void add(Meal meal) {
        meals.add(meal);
    }

    public void delete(int id) {
        Meal meal = getById(id);
        if (meal != null) {
            meals.remove(meal);
        }
    }

    public void update(Meal meal) {
        Meal updateMeal = getById(meal.getId());
        updateMeal.setDateTime(meal.getDateTime());
        updateMeal.setCalories(meal.getCalories());
        updateMeal.setDescription(meal.getDescription());
    }

    public Meal getById(int id) {
        for(Meal meal: meals) {
            if (meal.getId() == id) {
                return meal;
            }
        }
        return null;
    }

    public List<Meal> getAll() {
        return meals;
    }
}
