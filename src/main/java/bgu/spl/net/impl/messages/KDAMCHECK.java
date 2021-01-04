package bgu.spl.net.impl.messages;

import bgu.spl.net.impl.BGRSServer.ConnectionHandler;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.Message;

public class KDAMCHECK implements Message {
    final int opcode=6;
    private int courseNum;

    public KDAMCHECK(String substring) {
        this.courseNum=Integer.parseInt(substring);
    }

    @Override
    public Message process(ConnectionHandler handler) {
        System.out.println("kdam");
        Database db=Database.getInstance();
        if (!db.checkCourseExistance(courseNum))
            return new ERR(opcode);
        return new Data("ACK "+String.valueOf(opcode)+"|"+db.getKdam(courseNum).toString());
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
