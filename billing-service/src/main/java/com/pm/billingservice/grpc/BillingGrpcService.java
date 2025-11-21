package com.pm.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    /**
     
     *
     * @param billingRequest    Incoming request object from the client (contains billing details).
     * @param responseObserver  is Used to send response back to the client asynchronously
     */
    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest, StreamObserver<billing.BillingResponse> responseObserver) {

        // Log the received request for debugging/tracing purposes
        log.info("createBillingAccount request received {}", billingRequest.toString());



        // Build the response object with dummy values for now
        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("12345")   // Hardcoded account ID for demo
                .setStatus("ACTIVE")     
                .build();

        // Send the response back to the client
        responseObserver.onNext(response);

        // Mark the response as completed
        responseObserver.onCompleted();
    }
}
