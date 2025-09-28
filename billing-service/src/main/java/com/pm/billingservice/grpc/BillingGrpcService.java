package com.pm.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    @Override
    public void createBillingAccount(BillingRequest request, StreamObserver<BillingResponse> responseObserver) {
        try {
            // logic for patient billing account creation
            String patientId = request.getPatientId();
            double amount = request.getAmount();

            // In real life, save billing info to DB here
            System.out.println("Creating billing account for Patient ID: " + patientId + " with amount: " + amount);

            // Build response
            BillingResponse response = BillingResponse.newBuilder()
                    .setStatus("SUCCESS")
                    .setMessage("Billing account created for patient " + patientId)
                    .setPatientId(patientId)
                    .setAmount(amount)
                    .build();

            // Send response
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Handle error
            responseObserver.onError(e);
        }
    }
}
