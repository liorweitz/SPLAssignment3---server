package bgu.spl.net.impl.messages;

import bgu.spl.net.impl.BGRSServer.ConnectionHandler;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.Message;
import java.util.Scanner;

public class STUDENTREG implements Message {
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

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
