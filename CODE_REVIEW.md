# Code Review: Theater Booking System

## Executive Summary
This document contains a comprehensive code review of the theater booking system. The review identified several critical bugs, code quality issues, and potential improvements.

## Critical Bugs Fixed ✅

### 1. Date Validation Logic Error (Controller.java)
**Severity:** CRITICAL  
**Location:** `Controller.opretBestillingMedPladser`, lines 41-42  
**Issue:** The date validation logic was inverted using incorrect boolean operators. The condition checked if the date was NOT within the valid range, then returned null - but the logic was incorrect.

**Before:**
```java
if (!(forestilling.getStartDato().isBefore(dato) || forestilling.getStartDato().isEqual(dato)) &&
        (forestilling.getSlutDato().isAfter(dato) || forestilling.getSlutDato().isEqual(dato))) {
    return null;
}
```

**After:**
```java
if (!((forestilling.getStartDato().isBefore(dato) || forestilling.getStartDato().isEqual(dato)) &&
        (forestilling.getSlutDato().isAfter(dato) || forestilling.getSlutDato().isEqual(dato)))) {
    return null;
}
```

**Impact:** This bug prevented valid bookings from being created when dates were within the performance period.

### 2. Reference Comparison Error (Kunde.java)
**Severity:** CRITICAL  
**Location:** `Kunde.bestiltePladserTilForestillingPådag`, line 33  
**Issue:** Used `==` instead of `.equals()` for LocalDate comparison.

**Before:**
```java
if(bestilling.getDate() == date && bestilling.getForstilling() == forstilling){
```

**After:**
```java
if(bestilling.getDate().equals(date) && bestilling.getForstilling() == forstilling){
```

**Impact:** This bug caused the method to fail to find bookings even when dates matched, because `==` compares object references, not values.

### 3. Inefficient Loop Creating Multiple Objects (Controller.java)
**Severity:** HIGH  
**Location:** `Controller.opretBestillingMedPladser`, lines 45-58  
**Issue:** A Bestilling object was created inside the seat validation loop, causing unnecessary object creation and premature returns.

**Before:**
```java
for (Plads plads : pladser) {
    if (!forestilling.erPladsenLedig(plads.getRække(), plads.getNummer(), dato)) {
        return null;
    }
    Bestilling bestilling = new Bestilling(dato, forestilling, kunde);
    for (Plads plads1 : pladser) {
        bestilling.addPlads(plads1);
    }
    forestilling.addBestilling(bestilling);
    kunde.addBestilling(bestilling);
    return bestilling;
}
return null;
```

**After:**
```java
for (Plads plads : pladser) {
    if (!forestilling.erPladsenLedig(plads.getRække(), plads.getNummer(), dato)) {
        return null;
    }
}

Bestilling bestilling = new Bestilling(dato, forestilling, kunde);
for (Plads plads : pladser) {
    bestilling.addPlads(plads);
}
forestilling.addBestilling(bestilling);
kunde.addBestilling(bestilling);
return bestilling;
```

**Impact:** This refactoring ensures proper validation of all seats before creating a booking, and avoids creating unnecessary objects.

## Additional Issues Identified (Not Fixed)

### 4. Hard-coded Test Data (Forstilling.java)
**Severity:** MEDIUM  
**Location:** `Forstilling.createBestillinger`, line 33  
**Issue:** The method creates a booking with hard-coded test customer data.

```java
Bestilling bestilling = new Bestilling(date, this, new Kunde("testotron", "69420"));
```

**Recommendation:** Remove this method or make it accept a Kunde parameter.

### 5. Mutable Collections Exposed (Multiple files)
**Severity:** MEDIUM  
**Locations:**
- `Storage.getForestillinger()`, `getKunder()`, `getPladser()`
- `Bestilling.getPladser()`

**Issue:** Methods return direct references to internal ArrayList objects, allowing external modification.

**Recommendation:** Return defensive copies or unmodifiable collections:
```java
return new ArrayList<>(forestillinger);
// or
return Collections.unmodifiableList(forestillinger);
```

### 6. Inconsistent Naming
**Severity:** LOW  
**Issue:** Inconsistent use of "Forstilling" vs "Forestilling" throughout the codebase.
- Class is named `Forstilling` but method names use `Forestilling`
- Example: `Storage.addForestilling()` vs class name `Forstilling`

**Recommendation:** Standardize on one spelling throughout the codebase.

### 7. Magic Numbers (App.java)
**Severity:** LOW  
**Location:** `App.initStorage`, lines 34-57  
**Issue:** Seat creation logic contains magic numbers (5, 10, 19, etc.) and complex nested conditions.

```java
if (row <= 5 && seat <= 2 || seat >= 19) {
    // ...
}
```

**Recommendation:** Extract these as named constants and consider using a more maintainable data structure for seat configuration.

### 8. Missing Input Validation (GuiAdmin.java)
**Severity:** MEDIUM  
**Location:** `GuiAdmin.buttonAddForstilling` action, lines 147-156  
**Issue:** No validation for date parsing - will throw uncaught exception on invalid input.

```java
LocalDate StartDato = textFieldForstillingStartDato.getText().isBlank() ? 
    LocalDate.now() : LocalDate.parse(textFieldForstillingStartDato.getText().trim());
```

**Recommendation:** Add try-catch block and show error message to user on invalid date format.

### 9. Null Safety Issues (Forstilling.java)
**Severity:** MEDIUM  
**Location:** `Forstilling.succesDato`, lines 67-87  
**Issue:** Method contains null checks in the loop but returns null without handling the case where no valid date is found.

**Recommendation:** Document that this method can return null, or throw an exception when no bookings exist.

### 10. Missing Javadoc Comments
**Severity:** LOW  
**Issue:** No classes or methods have Javadoc documentation.

**Recommendation:** Add Javadoc comments to public APIs explaining purpose, parameters, and return values.

## Code Quality Observations

### Positive Aspects
1. ✅ Good separation of concerns with Model-View-Controller architecture
2. ✅ Proper use of JavaFX for GUI development
3. ✅ Defensive programming with null checks in several places
4. ✅ Use of enums for PladsType
5. ✅ Proper encapsulation with private fields and public getters

### Areas for Improvement
1. ⚠️ Missing unit tests for business logic
2. ⚠️ No logging framework for debugging and monitoring
3. ⚠️ No exception handling strategy
4. ⚠️ No data persistence (all data is in-memory)
5. ⚠️ No input validation at the model layer

## Security Considerations

### No Critical Security Issues Found
The application appears to be a local desktop application without network connectivity or persistent storage. No obvious security vulnerabilities were identified in the reviewed code.

### Recommendations
1. If adding persistence, ensure proper input sanitization to prevent injection attacks
2. If adding network capabilities, implement proper authentication and encryption
3. Consider adding rate limiting for booking operations if exposed via API

## Performance Considerations

1. **Storage Implementation:** Using ArrayList for storage is acceptable for small datasets but consider using HashMap with appropriate keys for larger datasets and faster lookups.

2. **Loop Optimization:** The `succesDato()` method has O(n²) complexity. Consider caching or optimizing if performance becomes an issue.

## Testing Recommendations

1. Add unit tests for:
   - Date validation logic in `Controller.opretBestillingMedPladser`
   - Booking creation and seat availability checking
   - Date comparison logic in `Kunde` and `Forstilling`
   - Edge cases (null inputs, invalid dates, duplicate bookings)

2. Add integration tests for:
   - Complete booking workflow
   - GUI interactions

## Conclusion

The code review identified and fixed 3 critical bugs that would have prevented the system from functioning correctly:
1. ✅ Date validation logic error
2. ✅ Object reference comparison error
3. ✅ Inefficient object creation in loop

Additional issues were identified but not fixed to maintain minimal changes. The codebase follows good architectural patterns but would benefit from:
- Comprehensive unit tests
- Better input validation and error handling
- Documentation (Javadoc)
- Addressing the exposed mutable collections

## Review Metadata
- **Review Date:** 2025-11-13
- **Reviewer:** GitHub Copilot Code Review Agent
- **Lines of Code Reviewed:** ~600 lines
- **Critical Bugs Fixed:** 3
- **Total Issues Identified:** 10
