package com.demo.demoserver;


    /*
 * Copyright 2015, gRPC Authors All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.demo.Dao.DaoImp.replyDaoImp;

import grpc.demo.login.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */

/**
     * Created by xjl on 2017/10/23.
     */

public class demoServer {
    private static final Logger logger = Logger.getLogger(demoServer.class.getName());

    /* The port on which the server should run */
    private int port = 50055;
    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                demoServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the demo library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final demoServer server = new demoServer();
        server.start();
        server.blockUntilShutdown();
    }

    private class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        public void register(RegisterRequest request, StreamObserver<RegisterReply> responseObserver) {
            responseObserver.onNext(new replyDaoImp().register(request));
            responseObserver.onCompleted();
        }

        public void login(LoginRequest request, StreamObserver<LoginReply> responseObserver) {
            System.out.print("get requrst from Client");
            responseObserver.onNext(new replyDaoImp().login(request));
            responseObserver.onCompleted();
        }

        public void searchFriend(SearchFriendRequest request, StreamObserver<SearchFriendReply> responseObserver) {
            System.out.print("get requrst from Client");
            responseObserver.onNext(new replyDaoImp().searchFriend(request));
            responseObserver.onCompleted();
        }
    }

}



