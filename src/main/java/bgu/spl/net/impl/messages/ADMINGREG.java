package bgu.spl.net.impl.messages;

import bgu.spl.net.impl.BGRSServer.ConnectionHandler;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.Message;
import java.util.Scanner;

public class ADMINGREG implements Message {
    final int opcode=1;
    private String userName;
    private String pwd;

    public ADMINGREG(String substring) {
        Scanner sc=new Scanner(substring);
        sc.useDelimiter("\0");
        userName=sc.next();
        pwd=sc.next();
    }

    @Override
    public Message process(ConnectionHandler handler) {
        Database db= Database.getInstance();
        if (db.isLoggedIn(handler)!=-1) {
            //handler logged in.
            System.out.println("handler logged in");
            return new ERR(opcode);
        }
        //
        if (db.isAdminExist(userName) | db.isStudentExist(userName)) {
            // user name taken.
            System.out.println("cant register");
            return new ERR(opcode);
        }

        else {
            db.adminRegister(userName, pwd, handler);
            System.out.println("registered");
            return new ACK(opcode);
        }
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
