package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.bgrs.BGRSMessageEncoderDecoder;
import bgu.spl.net.impl.bgrs.BGRSProtocol;

import java.io.IOException;

public class ReactorMain {
    public static void main (String[] args) throws IOException {
        Database db=Database.getInstance();
        db.initialize("//home//spl211//IdeaProjects//Server//src//Courses.txt");
        Server server=new Reactor(3, Integer.decode(args[0]).intValue(),()->new BGRSProtocol(),()->new BGRSMessageEncoderDecoder());
        server.serve();
    }
}
