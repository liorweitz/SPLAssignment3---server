package bgu.spl.net.impl.messages;

import bgu.spl.net.srv.Message;

public class ACK implements Message {
    final int opcode=12;
    private int ACKWith;

    public ACK(int ACKWith){
        this.ACKWith=ACKWith;
        System.out.println("ack");
    }
}
