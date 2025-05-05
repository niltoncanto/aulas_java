<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mack.clinica.model.Usuario" %>
<%@ include file="header_paciente.jsp" %>
<body>

<!-- Conteúdo centralizado -->
<div class="content">
    <h1>Agendar Consulta</h1>
    <form method="post" action="agendarConsulta" class="form-container">
        <label for="profissionalId">Profissional:</label>
        <select id="profissionalId" name="profissionalId" required>
            <option value="">Selecione o médico</option>
            <%
                List<Usuario> medicos = (List<Usuario>) request.getAttribute("medicos");
                if (medicos != null) {
                    for (Usuario medico : medicos) {
            %>
                        <option value="<%= medico.getId() %>"><%= medico.getNome() %></option>
            <%
                    }
                }
            %>
        </select>

        <label for="dataHora">Data e Hora:</label>
        <input type="datetime-local" id="dataHora" name="dataHora" required>

        <button type="submit" class="button">Agendar</button>
    </form>
</div>

</body>
</html>