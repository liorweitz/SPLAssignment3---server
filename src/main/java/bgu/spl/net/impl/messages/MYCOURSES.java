package bgu.spl.net.impl.messages;

import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.Message;
import bgu.spl.net.srv.MessageWithProcess;

import java.net.SocketAddress;

public class MYCOURSES implements MessageWithProcess {
    final int opcode=11;

    public MYCOURSES(String substring) {
    }

    @Override
    public Message process(ConnectionHandler handler) {
        Database db=Database.getInstance();
        if (db.isLoggedIn(handler)!=1)
            return new ERR(opcode);
        else{
            return new Data(db.getEnrolledCourses(handler).toString());
        }
    }
}
