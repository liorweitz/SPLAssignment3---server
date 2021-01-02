package bgu.spl.net.impl.messages;

import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Message;

public class Data implements Message {
    private String data;

    public Data(String substring){
        this.data=substring;
    }

    @Override
    public Message process(ConnectionHandler handler) {
        return null;
    }

    @Override
    public byte[] encode() {
        return data.getBytes();
    }
}
