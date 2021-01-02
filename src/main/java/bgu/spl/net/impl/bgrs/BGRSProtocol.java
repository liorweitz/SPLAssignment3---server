package bgu.spl.net.impl.bgrs;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Message;

public class BGRSProtocol implements MessagingProtocol<Message> {
    @Override
    public Message process(Message msg, ConnectionHandler handler) {
        return (msg).process(handler);
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
