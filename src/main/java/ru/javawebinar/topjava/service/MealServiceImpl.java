package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;


@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        Meal tempMeal = checkNotFoundWithId(repository.get(id, userId), id);
        if (tempMeal==null)
            throw new NotFoundException("meal or user was not found");
        return tempMeal;
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public void update(Meal meal, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public Collection<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getAllFiltered(userId, startDate, endDate);
    }
}