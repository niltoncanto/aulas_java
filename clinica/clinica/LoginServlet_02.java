package com.mack.clinica.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet responsável por autenticar o usuário e redirecioná-lo para o painel correto.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Método que processa requisições POST do formulário de login.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtém o usuário e a senha enviados pelo formulário
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        // Verifica se o usuário é o administrador
        if ("admin".equals(usuario) && "123".equals(senha)) {
            // Redireciona para o painel do administrador
            response.sendRedirect(request.getContextPath() + "/admin_dashboard");
        }
        // Verifica se o usuário é um paciente específico
        else if ("55555555555".equals(usuario) && "123".equals(senha)) {
            // Redireciona para o painel do paciente
            response.sendRedirect(request.getContextPath() + "/paciente_dashboard");
        }
        // Caso o usuário ou senha estejam incorretos
        else {
            // Redireciona de volta para a página de login com erro
            response.sendRedirect(request.getContextPath() + "/index.jsp?erro=1");
        }
    }
}

