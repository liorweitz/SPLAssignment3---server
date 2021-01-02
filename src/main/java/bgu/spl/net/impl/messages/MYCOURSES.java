package bgu.spl.net.impl.messages;

import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.Message;

public class MYCOURSES implements Message {
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

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
