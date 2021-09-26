package ru.job4j.todo.servlets;

import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HbmStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        if (HbmStore.instOf().findUserByEmail(email) == null) {
            User user = User.userOf(
                    0, req.getParameter("name"), email, req.getParameter("password"));
            System.out.println(user);
            HbmStore.instOf().add(user);
            req.setAttribute("error", "Регистрация завершена, авторизуйтесь в системе");
        } else {
            req.setAttribute("error", "Пользователь с таким email уже существует");
        }
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }
}
