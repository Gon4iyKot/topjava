package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Modifying
    @Transactional
    @Query(name = Meal.DELETE)
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query(name = Meal.ALL_SORTED)
    List<Meal> findAll(@Param("userId") int userId);

    @Query(name = Meal.GET_WITH_USER)
    Meal getMealWithUser(@Param("id") int id, @Param("userId") int userId);

    @Query(name = Meal.GET_BETWEEN)
    List<Meal> findAllBetween(@Param("userId") int userID, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}