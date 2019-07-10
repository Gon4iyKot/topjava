package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;


@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getWithUser() {
        Meal actual = service.getMealWithUser((MEAL1_ID + 4), USER_ID);
        assertMatch(actual, MEAL5);
        UserTestData.assertMatch(actual.getUser(), USER);
    }

    @Test
    public void getWithWrongUser() {
        thrown.expect(NotFoundException.class);
        service.getMealWithUser((MEAL1_ID + 4), ADMIN_ID);
    }

    @Test
    public void getWithWrongMealId() {
        thrown.expect(NotFoundException.class);
        service.getMealWithUser((0), USER_ID);
    }
}