package bgu.spl.net.impl.bgrs;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.messages.*;
import bgu.spl.net.impl.BGRSServer.Message;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BGRSMessageEncoderDecoder implements MessageEncoderDecoder<Message> {

    private byte[] bytes =null;
    private int len = 0;
    private ByteBuffer opcodeBuffer=ByteBuffer.allocate(2);
//    private byte[] messageBuffer=null;
    short opcode;
    int counter=0;
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

        if (bytes==null){
            opcodeBuffer.put(nextByte);
            if (!opcodeBuffer.hasRemaining()){
                opcodeBuffer.flip();
                opcode=opcodeBuffer.getShort();
                bytes=new byte[1024];
            }
        }
        else {
            if (opcode==4 | opcode==11){
                return popString();
            }
            else if (opcode==8){
                pushByte(nextByte);
                if (nextByte=='\0'){
                    return popString();
                }
            }
            else if (opcode==5 | opcode==6 | opcode==7 | opcode==9 | opcode==10){
                pushByte(nextByte);
                counter++;
                if (counter==2){
                    return popString();
                }

            }
            else if (opcode == 1 | opcode == 2 | opcode == 3){

                if (nextByte=='\0'){
                    counter++;
                }
                pushByte(nextByte);
                if (counter==2){
                    return popString();
                }
            }
        }
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
        bytes=null;
        opcodeBuffer.clear();
        counter=0;
        len = 0;
        return result;
    }

    public Message decode(ByteBuffer buf){

        while (buf.hasRemaining()) {
            String message = decodeNextByte(buf.get());
            if (message != null) {
                buf.position(buf.limit());
                System.out.println(message);
                return analyze(message);
            }
        }
        return null;
    }

    public Message analyze(String message) {
         switch (opcode) {
            case 1:
                return new ADMINGREG(message);
            case 2:
                return new STUDENTREG(message);
            case 3:
                return new LOGIN(message);
            case 4:
                return new LOGOUT(message);
            case 5:
                return new COURSEREG(message);
            case 6:
                return new KDAMCHECK(message);
            case 7:
                return new COURSESTAT(message);
            case 8:
                return new STUDENTSTAT(message);
            case 9:
                return new ISREGISTERED(message);
            case 10:
                return new UNREGISTER(message);
            case 11:
                return new MYCOURSES(message);
        }
    return null;
    }
}
