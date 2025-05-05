package com.example.helloworld;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Mapeamento da URL para o Servlet
@WebServlet(name = "HelloWorldServlet", urlPatterns = {"/hello"})
public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Define o tipo de resposta como HTML
        response.setContentType("text/html;charset=UTF-8");
        // Envia o conteúdo da resposta
        response.getWriter().println("<html>");
        response.getWriter().println("<head><title>Hello World Servlet</title></head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<h1>Hello, World! Este é o Servlet!</h1>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }
}
