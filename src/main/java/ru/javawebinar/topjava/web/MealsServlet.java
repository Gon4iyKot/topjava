package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.storage.StorageWithoutDatabase;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsServlet extends HttpServlet {
    private static final Logger log = getLogger(MealsServlet.class);
    private List<MealTo> fullListOfMeals;
    private Storage mealStorage;

    @Override
    public void init() throws ServletException {
        super.init();
        mealStorage = new StorageWithoutDatabase();
        mealStorage.saveMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealStorage.saveMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealStorage.saveMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealStorage.saveMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealStorage.saveMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealStorage.saveMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        getMealsFromStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to fullListOfMeals");

        String action = request.getParameter("action");
        String uuid = request.getParameter("uuid");

        if (action == null) {
            request.setAttribute("fullListOfMeals", fullListOfMeals);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }

        switch (action) {
            case "delete":
                mealStorage.deleteMeal(uuid);
                getMealsFromStorage();
                response.sendRedirect("meals");
                break;
            case "edit":
                request.setAttribute("meal", mealStorage.getMeal(uuid));
                request.getRequestDispatcher("edit.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (uuid == null) {
            mealStorage.saveMeal(new Meal(dateTime, request.getParameter("description"), calories));
        } else {
            mealStorage.saveMeal(new Meal(uuid, dateTime, request.getParameter("description"), calories));
        }
        getMealsFromStorage();
        response.sendRedirect("meals");
    }

    private void getMealsFromStorage() {
        fullListOfMeals = MealsUtil.getFilteredWithExcess(mealStorage.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 0), 2000);
    }
}