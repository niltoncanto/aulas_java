<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <%-- Meta de codificação e título dinâmico --%>
    <meta charset="UTF-8"/>
    <title>${pageTitle != null ? pageTitle : 'Painel do Paciente'}</title>
    <%-- CSS principal da aplicação --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<%-- Navegação comum a todas as páginas (sem link de chat) --%>
<nav class="navbar">
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/paciente_dashboard">Home</a>
        <a href="${pageContext.request.contextPath}/agendarConsulta">Agendar Consulta</a>
        <a href="${pageContext.request.contextPath}/minhaAgenda">Minha Agenda</a>
        <a href="${pageContext.request.contextPath}/meuCadastro">Meu Cadastro</a>
        <a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a>
    </div>
</nav>