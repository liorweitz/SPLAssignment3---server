package bgu.spl.net.impl.messages;

import bgu.spl.net.impl.BGRSServer.ConnectionHandler;
import bgu.spl.net.impl.BGRSServer.Message;

public class ACK implements Message {
    final int opcode=12;
    private int ACKWith;

    public ACK(int ACKWith){
        this.ACKWith=ACKWith;
        System.out.println("ack");
    }

    public Message process(ConnectionHandler handler){
        return null;
    }

    public byte[] encode() {
        return (String.valueOf(opcode)+String.valueOf(ACKWith)).getBytes();
    }
}
