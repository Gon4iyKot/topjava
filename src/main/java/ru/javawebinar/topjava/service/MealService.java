package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealService {

    Meal create(Meal meal, int userId);

    Meal get(int id, int userId);

    void delete(int id, int userId);

    void update(Meal meal, int userId);

    Collection<Meal> getAll(int userId);
}