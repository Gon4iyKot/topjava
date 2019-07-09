package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getWithUser() {
        Meal actual = service.getMealWithUser((MEAL1_ID + 4), USER_ID);
        assertThat(actual).isEqualToComparingFieldByField(MEAL5);
    }

    @Test
    public void getWithWrongUser() {
        thrown.expect(NotFoundException.class);
        Meal actual = service.getMealWithUser((MEAL1_ID + 4), ADMIN_ID);
        assertThat(actual).isEqualToComparingFieldByField(MEAL5);
    }

    @Test
    public void getWithWrongMealId() {
        thrown.expect(NotFoundException.class);
        Meal actual = service.getMealWithUser((MEAL1_ID + 99999), USER_ID);
        assertThat(actual).isEqualToComparingFieldByField(MEAL5);
    }
}