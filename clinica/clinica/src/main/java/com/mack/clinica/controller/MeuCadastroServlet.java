package com.mack.clinica.controller;

import java.io.IOException;

import com.mack.clinica.model.Usuario;
import com.mack.clinica.model.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/meuCadastro")
public class MeuCadastroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtém o caminho real do projeto
        String realPathBase = request.getServletContext().getRealPath("/");
        // Busca o email da sessão
        HttpSession session = request.getSession(false); // false para não criar nova sessão
        if (session != null) {
            String email = (String) session.getAttribute("email");
            String tipo = (String) session.getAttribute("tipo");
            System.out.println("Email da sessão1: " + email);
            System.out.println("Tipo da sessão1: " + tipo);
            if (email != null && tipo != null) {
                UsuarioDAO dao = new UsuarioDAO();
                Usuario usuario = dao.buscarPorEmail(email, tipo, realPathBase);
                request.setAttribute("usuario", usuario);
                request.getRequestDispatcher("/meu_cadastro.jsp").forward(request, response);
            }else{
                response.sendRedirect("index.jsp?erro=tipo");
            }
        }
    }
}
