package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "медведи на велосипеде", 500), 9);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "кот задом наперед", 500), 2);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "комарики на воздушном шарике", 500), 2);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "раки на хромой собаке", 500), 2);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "волки на кобыле", 500), 2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMeals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            userMeals.put(meal.getId(), meal);
            return meal;
        }
        try {
            if (meal.getUserId() != userId) {
                throw new NotFoundException("user is not an owner of this meal");
            }
            // treat case: update, but absent in storage
            return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } catch (NullPointerException e) {
            throw new NotFoundException("id does not exist");
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        try {
            return repository.get(userId) != null && repository.get(userId).remove(id) != null;
        } catch (NullPointerException e) {
            throw new NotFoundException("id not found");
        }
    }

    @Override
    public Meal get(int id, int userId) {
        try {
            Meal tempMeal = repository.get(userId).get(id);
            log.info("get {}", tempMeal);
            return tempMeal.getUserId() == userId ? tempMeal : null;
        } catch (NullPointerException e) {
            throw new NotFoundException("id not found");
        }
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());
    }

    public Collection<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getFilteredAll запущен");
        return getAll(userId).stream().filter(a -> DateTimeUtil
                .isBetweenDate(a.getDate(), startDate, endDate)).collect(Collectors.toList());
    }
}