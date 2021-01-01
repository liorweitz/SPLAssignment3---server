package bgu.spl.net.srv;

import java.net.SocketAddress;

public interface MessageWithProcess extends Message {
    Message process(ConnectionHandler handler);
}
