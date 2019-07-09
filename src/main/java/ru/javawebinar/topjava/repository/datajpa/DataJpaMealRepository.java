package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    @Autowired
    private CrudMealRepository crudMealRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUser(crudUserRepository.getOne(userId));
        if (meal.isNew()) {
            return crudMealRepository.save(meal);
        } else {
            var foundMeal = crudMealRepository.findById(meal.getId()).orElse(null);
            return checkForNullAndUser(foundMeal, userId) ? crudMealRepository.save(meal) : null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal foundMeal = crudMealRepository.findById(id).orElse(null);
        return checkForNullAndUser(foundMeal, userId) ? foundMeal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.findAll(userId);
    }

    @Override
    public Meal getMealWithUser(int id, int userId) {
        return crudMealRepository.getMealWithUser(id, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudMealRepository.findAllBetween(userId, startDate, endDate);
    }

    private boolean checkForNullAndUser(Meal meal, int userId) {
        return (meal != null && meal.getUser().getId() == userId);
    }
}