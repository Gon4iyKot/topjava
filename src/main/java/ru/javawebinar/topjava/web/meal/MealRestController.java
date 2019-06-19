package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;
    int authKey;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        authKey = SecurityUtil.authUserId();
        log.info("create meal {}", meal);
        meal.setUserId(authKey);
        checkNew(meal);
        return service.create(meal, authKey);
    }

    public Meal get(int id) {
        authKey = SecurityUtil.authUserId();
        log.info("get by id {}", id);
        return service.get(id, authKey);
    }

    public void delete(int id) {
        authKey = SecurityUtil.authUserId();
        log.info("delete by id {}", id);
        service.delete(id, authKey);
    }

    public void update(Meal meal, int id) {
        authKey = SecurityUtil.authUserId();
        log.info("update meal {}", meal);
        meal.setUserId(authKey);
        assureIdConsistent(meal, id);
        service.update(meal, authKey);
    }

    public Collection<Meal> getAll() {
        authKey = SecurityUtil.authUserId();
        log.info("getall with authKey {}", authKey);
        return service.getAll(authKey);
    }
}