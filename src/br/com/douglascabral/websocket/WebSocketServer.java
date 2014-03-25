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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket 
public class WebSocketServer {

	private static List<Session> sessions = Collections.synchronizedList(new ArrayList<Session>());
	
	Session session = null;
	
	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		synchronized (sessions) {
			sessions.remove( this.session );
		}
		
		System.out.println("Existem " + sessions.size() + " sessões ativas" );
		
	}
	
	
	@OnWebSocketConnect
	public void onConnect(Session session) {
		
		sessions.add(session);
		
		this.session = session;
		
		System.out.println("Connect: " + session.getRemoteAddress().getAddress() + " / " + session.getLocalAddress().getAddress());
		System.out.println("Existem " + sessions.size() + " sessões ativas" );
		
        try {
            session.getRemote().sendString("Conectado!");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	@OnWebSocketError
	public void onError(Throwable t) {}
	
	
	@OnWebSocketMessage 
	public void onMessage(Session session, String message) {
		
		int hash = session.hashCode();
		
		try {
			for (Session s : this.sessions) {
				s.getRemote().sendString(hash + ": " + message);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
