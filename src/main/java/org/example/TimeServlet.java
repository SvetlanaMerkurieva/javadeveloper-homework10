package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String timezone = TimezoneValidateFilter.getTimezone(req);

        String dataTime = LocalDateTime.now(ZoneId.of(timezone)).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.write("<html><body>");
        out.write("<h1>Time zone: ${timezone}</h1>".replace("${timezone}", timezone));
        out.write("<h2>${dateTime}</h2>".replace("${dateTime}", dataTime));
        out.write("</body></html>");
        out.close();

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
