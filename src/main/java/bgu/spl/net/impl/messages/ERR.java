package bgu.spl.net.impl.messages;

import bgu.spl.net.srv.Message;

public class ERR implements Message {
    final int opcode=13;
    private int errorWith;

    public ERR(int error){
        this.errorWith=error;
        System.out.println("error");
    }
}
