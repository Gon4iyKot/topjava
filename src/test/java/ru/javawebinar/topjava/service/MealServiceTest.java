package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

/*
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }
*/

    @Autowired
    MealService service;

    @Before
    public void setUp() {

    }

    @Test
    public void get() {
        Meal meal = service.get(FIRST_MEAL_ID, USER_ID);
        assertMatch(meal, FIRST_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void getOthersFood() {
        service.get(FIRST_MEAL_ID, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(SECOND_MEAL_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), Arrays.asList(FIRST_MEAL, THIRD_MEAL));
    }

    @Test(expected = NotFoundException.class)
    public void deleteOthersFood() {
        service.delete(SECOND_MEAL_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> mealList = service.getBetweenDates(LocalDate.of(2019, Month.JUNE, 26), LocalDate.of(2019, Month.JUNE, 26), USER_ID);
        assertMatch(mealList, Arrays.asList(SECOND_MEAL, FIRST_MEAL));
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> mealList = service.getBetweenDateTimes(LocalDateTime.of(2019, Month.JUNE, 25, 13, 0), LocalDateTime.of(2019, Month.JUNE, 26, 12, 0), USER_ID);
        assertMatch(mealList, Arrays.asList(FIRST_MEAL, THIRD_MEAL));
    }

    @Test
    public void getAll() {
        List<Meal> mealList = service.getAll(USER_ID);
        assertMatch(mealList, MEAL_LIST_USER);
    }

    @Test
    public void update() {
        Meal newAdminMeal = new Meal(FOURTH_MEAL_ID, LocalDateTime.of(2015, Month.JUNE, 26, 15, 0), "поменяно", 500);
        service.update(newAdminMeal, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), Arrays.asList(FIFTH_MEAL, newAdminMeal));
    }

    @Test(expected = NotFoundException.class)
    public void updateOthersFood() {
        Meal newAdminMeal = new Meal(FOURTH_MEAL_ID, LocalDateTime.of(2015, Month.JUNE, 26, 15, 0), "поменяно", 500);
        service.update(newAdminMeal, USER_ID);
    }

    @Test
    public void create() {
        Meal newAdminMeal = new Meal(null, LocalDateTime.of(2015, Month.JUNE, 26, 15, 0), "нюхал водород", 500);
        Meal created = service.create(newAdminMeal, ADMIN_ID);
        newAdminMeal.setId(created.getId());
        assertMatch(service.getAll(ADMIN_ID), Arrays.asList(FIFTH_MEAL, FOURTH_MEAL, newAdminMeal));
    }
}