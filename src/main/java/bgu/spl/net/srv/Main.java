package bgu.spl.net.srv;

import bgu.spl.net.impl.bgrs.BGRSMessageEncoderDecoder;
import bgu.spl.net.impl.bgrs.BGRSProtocol;
import bgu.spl.net.impl.messages.ADMINGREG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Integer> list=new ArrayList<>();
        String se=list.toString();
        System.out.println(list);
        System.out.println(list.toString());
        Database db=Database.getInstance();

//        Database db=Database.getInstance();
//        db.initialize("//home//spl211//IdeaProjects//Server//src//Courses");
//        Server server=new Reactor(3, Integer.decode(args[0]).intValue(),()->new BGRSProtocol(),()->new BGRSMessageEncoderDecoder());
//        server.serve();
    }
}
