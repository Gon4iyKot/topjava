package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = MealAjaxController.REST_URL)
public class MealAjaxController extends AbstractMealController {
    static final String REST_URL = "/ajax/meals";

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void create(@RequestParam(name = "id") Integer id,
                       @RequestParam(name = "dateTime") LocalDateTime dateTime,
                       @RequestParam(name = "description") String description,
                       @RequestParam(name = "calories") Integer calories) {
        Meal newMeal = new Meal(id, dateTime, description, calories);
        if (newMeal.isNew()) {
            super.create(newMeal);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Integer id) {
        super.delete(id);
    }
}