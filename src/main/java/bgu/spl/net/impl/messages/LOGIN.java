package bgu.spl.net.impl.messages;

import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.Message;
import java.util.Scanner;

public class LOGIN implements Message {
    final int opcode=3;
    private String userName;
    private String pwd;

    public LOGIN(String substring) {
        Scanner sc=new Scanner(substring);
        sc.useDelimiter("0");
        userName=sc.next();
        pwd=sc.next();
    }

    @Override
    public Message process(ConnectionHandler handler) {
        Database db = Database.getInstance();
        if (db.isLoggedIn(handler)==-1) {
            if (db.isAdminExist(userName)) {
//                Database.User admin = db.getAdmin(userName);
                if (pwd == db.getAdminPwd(userName)) {
                    db.logIn(handler, userName);
                    System.out.println("logged in");
                    return new ACK(opcode);
                }
            } else if (db.isStudentExist(userName)) {
//                Database.User student = db.getStudent(userName);
                if (pwd == db.getStudentPwd(userName)) {
                    db.logIn(handler, userName);
                    System.out.println("logged in");
                    return new ACK(opcode);
                }
            }
        }
        System.out.println("not logged in");
        return new ERR(opcode);
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
