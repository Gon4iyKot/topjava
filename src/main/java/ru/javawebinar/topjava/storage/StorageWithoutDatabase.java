package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StorageWithoutDatabase implements Storage {
    private Map<String, Meal> mealMap = new ConcurrentHashMap<>();

    @Override
    public void saveMeal(Meal meal) {
        mealMap.put(meal.getUuid(), meal);
    }

    @Override
    public Meal getMeal(String uuid) {
        return mealMap.get(uuid);
    }

    @Override
    public void deleteMeal(String uuid) {
        mealMap.remove(uuid);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }
}