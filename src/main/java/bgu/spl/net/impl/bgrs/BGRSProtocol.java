package bgu.spl.net.impl.bgrs;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.BGRSServer.ConnectionHandler;
import bgu.spl.net.impl.BGRSServer.Message;

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
