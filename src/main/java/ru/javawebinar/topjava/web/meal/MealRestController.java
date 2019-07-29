package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.MEALS_REST_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {

    public static final String MEALS_REST_URI = "/rest/meals";

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Meal get(@PathVariable(name = "id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable(name = "id") int id) {
        super.update(meal, id);
    }

    @PostMapping
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        Meal createdMeal = super.create(meal);
        URI uriOfNewMeal = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(MEALS_REST_URI + "/{id}")
                .buildAndExpand(createdMeal.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewMeal).body(createdMeal);
    }

    @Override
    @GetMapping(value = "/filtered", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(@RequestParam(required = false) LocalDate startDate,
                                   @RequestParam(required = false) LocalTime startTime,
                                   @RequestParam(required = false) LocalDate endDate,
                                   @RequestParam(required = false) LocalTime endTime
    ) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}