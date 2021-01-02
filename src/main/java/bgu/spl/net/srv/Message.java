package bgu.spl.net.srv;

public interface Message{

    Message process(ConnectionHandler handler);
    byte[] encode();
}
