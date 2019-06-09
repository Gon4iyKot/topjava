package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(UserMealWithExceed::printThis);
        getFilteredWithExceededInStreams(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(UserMealWithExceed::printThis);

//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> exceedMarker = new HashMap<>();
        List<UserMealWithExceed> mealWithExceedList = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalDate dateExtraction = meal.getDateTime().toLocalDate();
            exceedMarker.merge(dateExtraction, meal.getCalories(), (a, b) -> a + b);
        }
        for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealWithExceedList.add(new UserMealWithExceed(meal, exceedMarker.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return mealWithExceedList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededInStreams(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, List<UserMeal>> mealMap = mealList.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate()));
        return mealMap.entrySet().stream().flatMap(entry -> {
            AtomicInteger calorieCounter = new AtomicInteger();
            return entry.getValue().stream().peek(meal -> calorieCounter.addAndGet(meal.getCalories()))
                    .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                    .collect(Collectors.toList()).stream().map(meal -> new UserMealWithExceed(meal, calorieCounter.intValue() > caloriesPerDay));
        }).collect(Collectors.toList());
    }
}
