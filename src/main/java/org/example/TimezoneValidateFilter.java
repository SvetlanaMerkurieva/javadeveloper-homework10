package org.example;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.zone.ZoneRulesException;


@WebFilter(value = "/time/*")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException, ZoneRulesException {
        String timezone = getTimezone(req);
        try {
            chain.doFilter(req, resp);
        } catch (ZoneRulesException e) {
            if (e.getMessage().equals("Unknown time-zone ID: " + timezone)) {
                resp.setStatus(400);
                resp.setContentType("text/html");

                PrintWriter out = resp.getWriter();
                out.write("<html><body>");
                out.write("<h2>Invalid timezone<h2>");
                out.write("</body></html>");
                out.close();
            }
            e.getMessage();
        }
    }
    public static String getTimezone(HttpServletRequest req) {
        if (req.getParameterMap().containsKey("timezone")) {
            String timezone =  req.getParameter("timezone");
            if (timezone.contains(" ")) {
                return timezone.substring(0, 3) + "+" + timezone.substring(4, timezone.length());
            }
            return timezone;
        }
        return "UTC";
    }
}
