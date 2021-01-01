package bgu.spl.net.impl.messages;

import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.Message;
import bgu.spl.net.srv.MessageWithProcess;

import java.net.SocketAddress;
import java.util.ArrayList;

public class STUDENTSTAT implements MessageWithProcess {
    final int opcode=8;
    private String studentName;

    public STUDENTSTAT(String substring) {
        this.studentName=substring;
    }

    @Override
    public Message process(ConnectionHandler handler) {
        Database db= Database.getInstance();
        if (db.isLoggedIn(handler)!=0 | db.isStudentExist(studentName))
            return new ERR(opcode);
        else{
            StringBuilder sb=new StringBuilder();
            ArrayList<Integer> enrolledCourses=db.sortCourseNumList(db.getEnrolledCourses(studentName));
            sb.append("Studnt: "+studentName);
            sb.append("|");
            sb.append(enrolledCourses.toString());
            return new Data(sb.toString());
        }
    }
}
