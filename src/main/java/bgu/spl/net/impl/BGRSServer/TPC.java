package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessagingProtocol;

import java.util.function.Supplier;

public class TPC extends BaseServer{
    public TPC(int port, Supplier<MessagingProtocol> protocolFactory, Supplier<MessageEncoderDecoder> encderDecoderFactory){
        super(port, protocolFactory,encderDecoderFactory);
    }

    @Override
    protected void execute(BlockingConnectionHandler handler) {
        new Thread(handler).start();
    }
}
