package bgu.spl.net.impl.messages;

import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.Message;
import bgu.spl.net.srv.MessageWithProcess;

import java.net.SocketAddress;
import java.util.Scanner;

public class STUDENTREG implements MessageWithProcess {
    final int opcode=2;
    private String userName;
    private String pwd;

    public STUDENTREG(String substring){
        Scanner sc=new Scanner(substring);
        sc.useDelimiter("0");
        userName=sc.next();
        pwd=sc.next();
    }

    @Override
    public Message process(ConnectionHandler handler) {
        Database db=Database.getInstance();
        if (db.isLoggedIn(handler)!=-1){
            //handler is signed in, cant register.
            return new ERR(opcode);
        }
        if (db.isStudentExist(userName) | db.isAdminExist(userName))
            return new ERR(opcode);
        else{
            db.studentRegister(userName,pwd, handler);
            return new ACK(opcode);
        }

    }
}
