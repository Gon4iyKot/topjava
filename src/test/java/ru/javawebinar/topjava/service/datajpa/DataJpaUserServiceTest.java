package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL2;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeals() {
        User actual = service.getWithMeals(ADMIN_ID);
        UserTestData.assertMatch(actual, ADMIN);
        MealTestData.assertMatch(actual.getMeals(), List.of(ADMIN_MEAL2, ADMIN_MEAL1));
    }

    @Test
    public void getFalseUserWithMeals() {
        thrown.expect(NotFoundException.class);
        User actual = service.getWithMeals(0);
        UserTestData.assertMatch(actual, ADMIN);
        MealTestData.assertMatch(actual.getMeals(), List.of(ADMIN_MEAL2, ADMIN_MEAL1));
    }

}