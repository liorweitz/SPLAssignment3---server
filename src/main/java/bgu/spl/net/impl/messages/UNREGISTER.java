package bgu.spl.net.impl.messages;

import bgu.spl.net.impl.BGRSServer.ConnectionHandler;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.Message;

public class UNREGISTER implements Message {
    final int opcode=10;
    private int courseNum;

    public UNREGISTER(String substring){
        courseNum=Integer.parseInt(substring);
    }
    @Override
    public Message process(ConnectionHandler handler) {
        Database db=Database.getInstance();
        if(db.isLoggedIn(handler)!=1 || !db.isEnrolled(handler, courseNum))
            return new ERR(opcode);
        else{
            db.unregister(handler,courseNum);
            return new ACK(opcode);
        }

    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
