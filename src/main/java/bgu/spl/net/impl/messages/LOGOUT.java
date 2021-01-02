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
            System.out.println("logged out");
            db.logOut(handler);
            return new ACK(opcode);
        }
        else{
            System.out.println("handler isnt logged in");
            return new ERR(opcode);

        }

    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
