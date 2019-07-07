package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUser(em.getReference(User.class, userId));
        if (meal.isNew()) {
            return crudRepository.save(meal);
        } else {
            var foundMeal = crudRepository.findById(meal.getId()).orElse(null);
            return (foundMeal!=null&&foundMeal.getUser().getId()==userId)?crudRepository.save(meal):null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId)!=0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal foundMeal = crudRepository.findById(id).orElse(null);
        return (foundMeal!=null&&foundMeal.getUser().getId()==userId)?foundMeal:null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAll(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.findAllBetween(userId, startDate, endDate);
    }
}
