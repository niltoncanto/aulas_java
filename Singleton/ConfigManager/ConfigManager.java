import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Classe Singleton para gerenciar configurações
public class ConfigManager {

    // Instância única do ConfigManager
    private static ConfigManager instancia;

    // Estrutura para armazenar as configurações carregadas
    private Map<String, String> configuracoes;

    // Construtor privado
    private ConfigManager() {
        configuracoes = new HashMap<>();
    }

    // Método público para obter a única instância
    public static ConfigManager getInstancia() {
        if (instancia == null) {
            instancia = new ConfigManager();
        }
        return instancia;
    }

    // Método para carregar configurações de um arquivo .properties
    public void carregar(String caminhoArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linha = linha.trim();
                // Ignora linhas em branco e comentários
                if (linha.isEmpty() || linha.startsWith("#")) {
                    continue;
                }
                // Divide a linha em chave=valor
                String[] partes = linha.split("=", 2);
                if (partes.length == 2) {
                    configuracoes.put(partes[0].trim(), partes[1].trim());
                }
            }
            System.out.println("Configurações carregadas com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo de configurações: " + e.getMessage());
        }
    }

    // Método para acessar uma configuração específica
    public String getConfiguracao(String chave) {
        return configuracoes.get(chave);
    }

    // Método para exibir todas as configurações (opcional, para testes)
    public void exibirTodas() {
        System.out.println("===== Configurações =====");
        for (Map.Entry<String, String> entry : configuracoes.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Obtendo a instância do ConfigManager
        ConfigManager config = ConfigManager.getInstancia();

        // Carregando as configurações de um arquivo .properties
        config.carregar("config.properties");

        // Acessando configurações específicas
        System.out.println("Servidor: " + config.getConfiguracao("servidor"));
        System.out.println("Porta: " + config.getConfiguracao("porta"));
        System.out.println("Modo: " + config.getConfiguracao("modo"));

        // Exibindo todas as configurações
        config.exibirTodas();
    }
}

