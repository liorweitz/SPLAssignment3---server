package bgu.spl.net.impl.messages;

import bgu.spl.net.impl.BGRSServer.ConnectionHandler;
import bgu.spl.net.impl.BGRSServer.Message;

public class ACK implements Message {
    final int opcode=12;
    private int ACKWith;
    private String data=null;

    public ACK(int ACKWith){
        this.ACKWith=ACKWith;
        System.out.println("ack");
    }

    public ACK(int ACKWith, String data){
        this.ACKWith=ACKWith;
        this.data=data;
    }

    public Message process(ConnectionHandler handler){
        return null;
    }

    public byte[] encode() {
        if (data==null) {
//            StringBuilder sb=new StringBuilder();
//            sb.append("ACK");
//            sb.append(ACKWith);
//            sb.append("\0");
            return ("ACK "+ACKWith+"\0").getBytes();
        }
        else{
            return ("ACK "+ACKWith +data+"\0").getBytes();
        }
    }
}
