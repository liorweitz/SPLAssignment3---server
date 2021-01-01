package bgu.spl.net.impl.messages;

import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.Message;
import bgu.spl.net.srv.MessageWithProcess;

import java.net.SocketAddress;
import java.util.Scanner;

public class LOGIN implements MessageWithProcess {
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
                Database.User admin = db.getAdmin(userName);
                if (pwd == admin.getPwd()) {
                    db.logIn(handler, userName);
                    System.out.println("logged in");
                    return new ACK(opcode);
                }
            } else if (db.isStudentExist(userName)) {
                Database.User student = db.getStudent(userName);
                if (pwd == student.getPwd()) {
                    db.logIn(handler, userName);
                    System.out.println("logged in");
                    return new ACK(opcode);
                }
            }
        }
        System.out.println("not logged in");
        return new ERR(opcode);
    }
}
