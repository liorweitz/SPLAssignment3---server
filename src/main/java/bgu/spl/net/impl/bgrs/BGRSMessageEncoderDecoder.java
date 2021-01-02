package bgu.spl.net.impl.bgrs;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.messages.*;
import bgu.spl.net.impl.BGRSServer.Message;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BGRSMessageEncoderDecoder implements MessageEncoderDecoder<Message> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
//    private Callable<Message>[] buildMap=new Callable[14];
//    String substring;
//
//    public void Initialize(){
//        buildMap[1]=(substring)->new ADMINGREG(substring);
//    }

    @Override
    public String decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (nextByte == '\n') {
            return popString();
        }

        pushByte(nextByte);
        return null; //not a line yet
    }

    @Override
    public byte[] encode(Message message) {
        return (message.encode()); //uses utf8 by default
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    private String popString() {
        //notice that we explicitly requesting that the string will be decoded from UTF-8
        //this is not actually required as it is the default encoding in java.
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        len = 0;
        return result;
    }

    public Message decode(ByteBuffer buf){
        while (buf.hasRemaining()) {
            String message = decodeNextByte(buf.get());
            if (message != null) {
                return analyze(message);
            }
        }
        return null;
    }

    public Message analyze(String message) {
        String substring=message.substring(2,message.length());
        if (message.charAt(0)=='0'){
            switch (message.charAt(1)) {
                case '1':
                    return new ADMINGREG(substring);
                case '2':
                    return new STUDENTREG(substring);
                case '3':
                    return new LOGIN(substring);
                case '4':
                    return new LOGOUT(substring);
                case '5':
                    return new COURSEREG(substring);
                case '6':
                    return new KDAMCHECK(substring);
                case '7':
                    return new COURSESTAT(substring);
                case '8':
                    return new STUDENTSTAT(substring);
                case '9':
                    return new ISREGISTERED(substring);
            }
        }
        else if(message.charAt(0)=='1'){
            switch (message.charAt(1)) {
                case '1':
                    return new MYCOURSES(substring);
            }
        }
        return null;
    }
}
