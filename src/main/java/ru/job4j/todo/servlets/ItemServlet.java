package ru.job4j.todo.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HbmStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ItemServlet extends HttpServlet {
    private final static Gson GSON = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
    private final HbmStore store = HbmStore.instOf();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        List<Item> items = store.findAll();
        String json = GSON.toJson(items);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json; charset=utf-8");
        String id = req.getParameter("id");
        if (id != null) {
            store.update(Integer.parseInt(id));
        } else {
            String[] cIds = req.getParameterValues("category");
            String description = req.getParameter("Text");
            User user = (User) req.getSession().getAttribute("user");
            store.add(Item.itemOf(0, description, user), cIds);
        }
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}