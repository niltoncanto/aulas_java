<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mack.clinica.model.Usuario" %>
<%@ include file="header_paciente.jsp" %>
<body>
<!-- Conteúdo centralizado -->
<div class="container">
    <h1>Meu Cadastro</h1>
    <%
    Usuario usuario = (Usuario) request.getAttribute("usuario");
    String sucesso = (String) request.getParameter("sucesso");
    if (usuario != null) {
    %>
        <form class="form-cadastro" action="atualizarUsuario" method="post">
            <div class="form-group">
                <input type="hidden" name="id" id="id" value="<%= usuario.getId() %>" >
            </div>

            <div class="form-group">
                <label for="nome">Nome:</label>
                <input type="text" name="nome" id="nome" value="<%= usuario.getNome() %>" >
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" name="email" id="email" value="<%= usuario.getEmail() %>" >
            </div>

            <div class="form-group">
                <label for="cpf">CPF:</label>
                <input type="text" name="cpf" id="cpf" value="<%= usuario.getCPF() %>" >
            </div>

            <div class="form-group">
                <label for="celular">Celular:</label>
                <input type="text" name="celular" id="celular" value="<%= usuario.getCelular() %>" >
            </div>

            <div class="form-group">
                <label for="senha">Senha:</label>
                <input type="password" name="senha" id="senha" placeholder="********" >
            </div>

            <div class="form-group">
                <label for="confirmarSenha">Confirme Senha:</label>
                <input type="password" name="confirmarSenha" id="confirmarSenha" placeholder="********" >
            </div>
            <button type="submit">Enviar</button>
        </form>
    <%
    } else if (sucesso != null && sucesso.equals("1")) {
    %>
        <div style="color:red;">Usuário atualizado com sucesso!</div>
    <%
    } else {
    %>
        <p>Usuário não encontrado.</p>
    <%
    }
    %>
</div>

</body>
</html>