package com.demo.demoserver;


import grpc.demo.login.GreeterGrpc;
import grpc.demo.login.RegisterReply;
import grpc.demo.login.RegisterRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;


import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by xjl on 2017/10/24.
 */
public class demoClientTest {
    private static final Logger logger = Logger.getLogger(demoClientTest.class.getName());
    private final static int PORT=50055;
    private final static String HOST="127.0.0.1";
    private final ManagedChannel mChannel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private final String Name="xjl";

   public demoClientTest(int port, String host){
       mChannel=ManagedChannelBuilder.forAddress(host,port).usePlaintext(true).build();
       blockingStub=GreeterGrpc.newBlockingStub(mChannel);

   }

    public void shutdown() throws InterruptedException {
        mChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /** Say hello to server. */
    public void greet(String name) {
        logger.info("Will try to greet " + name + " ...");
        RegisterRequest request = RegisterRequest.newBuilder().build();
        RegisterReply response;
        try {
            response = blockingStub.register(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greeting: " + response.getMessage());
    }
    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        demoClientTest client = new demoClientTest(PORT, HOST);
        try {

            String user = "world";
            if (args.length > 0) {
                user = args[0];
            }
            client.greet(user);
        } finally {
            client.shutdown();
        }
    }

}
