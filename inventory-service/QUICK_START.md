# Quick Reference: Build & Run All Services

## 🚀 Quick Start (5 Minutes)

### Prerequisites Check
```bash
# Check Java version (must be 21)
java -version

# Check Maven is installed
mvn -version

# Ensure MySQL is running on localhost:3306
# Ensure Kafka is running on localhost:9092
```

### Build All Services (One Command at a Time)
```batch
cd inventory-service && mvnw.cmd clean install -DskipTests && cd ..
cd notification-service && mvnw.cmd clean install -DskipTests && cd ..
cd order-service && mvnw.cmd clean install -DskipTests && cd ..
cd payment-service && mvnw.cmd clean install -DskipTests && cd ..
cd product-service && mvnw.cmd clean install -DskipTests && cd ..
```

**Expected**: `[INFO] BUILD SUCCESS` for each service

---

## 🎯 Run Services (Use Separate Terminal Windows)

### Terminal 1: Product Service
```bash
cd product-service
mvnw.cmd spring-boot:run
```
✅ Runs on: http://localhost:8181

### Terminal 2: Payment Service
```bash
cd payment-service
mvnw.cmd spring-boot:run
```
✅ Runs on: http://localhost:8122

### Terminal 3: Order Service
```bash
cd order-service
mvnw.cmd spring-boot:run
```
✅ Runs on: http://localhost:8123

### Terminal 4: Notification Service
```bash
cd notification-service
mvnw.cmd spring-boot:run
```
✅ Runs on: http://localhost:8124

### Terminal 5: Inventory Service
```bash
cd inventory-service
mvnw.cmd spring-boot:run
```
✅ Runs on: http://localhost:8200

---

## ✅ Verify All Services Are Running

```bash
# Check Product Service
curl http://localhost:8181/actuator/health

# Check Payment Service
curl http://localhost:8122/actuator/health

# Check Order Service
curl http://localhost:8123/actuator/health

# Check Notification Service
curl http://localhost:8124/actuator/health

# Check Inventory Service
curl http://localhost:8200/actuator/health
```

**Expected Response**: `{"status":"UP"}`

---

## 📋 Service Details

| Service | Port | Context |
|---------|------|---------|
| Product | 8181 | `/api/products` |
| Payment | 8122 | `/api/payments` |
| Order | 8123 | `/api/order-events` |
| Notification | 8124 | `/api/notifications` |
| Inventory | 8200 | `/api/inventory` |

---

## 🔧 Issues Fixed

✅ Invalid test dependencies removed  
✅ Spring Boot version standardized to 4.0.5  
✅ Java version standardized to 21  
✅ YAML configuration fixed  
✅ All services ready to build and run

---

## 📚 Documentation

- **BUILD_AND_RUN_GUIDE.md** - Comprehensive guide
- **BUILD_RUN_SUMMARY.md** - Complete summary
- **DETAILED_CHANGES.md** - All changes documented

---

## 🆘 Troubleshooting

**Build fails?**
```bash
mvn clean
mvn install -X -DskipTests
```

**Service won't start?**
- Check Java: `java -version` (must be 21)
- Check MySQL: `mysql -u root -p Subham@1234`
- Check Kafka: Running on localhost:9092

**Port already in use?**
- Check what's using the port: `netstat -ano | findstr :8181`
- Kill the process or change port in application.yaml

---

## ✨ Status: ALL READY TO GO! ✨
