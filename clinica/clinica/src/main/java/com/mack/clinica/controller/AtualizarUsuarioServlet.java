package com.mack.clinica.controller;

import java.io.IOException;

import com.mack.clinica.model.Usuario;
import com.mack.clinica.model.UsuarioDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/atualizarUsuario")
public class AtualizarUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera os parâmetros do formulário
        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String cpf = request.getParameter("cpf");
        String celular = request.getParameter("celular");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");
        String realPathBase = request.getServletContext().getRealPath("/");

        System.out.println("UsuarioID: " + id);
        System.out.println("UsuarioNome: " + nome);

        // Verifica se as senhas são iguais
        if (senha != null && !senha.equals(confirmarSenha)) {
            // Senhas diferentes, volta para o formulário com mensagem de erro
            request.setAttribute("erroSenha", "As senhas não conferem. Por favor, tente novamente.");
            // Manda de volta para o JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("meu_cadastro.jsp");
            dispatcher.forward(request, response);
            return; // Termina o processamento
        }

        // Continua o processo normalmente se as senhas forem iguais
        Usuario usuario = new Usuario();
        usuario.setId(Integer.parseInt(id));
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setCPF(cpf);
        usuario.setCelular(celular);
        usuario.setSenha(senha); // Aqui você definiria a senha nova
 
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if(usuarioDAO.atualizarUsuario(usuario, realPathBase)){
            // Depois de atualizar, pode redirecionar para uma página de sucesso
            response.sendRedirect("meu_cadastro.jsp?sucesso=1");
        }else{
            response.sendRedirect("meu_cadastro.jsp?sucesso=0");
        }

    }
}
