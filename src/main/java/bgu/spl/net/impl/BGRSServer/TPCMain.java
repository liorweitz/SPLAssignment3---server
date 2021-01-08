package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.bgrs.BGRSMessageEncoderDecoder;
import bgu.spl.net.impl.bgrs.BGRSProtocol;

import java.io.IOException;

public class TPCMain {
    public static void main (String[] args) throws IOException {
        Database db=Database.getInstance();
        db.initialize("Server//Courses.txt");
        Server server=new TPC(Integer.decode(args[0]).intValue(),()->new BGRSProtocol(),()->new BGRSMessageEncoderDecoder());
        server.serve();
    }
}
