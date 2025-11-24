# Notification Hub 🔔

A production-ready Event-Driven Notification System built with **Spring Boot** and **Apache Kafka**.

## 🚀 Features

- **Event-Driven Architecture**: Async processing using Kafka
- **Multi-Channel Support**: Email, SMS, Push Notifications
- **Resilience**: Automatic retries with exponential backoff
- **Dead Letter Queue (DLQ)**: Handling of failed messages
- **Template System**: Dynamic content with variable substitution
- **Tracking**: Real-time status updates (PENDING -> SENT/FAILED)

## 🛠 Tech Stack

- **Java 17** & **Spring Boot 3**
- **Apache Kafka** (Messaging)
- **PostgreSQL** (Persistence)
- **Redis** (Caching - *configured*)
- **Docker Compose** (Infrastructure)

## 🏃‍♂️ Getting Started

### 1. Start Infrastructure
```bash
docker-compose up -d
```
This starts Zookeeper, Kafka, PostgreSQL, Redis, and Kafdrop.

### 2. Run Application
```bash
mvn spring-boot:run
```

### 3. Access Tools
- **Kafdrop (Kafka UI)**: [http://localhost:9000](http://localhost:9000)
- **H2 Console** (if enabled): [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

## 🧪 Testing with API

### Create a Template
```bash
curl -X POST http://localhost:8080/api/templates \
  -H "Content-Type: application/json" \
  -d '{
    "code": "WELCOME_EMAIL",
    "subjectTemplate": "Welcome to Notification Hub, {name}!",
    "contentTemplate": "Hi {name}, thanks for joining us from {city}.",
    "channel": "EMAIL"
  }'
```

### Send Notification (Direct)
```bash
curl -X POST http://localhost:8080/api/notifications/send \
  -H "Content-Type: application/json" \
  -d '{
    "recipient": "user@example.com",
    "subject": "Direct Message",
    "content": "Hello World",
    "channel": "EMAIL"
  }'
```

### Send Notification (Using Template)
```bash
curl -X POST http://localhost:8080/api/notifications/send \
  -H "Content-Type: application/json" \
  -d '{
    "recipient": "user@example.com",
    "templateCode": "WELCOME_EMAIL",
    "variables": {
      "name": "Shivam",
      "city": "Bangalore"
    }
  }'
```

### Check Status
```bash
curl http://localhost:8080/api/notifications/1
```

## 🔄 Kafka Flow

1. **API** receives request -> Saves to DB (PENDING) -> Publishes to `notification-events`
2. **Listener** consumes event -> Routes to Channel Service (Email/SMS/Push)
3. **Success**: Updates DB to SENT
4. **Failure**: Retries 3 times (with backoff) -> If still fails, moves to DLQ

## 📊 Monitoring

Check `notification-events` topic in Kafdrop to see the message flow.
