package bgu.spl.net.api;

import bgu.spl.net.srv.ConnectionHandler;

import java.net.SocketAddress;

public interface MessagingProtocol<T> {
 
    /**
     * process the given message 
     * @param msg the received message
     * @param localAddress
     * @return the response to send or null if no response is expected by the client
     */
    T process(T msg, ConnectionHandler handler);
 
    /**
     * @return true if the connection should be terminated
     */
    boolean shouldTerminate();
 
}