package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.bgrs.BGRSMessageEncoderDecoder;
import bgu.spl.net.impl.bgrs.BGRSProtocol;
import bgu.spl.net.impl.messages.ADMINGREG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Main {
    public static void main(String[] args) throws IOException {
        byte[] ba1=("125").getBytes();
        byte[] ba2=(String.valueOf(12)+String.valueOf(5)).getBytes();
//        Database db=Database.getInstance();
//        db.initialize("//home//spl211//IdeaProjects//Server//src//Courses");
//        Server server=new Reactor(3, Integer.decode(args[0]).intValue(),()->new BGRSProtocol(),()->new BGRSMessageEncoderDecoder());
//        server.serve();
    }
}
