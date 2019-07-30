package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.readFromJson;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.MealsUtil.getWithExcess;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String MEAL_REST_URI = MealRestController.MEALS_REST_URI + "/";

    @Autowired
    protected MealService mealService;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get(MEAL_REST_URI))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(getWithExcess(MEALS, authUserCaloriesPerDay())));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(MEAL_REST_URI + MEAL1_ID))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(MEAL_REST_URI + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    void testUpdate() throws Exception {
        Meal updated = getUpdated();
        mockMvc.perform(put(MEAL_REST_URI + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    void createMeal() throws Exception {
        Meal newMeal = getCreated();
        ResultActions action = mockMvc.perform(post(MEAL_REST_URI)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(newMeal)))
                .andDo(print())
                .andExpect(status().isCreated());
        Meal createdMeal = readFromJson(action, Meal.class);
        newMeal.setId(createdMeal.getId());
        assertMatch(newMeal, createdMeal);
        assertMatch(mealService.getAll(USER_ID), newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);

    }

    @Test
    void getBetween() throws Exception {
        mockMvc.perform(get(MEAL_REST_URI + "/filtered?startDate=2015-05-30&endDate=2015-05-30"))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(contentJson(getWithExcess(List.of(MEAL3, MEAL2, MEAL1), SecurityUtil.authUserCaloriesPerDay())));
    }

    @Test
    void getBetweenWithNull() throws Exception {
        mockMvc.perform(get(MEAL_REST_URI + "/filtered?startDate=2015-05-30&endDate=2015-05-30&startTime=12:00:00&endTime="))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(contentJson(getWithExcess(List.of(MEAL3, MEAL2), SecurityUtil.authUserCaloriesPerDay())));
    }
}