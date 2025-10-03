package com.pm.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * gRPC service implementation for handling billing-related operations.
 * This service extends the generated gRPC base class (BillingServiceImplBase)
 * and provides concrete implementations for RPC methods defined in the proto file.
 */
@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    // Logger for logging request and response details
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    /**
     * Handles the gRPC call to create a new billing account.
     *
     * @param billingRequest    Incoming request object from the client (contains billing details).
     * @param responseObserver  Used to send the response back to the client asynchronously.
     */
    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest, StreamObserver<billing.BillingResponse> responseObserver) {

        // Log the received request for debugging/tracing purposes
        log.info("createBillingAccount request received {}", billingRequest.toString());



        // Build the response object with dummy values for now
        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("12345")   // Hardcoded account ID for demo
                .setStatus("ACTIVE")     // Hardcoded status
                .build();

        // Send the response back to the client
        responseObserver.onNext(response);

        // Mark the response as completed (no more data will be sent)
        responseObserver.onCompleted();
    }
}
