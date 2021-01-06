package bgu.spl.net.impl.messages;

import bgu.spl.net.impl.BGRSServer.ConnectionHandler;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.Message;
import java.util.ArrayList;

public class ISREGISTERED implements Message {
    final int opcode=9;
    private int courseNum;

    public ISREGISTERED(String substring) {
        this.courseNum=Integer.parseInt(substring);
    }

    @Override
    public Message process(ConnectionHandler handler) {
        Database db=Database.getInstance();
        if (db.isLoggedIn(handler)!=1)
            return new ERR(opcode);
        else{
            ArrayList<Integer> enrolledCourses=db.getEnrolledCourses(handler);
            if (enrolledCourses.contains(courseNum))

                return new Data("ACK "+String.valueOf(opcode)+"|REGISTERED");
            else
                return new Data("ACK "+String.valueOf(opcode)+"|NOT RGISTERED");
        }
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
