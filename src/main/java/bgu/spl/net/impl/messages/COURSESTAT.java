package bgu.spl.net.impl.messages;

import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.Message;
import bgu.spl.net.srv.MessageWithProcess;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;

public class COURSESTAT implements MessageWithProcess {
    final int opcode=7;
    private int courseNum;

    public COURSESTAT(String substring) {
        this.courseNum=Integer.parseInt(substring);
    }

    @Override
    public Message process(ConnectionHandler handler) {
        Database db= Database.getInstance();
        if (db.isLoggedIn(handler)!=0)
            return new ERR(opcode);
        else{
            StringBuilder sb=new StringBuilder();
            Database.Course course=db.getCourse(courseNum);
            ArrayList<String> enrolledStudents=course.getEnrolledStudents();
            Collections.sort(enrolledStudents);
            sb.append("course: "+"("+courseNum+")"+" "+course.getCourseName());
            sb.append("|");
            sb.append("Seats Available: "+course.getOccupiedPlaces()+"/"+course.getNumOfMaxStudents());
            sb.append("|");
            sb.append("Students Registered: "+enrolledStudents.toString());
            return new Data(sb.toString());
        }
    }
}
