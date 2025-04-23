# Seat Booking Module
The Seat Booking System is designed to simulate a real-time booking platform like BookMyShow, where multiple users can select, lock, and book seats concurrently. The system ensures data consistency, thread safety, and concurrent access management using Java Multithreading and synchronization mechanisms like ReentrantLock.

# What have been performed
  - Seat Selection & Locking Mechanism Implemented
    - Users can select and lock one or more seats.
    - A lock is applied using ReentrantLock to prevent race conditions and double bookings.
    - Lock timeout mechanism is used to release locks if the booking is not completed within a specific time window.

  - Custom Lock Management
    a. Designed a lock manager to allow seat unlocking by a different thread (e.g., the confirmation request), since ReentrantLock requires the same thread to unlock.
    b. Lock ownership mapping handled at the application level using session/user identifiers.

  - Booking Confirmation
    a. On confirmation, the system:
    b. Validates lock ownership.
    c. Marks seats as booked in the database.
    d. Releases seat locks.

  - Concurrency Handling
    a. Handled with Java concurrent collections and atomic operations to ensure thread-safe updates.
    b. Multiple users can perform seat operations without interfering with each other’s state.

  - Database Persistence
    a. Seat states (available, locked, booked) are persisted in the database.
    b. Transactions ensure atomic updates during booking.

  - Exception and Timeout Handling
    a. Graceful handling of exceptions during lock, unlock, and booking steps.
    b. Time-based expiry for stale locks.
    c. Simulated Multi-threading Environment for Testing
    d. Used thread pools to simulate multiple users booking seats simultaneously.

  - Unit tests created to verify thread safety and correctness.
