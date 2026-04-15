# E-Commerce Microservices - Build and Run Guide

## Overview
This project contains 5 microservices for an E-Commerce application:
1. **Product Service** - Manages product information
2. **Order Service** - Handles order processing
3. **Payment Service** - Processes payments
4. **Inventory Service** - Manages inventory
5. **Notification Service** - Sends notifications

## Prerequisites
- Java 21 (all services require Java 21)
- Maven 3.8.0 or higher
- MySQL 8.0+ running on localhost:3306
- Kafka running on localhost:9092
- Git (optional)

## Database Setup
All services use the same database: `e_commerce_db`

```sql
CREATE DATABASE IF NOT EXISTS e_commerce_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Credentials:
- Username: `root`
- Password: `Subham@1234`

The tables will be auto-created by Hibernate (ddl-auto: update).

## Building All Services

### Option 1: Build All Services (Individual)
Navigate to each service directory and run:
```bash
cd inventory-service
mvnw.cmd clean install -DskipTests

cd ../notification-service
mvnw.cmd clean install -DskipTests

cd ../order-service
mvnw.cmd clean install -DskipTests

cd ../payment-service
mvnw.cmd clean install -DskipTests

cd ../product-service
mvnw.cmd clean install -DskipTests
```

### Option 2: Quick Build Script (Windows)
Run the following commands in sequence from the project root:

For Windows Command Prompt:
```batch
cd inventory-service && mvnw.cmd clean install -DskipTests && cd ..
cd notification-service && mvnw.cmd clean install -DskipTests && cd ..
cd order-service && mvnw.cmd clean install -DskipTests && cd ..
cd payment-service && mvnw.cmd clean install -DskipTests && cd ..
cd product-service && mvnw.cmd clean install -DskipTests && cd ..
```

## Running Individual Services

### Product Service
```bash
cd product-service
mvnw.cmd spring-boot:run
# Service runs on: http://localhost:8181
```

### Payment Service
```bash
cd payment-service
mvnw.cmd spring-boot:run
# Service runs on: http://localhost:8122
```

### Order Service
```bash
cd order-service
mvnw.cmd spring-boot:run
# Service runs on: http://localhost:8123
```

### Notification Service
```bash
cd notification-service
mvnw.cmd spring-boot:run
# Service runs on: http://localhost:8124
```

### Inventory Service
```bash
cd inventory-service
mvnw.cmd spring-boot:run
# Service runs on: http://localhost:8200
```

## Service Ports
| Service | Port |
|---------|------|
| Product Service | 8181 |
| Payment Service | 8122 |
| Order Service | 8123 |
| Notification Service | 8124 |
| Inventory Service | 8200 |

## Health Check Endpoints
All services expose a health check endpoint:
```
GET /actuator/health
```

## API Endpoints

### Product Service
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products/add` - Add new product
- `GET /api/products` - Get all products

### Order Service
- `GET /api/order-events/{id}` - Get order by ID
- `POST /api/order-events` - Create new order
- `DELETE /api/order-events/{id}` - Delete order

### Payment Service
- `GET /api/payments/{id}` - Get payment by ID
- `GET /api/payments/order/{orderId}` - Get payments for order

### Inventory Service
- `GET /api/inventory/{productId}` - Get inventory for product
- `PUT /api/inventory/{productId}` - Update inventory

### Notification Service
- `GET /api/notifications/{id}` - Get notification by ID
- `GET /api/notifications` - Get all notifications

## Troubleshooting

### Build Fails
1. Ensure Maven is properly installed: `mvn -v`
2. Clear Maven cache: `mvn clean`
3. Check Java version: `java -version` (should be 21)
4. Rebuild: `mvn clean install -DskipTests`

### Service Won't Start
1. Check if MySQL is running: `mysql -u root -p`
2. Check if Kafka is running on localhost:9092
3. Check port availability (no other service using the same port)
4. Check application.yaml configuration

### Database Connection Issues
1. Verify MySQL credentials in application.yaml
2. Ensure database `e_commerce_db` exists
3. Check MySQL is listening on port 3306: `netstat -an | findstr 3306`

### Kafka Issues
1. Verify Kafka is running
2. Check bootstrap-servers in application.yaml (should be localhost:9092)
3. Ensure Kafka topics are created or auto-create is enabled

## Fixed Issues
The following issues have been resolved:
- ✓ Removed non-existent test dependencies (spring-boot-starter-data-jpa-test, spring-boot-starter-webmvc-test)
- ✓ Updated Spring Boot versions to 4.0.5 for all services
- ✓ Standardized Java version to 21 across all services
- ✓ Fixed JPA configuration issues
- ✓ Fixed YAML configuration syntax errors
- ✓ Added spring-boot-starter-test for all services

## Technology Stack
- **Framework**: Spring Boot 4.0.5
- **Java Version**: 21
- **Build Tool**: Maven
- **Database**: MySQL 8.0+
- **Message Queue**: Apache Kafka
- **ORM**: Hibernate JPA
- **Validation**: Jakarta Validation
- **Logging**: SLF4J with Logback

## Next Steps
1. Build all services: See "Building All Services" section above
2. Ensure MySQL and Kafka are running
3. Run each service individually
4. Test API endpoints using Postman or curl

## Support
For issues or questions, check the logs in the `logs/` directory of each service.
