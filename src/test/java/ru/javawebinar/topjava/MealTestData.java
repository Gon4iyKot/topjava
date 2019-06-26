package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    //    User meals
    public static final int FIRST_MEAL_ID = START_SEQ + 2;
    public static final int SECOND_MEAL_ID = START_SEQ + 3;
    public static final Meal FIRST_MEAL = new Meal(FIRST_MEAL_ID, LocalDateTime.of(2019, Month.JUNE, 26, 12, 0), "ехали собаки", 2000);
    public static final Meal SECOND_MEAL = new Meal(SECOND_MEAL_ID, LocalDateTime.of(2019, Month.JUNE, 26, 13, 0), "на волшебном раке", 1000);
    //    Admin meals
    public static final int THIRD_MEAL_ID = START_SEQ + 4;
    public static final int FOURTH_MEAL_ID = START_SEQ + 5;
    public static final int FIFTH_MEAL_ID = START_SEQ + 6;
    public static final Meal THIRD_MEAL = new Meal(THIRD_MEAL_ID, LocalDateTime.of(2019, Month.JUNE, 25, 14, 0), "а за ними кот", 2000);
    public static final Meal FOURTH_MEAL = new Meal(FOURTH_MEAL_ID, LocalDateTime.of(2019, Month.JUNE, 26, 15, 0), "нюхал водород", 500);
    public static final Meal FIFTH_MEAL = new Meal(FIFTH_MEAL_ID, LocalDateTime.of(2019, Month.JUNE, 26, 16, 0), "еще один", 500);

    public static final List<Meal> MEAL_LIST_USER = Arrays.asList(SECOND_MEAL, FIRST_MEAL, THIRD_MEAL);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles");
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields().isEqualTo(expected);
    }
}