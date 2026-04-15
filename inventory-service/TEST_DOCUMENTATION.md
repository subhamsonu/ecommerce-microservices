# Inventory Service - Test Documentation

## Test Classes Created

### 1. **InventoryServiceApplicationTests**
- **Location**: `src/test/java/com/smartmart/inventory_service/InventoryServiceApplicationTests.java`
- **Purpose**: Verifies that the Spring Boot context loads successfully
- **Test**: `contextLoads()` - Ensures all beans are properly configured and injected

### 2. **InventoryServiceImplTest**
- **Location**: `src/test/java/com/smartmart/inventory_service/InventoryServiceImplTest.java`
- **Purpose**: Unit tests for the InventoryServiceImpl class using Mockito
- **Test Cases**:
  - `findByProductId()` - Success and NotFound scenarios
  - `deleteByProductId()` - Success and NotFound scenarios
  - `reduceQuantity()` - Success, InsufficientQuantity, and NotFound scenarios
  - `increaseQuantity()` - Success and NotFound scenarios
  - `saveInventory()` - Success and invalid object scenarios
  - `deleteInventory()` - Success scenario

### 3. **InventoryControllerTest**
- **Location**: `src/test/java/com/smartmart/inventory_service/InventoryControllerTest.java`
- **Purpose**: Unit tests for the InventoryController class
- **Test Cases**: Verifies controller instantiation and basic wiring

### 4. **InventoryValidatorTest**
- **Location**: `src/test/java/com/smartmart/inventory_service/InventoryValidatorTest.java`
- **Purpose**: Unit tests for the InventoryValidator utility class
- **Test Cases**:
  - `validateQuantity()` - Positive, Zero, Null, and Negative scenarios
  - `validateProductId()` - Positive, Zero, Null, and Negative scenarios
  - `validateInventoryId()` - All validation scenarios
  - `validateInventoryExists()` - Existence check
  - `validateSufficientQuantity()` - Sufficient and Insufficient scenarios
  - `validateInventoryObject()` - Full object validation
  - `validateNonNegativeQuantity()` - Non-negative validation
  - `hassufficientQuantity()` - Boolean check method

## How to Run Tests

### Via Maven
```bash
cd inventory-service
mvn clean test
```

### Via IDE (Eclipse)
1. Right-click on the project
2. Select **Run As** → **Maven test**

Or for individual test class:
1. Right-click on the test file
2. Select **Run As** → **JUnit Test**

## Test Coverage

The tests cover:
- ✅ Service layer business logic
- ✅ Input validation
- ✅ Exception handling (InvalidQuantityException, InventoryNotFoundException, InsufficientInventoryException)
- ✅ Repository interaction via mocks
- ✅ Edge cases (null values, invalid IDs, insufficient inventory)

## Dependencies Used

- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework
- **Spring Boot Test** - Spring testing utilities
- **Jackson** - JSON serialization

## Build Instructions

If you encounter import errors after creating/updating test files:

1. **Right-click on project** → **Maven** → **Update Project**
   - Or press `Alt + F5`
   - Check "Force Update of Snapshots/Releases"
   - Click OK

2. **Rebuild project**:
   - `Project` → `Clean...` → Select project → OK
   - Or use Maven: `mvn clean install`

## Notes

- All test classes are in the `com.smartmart.inventory_service` package under `src/test/java`
- Tests use mocking to isolate components and avoid database dependencies
- The validator tests ensure all business rules are properly enforced
- Tests follow the Arrange-Act-Assert pattern for clarity
