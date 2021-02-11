package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDaoMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet  extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private MealDaoMemory dao;
    private AtomicInteger count = new AtomicInteger(7);

    public MealServlet() {
        super();
        dao = new MealDaoMemory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("action") != null) {
            String action = request.getParameter("action");

            if (action.equalsIgnoreCase("delete")) {
                log.debug("delete meal");
                int id = Integer.parseInt(request.getParameter("id"));
                dao.delete(id);
                response.sendRedirect("meals");
                return;
            }

            if (action.equalsIgnoreCase("edit")) {
                log.debug("edit meal");
                int id = Integer.parseInt(request.getParameter("id"));
                Meal meal = dao.getById(id);
                request.setAttribute("meal", meal);
                RequestDispatcher view = request.getRequestDispatcher("/edit.jsp");
                view.forward(request, response);
                return;
            }

            if (action.equalsIgnoreCase("add")) {
                log.debug("create meal");
                RequestDispatcher view = request.getRequestDispatcher("/edit.jsp");
                view.forward(request, response);
                return;
            }
        }

        log.debug("list meal");
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        RequestDispatcher view = request.getRequestDispatcher("/meals.jsp");
        request.setAttribute("meals", mealsTo);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        String date = request.getParameter("dateTime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        if (request.getParameter("id").equals("")) {
            Meal meal = new Meal(count.incrementAndGet(), dateTime, description, calories);
            dao.add(meal);
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            Meal newMeal = new Meal(id, dateTime, description, calories);
            dao.update(newMeal);
        }

        response.sendRedirect("meals");
    }
}
