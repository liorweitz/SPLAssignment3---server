package bgu.spl.net.impl.bgrs;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Message;
import bgu.spl.net.srv.MessageWithProcess;

import java.net.SocketAddress;


public class BGRSProtocol implements MessagingProtocol<Message> {
    @Override
    public Message process(Message msg, ConnectionHandler handler) {
        return ((MessageWithProcess)msg).process(handler);
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
