package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    void saveMeal (Meal meal);

    Meal getMeal (String uuid);

    void deleteMeal (String uuid);

    List<Meal> getAll ();
}