package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.stream.Collectors;

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
        assureIdConsistent(meal, id);
        service.update(meal, authKey);
    }

    public Collection<Meal> getAll() {
        authKey = SecurityUtil.authUserId();
        log.info("getall with authKey {}", authKey);
        return service.getAll(authKey);
    }

    public Collection<MealTo> getAllFiltered(String startDate, String endDate, String startTime, String endTime) {
        authKey = SecurityUtil.authUserId();
        log.info("getallFiltered with authKey {}", authKey);
        LocalDate dateFrom = startDate.equals("") ? LocalDate.MIN : LocalDate.parse(startDate);
        LocalDate dateTo = endDate.equals("") ? LocalDate.MAX : LocalDate.parse(endDate);
        LocalTime timeFrom = startTime.equals("") ? LocalTime.MIN : LocalTime.parse(startTime);
        LocalTime timeTo = endTime.equals("") ? LocalTime.MAX : LocalTime.parse(endTime);
        return MealsUtil.getWithExcess(service.getAllFiltered(authKey, dateFrom, dateTo), MealsUtil.DEFAULT_CALORIES_PER_DAY)
                .stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(), timeFrom, timeTo)).collect(Collectors.toList());
    }
}