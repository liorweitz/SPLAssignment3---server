package bgu.spl.net.impl.BGRSServer;

public interface Message{

    Message process(ConnectionHandler handler);
    byte[] encode();
}
