# Patient Management - Project Notes

## Architecture Overview

Two independent Spring Boot microservices:

- **Patient Service** — REST API on port `4000`, backed by PostgreSQL (H2 for dev)
- **Billing Service** — gRPC server on port `9001` (HTTP port `4001`)

The services are currently isolated — no inter-service calls are implemented yet.

---

## Project Structure

```
patient-management/
├── patient-service/
│   └── src/main/java/com/pm/patientservice/
│       ├── controller/        # REST endpoints
│       ├── service/           # Business logic
│       ├── repository/        # JPA data access
│       ├── model/             # JPA entity (Patient)
│       ├── dto/               # Request & Response DTOs
│       ├── mapper/            # Entity <-> DTO conversion
│       └── exception/         # Custom exceptions + GlobalExceptionHandler
│
├── billing-service/
│   └── src/main/
│       ├── java/com/pm/billingservice/grpc/   # gRPC service impl
│       └── proto/billing_service.proto         # Protobuf definition
│
├── api-requests/              # .http files for Patient Service
└── grpc-requests/             # .http files for Billing Service
```

---

## Patient Service Flow

### REST Request Lifecycle

```
HTTP Client
    ↓
PatientController          — routes request, triggers @Valid input validation
    ↓
GlobalExceptionHandler     — intercepts validation/business errors, returns 400s
    ↓
PatientService             — business logic (email uniqueness check, orchestration)
    ↓
PatientRepository          — JPA CRUD + existsByEmail() query
    ↓
Database (patient table)
    ↓
PatientMapper              — entity → PatientResponseDTO
    ↓
HTTP Response (JSON)
```

### Create Patient — Step by Step

1. `POST /patients` received with JSON body
2. `@Valid` + `CreatePatientValidationGroup` validates all fields (registeredDate required only on create)
3. `PatientService.createPatient()` checks `existsByEmail()` — throws `EmailAlreadyExistsException` if duplicate
4. `PatientMapper.toModel()` converts `PatientRequestDTO` → `Patient` entity (String → LocalDate)
5. `PatientRepository.save()` persists to DB (UUID auto-generated)
6. `PatientMapper.toDTO()` converts saved entity → `PatientResponseDTO` (LocalDate → String)
7. Response returned as JSON

### REST Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/patients` | Fetch all patients |
| POST | `/patients` | Create new patient |
| PUT | `/patients/{id}` | Update patient |
| DELETE | `/patients/{id}` | Delete patient |

---

## Billing Service Flow

### gRPC Request Lifecycle

```
gRPC Client
    ↓
BillingGrpcService         — extends BillingServiceImplBase (generated from proto)
    ↓
Business logic
    ↓
BillingResponse (protobuf)
    ↓
gRPC Response
```

### Proto Definition

```protobuf
service BillingService {
  rpc CreateBillingAccount (BillingRequest) returns (BillingResponse);
}

message BillingRequest {
  string patientId = 1;
  string name     = 2;
  string email    = 3;
}

message BillingResponse {
  string accountId = 1;
  string status    = 2;
}
```

---

## Data Model

### Patient Entity

| Column | Type | Constraints |
|--------|------|-------------|
| id | UUID | PK, auto-generated |
| name | String | NOT NULL |
| email | String | NOT NULL, UNIQUE |
| address | String | NOT NULL |
| dateOfBirth | LocalDate | NOT NULL |
| registeredDate | LocalDate | NOT NULL |

---

## Key Classes

| Class | Role |
|-------|------|
| `PatientController` | REST layer — maps HTTP to service calls |
| `PatientService` | Business rules — email uniqueness, orchestration |
| `PatientRepository` | JPA interface — CRUD + `existsByEmail()` |
| `PatientMapper` | Converts entity ↔ DTOs, handles type coercions |
| `PatientRequestDTO` | Inbound payload with Jakarta validation annotations |
| `PatientResponseDTO` | Outbound payload — no internal details exposed |
| `GlobalExceptionHandler` | `@ControllerAdvice` — standardizes all error responses |
| `EmailAlreadyExistsException` | Thrown when duplicate email detected |
| `PatientNotFoundException` | Thrown when patient ID not found |
| `CreatePatientValidationGroup` | Marker interface for create-only field validation |
| `BillingGrpcService` | gRPC impl — handles `CreateBillingAccount` RPC |

---

## Configuration

| Service | Port | DB |
|---------|------|----|
| patient-service | `4000` | PostgreSQL (H2 for dev) |
| billing-service (HTTP) | `4001` | — |
| billing-service (gRPC) | `9001` | — |
