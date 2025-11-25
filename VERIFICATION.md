# Notification Hub Verification Guide

This guide details how to verify the **Notification Hub** system end-to-end.

## 1. Start Infrastructure
```bash
docker-compose up -d
mvn spring-boot:run
```
*Ensures Kafka (9093), Zookeeper, PostgreSQL (5433), and Redis are running.*

## 2. Create a Template
```bash
curl -X POST http://localhost:8080/api/templates \
-H "Content-Type: application/json" \
-d '{
  "code": "WELCOME_EMAIL",
  "subjectTemplate": "Welcome, {name}!",
  "contentTemplate": "Hello {name}, welcome to our platform!",
  "channel": "EMAIL"
}'
```

## 3. Send a Notification
```bash
curl -X POST http://localhost:8080/api/notifications/send \
-H "Content-Type: application/json" \
-d '{
  "recipient": "user@example.com",
  "templateCode": "WELCOME_EMAIL",
  "variables": {
    "name": "Shivam"
  },
  "channel": "EMAIL"
}'
```

## 4. Check Status
```bash
curl http://localhost:8080/api/notifications/1
```

## 5. Expected Results
- **Status**: Should transition from `PENDING` -> `SENT`.
- **Logs**: Check application logs for "Sending EMAIL..." and "EMAIL sent successfully".
- **Kafka**: Check `notification-events` topic in Kafdrop (http://localhost:9000).

## Troubleshooting
- **Port Conflicts**: If startup fails, check if ports 8080, 9093, or 5433 are in use.
- **Kafka Connection**: If logs show connection refused, ensure `KAFKA_ADVERTISED_LISTENERS` matches your Docker setup.
