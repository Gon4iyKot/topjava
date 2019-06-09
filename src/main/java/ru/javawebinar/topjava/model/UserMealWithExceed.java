package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }
    public UserMealWithExceed(UserMeal meal, boolean exceed) {
        this.dateTime = meal.getDateTime();
        this.description = meal.getDescription();
        this.calories = meal.getCalories();
        this.exceed = exceed;
    }

    public void printThis() {
        System.out.println(this.dateTime);
        System.out.println(this.description);
        System.out.println(this.calories);
        System.out.println(this.exceed);
    }
}
