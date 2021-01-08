package bgu.spl.net.impl.messages;

import bgu.spl.net.impl.BGRSServer.ConnectionHandler;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.Message;

public class LOGOUT implements Message {
    final int opcode=4;
    public LOGOUT(String substring) {
    }

    @Override
    public Message process(ConnectionHandler handler) {
        Database db=Database.getInstance();

        if (db.isLoggedIn(handler)!=-1){
            db.logOut(handler);
            return new ACK(opcode);
        }
        else{
            return new ERR(opcode);

        }

    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
