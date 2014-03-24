/**
 * 
 * Esta classe implementa o funcionamento de um servidor WebSocket
 * 
 * Utiliza com biblioteca as bibliotecas do Container Jetty
 * 
 * @author Douglas Cabral <contato@douglascabral.com.br>
 * 
 * @see http://www.jansipke.nl/websocket-tutorial-with-java-server-jetty-and-javascript-client/
 * @see http://www.eclipse.org/jetty/
 */
package br.com.douglascabral.websocket;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket 
public class WebSocketServer {
	
	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
	}
	
	@OnWebSocketConnect
	public void onConnect(Session session) {
		System.out.println("Connect: " + session.getRemoteAddress().getAddress());
        try {
            session.getRemote().sendString("Hello Webbrowser");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@OnWebSocketError
	public void onError(Throwable t) {
		System.out.println("Error: " + t.getMessage());
	}
	
	@OnWebSocketMessage 
	public void onMessage(String message) {
		System.out.println("Message: " + message);
	}
	
}
