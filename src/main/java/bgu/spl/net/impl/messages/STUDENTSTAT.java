package bgu.spl.net.impl.messages;

import bgu.spl.net.impl.BGRSServer.ConnectionHandler;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.Message;
import java.util.ArrayList;

public class STUDENTSTAT implements Message {
    final int opcode=8;
    private String studentName;

    public STUDENTSTAT(String substring) {
        this.studentName=substring.substring(0,substring.length()-1);
    }

    @Override
    public Message process(ConnectionHandler handler) {
        Database db= Database.getInstance();
        if (db.isLoggedIn(handler)!=0 | !db.isStudentExist(studentName))
            return new ERR(opcode);
        else{
            StringBuilder sb=new StringBuilder();
            ArrayList<Integer> enrolledCourses=db.sortCourseNumList(db.getEnrolledCourses(studentName));
            sb.append("Studnt: "+studentName);
            sb.append("\n");
            sb.append(enrolledCourses.toString());
            return new ACK(opcode, sb.toString());
        }
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
