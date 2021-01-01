package bgu.spl.net.impl.messages;

import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.Message;
import bgu.spl.net.srv.MessageWithProcess;

import java.net.SocketAddress;
import java.util.ArrayList;

public class UNREGISTER implements MessageWithProcess {
    final int opcode=10;
    private int courseNum;

    public UNREGISTER(String substring){
        courseNum=Integer.parseInt(substring);
    }
    @Override
    public Message process(ConnectionHandler handler) {
        Database db=Database.getInstance();
        if(db.isLoggedIn(handler)!=1 || db.isEnrolled(handler, courseNum))
            return new ERR(opcode);
        else{
            db.unregister(handler,courseNum);
            return new ACK(opcode);
        }

    }
}
