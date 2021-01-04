package bgu.spl.net.impl.messages;

import bgu.spl.net.impl.BGRSServer.ConnectionHandler;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.Message;

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
            return new Data("ACK "+String.valueOf(opcode)+"|"+db.getEnrolledCourses(handler).toString());
        }
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
