package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal, int userId) {
        log.info("create", meal);
        return service.create(meal, userId);
    }

    public Meal get(int id, int userId) {
        log.info("get", id);
        return service.get(id, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete", id);
        service.delete(id, userId);
    }

    public void update(Meal meal, int userId) {
        log.info("update", meal);
        service.update(meal, userId);
    }

    public Collection<Meal> getAll(int userId) {
        log.info("getall", userId);
        return service.getAll(userId);
    }
}