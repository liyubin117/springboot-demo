package org.rick.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/servlet2")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write("<h1>hello servlet powerd by springboot</h1>");
        writer.write("<h1>hello servlet powerd by springboot 1</h1>");
        writer.write("<h1>hello servlet powerd by springboot 2</h1>");
        writer.close();
    }
}
