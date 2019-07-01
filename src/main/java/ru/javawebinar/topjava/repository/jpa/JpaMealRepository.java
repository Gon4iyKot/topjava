package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            User ref = entityManager.getReference(User.class, userId);
            meal.setUser(ref);
            entityManager.persist(meal);
            return meal;
        } else {
            return meal.getUser().getId() == userId ? entityManager.merge(meal) : null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return entityManager.createQuery("DELETE FROM Meal m WHERE m.id=?1 AND m.user.id=?2")
                .setParameter(1, id)
                .setParameter(2, userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> mealList = entityManager.createQuery("SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.id=?1 and m.user.id=?2", Meal.class)
                .setParameter(1, id)
                .setParameter(2, userId)
                .getResultList();
        return DataAccessUtils.singleResult(mealList);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return entityManager.createQuery("SELECT m FROM Meal m LEFT JOIN FETCH m.user " +
                "WHERE m.user.id=?1 ORDER BY m.dateTime DESC", Meal.class)
                .setParameter(1, userId).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return entityManager.createQuery("SELECT m FROM Meal m LEFT JOIN FETCH m.user" +
                " WHERE m.user.id=?1 and m.dateTime BETWEEN ?2 AND ?3 ORDER BY m.dateTime DESC", Meal.class)
                .setParameter(1, userId)
                .setParameter(2, startDate)
                .setParameter(3, endDate)
                .getResultList();
    }
}