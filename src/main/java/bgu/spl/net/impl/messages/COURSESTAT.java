package bgu.spl.net.impl.messages;

import bgu.spl.net.impl.BGRSServer.ConnectionHandler;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.Message;
import java.util.ArrayList;
import java.util.Collections;

public class COURSESTAT implements Message {
    final int opcode=7;
    private int courseNum;

    public COURSESTAT(String substring) {
        this.courseNum=Integer.parseInt(substring);
    }

    @Override
    public Message process(ConnectionHandler handler) {
        Database db= Database.getInstance();
        if (db.isLoggedIn(handler)!=0 | !db.checkCourseExistance(courseNum))
            return new ERR(opcode);
        else{
            StringBuilder sb=new StringBuilder();
            ArrayList<String> enrolledStudents=db.getEnrolledStudents(courseNum);
            Collections.sort(enrolledStudents);
            sb.append("course: "+"("+courseNum+")"+" "+db.getCourseName(courseNum));
            sb.append("\n");
            sb.append("Seats Available: "+db.getOccupiedPlaces(courseNum)+"/"+db.getNumOfMaxStudents(courseNum));
            sb.append("\n");
            sb.append("Students Registered: "+enrolledStudents.toString());
            return new ACK(opcode,sb.toString());
        }
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
