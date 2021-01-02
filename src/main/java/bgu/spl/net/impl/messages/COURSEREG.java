package bgu.spl.net.impl.messages;

import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.Message;

public class COURSEREG implements Message {
    final int opcode=5;
    private int courseNum;

    public COURSEREG(String substring) {
        courseNum=Integer.parseInt(substring);
    }

    @Override
    public Message process(ConnectionHandler handler) {
        Database db= Database.getInstance();
        if((db.checkCourseExistance(courseNum)) || (!db.checkKdam(handler,courseNum)
                | db.isLoggedIn(handler)!=1 | db.checkPlace(courseNum)))
            return new ERR(opcode);
        db.enrollToCourse(handler,courseNum);
        return new ACK(opcode);
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
