package com.mack.clinica.controller;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

/**
 * Endpoint simples de chat via WebSocket.
 */
@ServerEndpoint("/chatws")
public class ChatWebSocket {

    /** Conjunto thread-safe de sessões ativas */
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    /**
     * Chamado quando um cliente abre a conexão.
     * Aqui desativamos o idle timeout e registramos a sessão.
     */
    @OnOpen
    public void onOpen(Session session) {
        // Desativa o timeout de inatividade (0 = nunca expira)
        session.setMaxIdleTimeout(0);

        sessions.add(session);
        System.out.println("Nova conexão WebSocket: " + session.getId());
    }

    /**
     * Chamado ao receber mensagem de um cliente.
     * Faz broadcast assíncrono para todas as sessões abertas.
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        for (Session s : sessions) {
            if (s.isOpen()) {
                try {
                    s.getAsyncRemote()
                     .sendText("[Usuário " + session.getId() + "]: " + message);
                } catch (Exception e) {
                    // Se falhar num cliente, loga e remove só aquela sessão
                    System.err.println("Falha ao enviar para sessão " 
                        + s.getId() + ": " + e.getMessage());
                    sessions.remove(s);
                }
            }
        }
        System.out.println("Mensagem processada e retransmitida: " + message);
    }

    /**
     * Captura qualquer erro interno do WebSocket.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Erro na sessão " 
            + session.getId() + ": " + throwable.getMessage());
        // throwable.printStackTrace(); // descomente para full stacktrace
    }

    /**
     * Chamado quando um cliente fecha a conexão.
     */
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        sessions.remove(session);
        System.out.println("Sessão fechada: " 
            + session.getId() + " Motivo: " + reason);
    }
}
