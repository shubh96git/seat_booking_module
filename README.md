# Seat Booking Module
The Seat Booking System is designed to simulate a real-time booking platform like BookMyShow, where multiple users can select, lock, and book seats concurrently. The system ensures data consistency, thread safety, and concurrent access management using Java Multithreading and synchronization mechanisms like ReentrantLock.

# What have been performed
  - Seat Selection & Locking Mechanism Implemented
    - Users can select and lock one or more seats.
    - A lock is applied using ReentrantLock to prevent race conditions and double bookings.
    - Lock timeout mechanism is used to release locks if the booking is not completed within a specific time window.

  - Custom Lock Management
    - Designed a lock manager to allow seat unlocking by a different thread (e.g., the confirmation request), since ReentrantLock requires the same thread to unlock.
    - Lock ownership mapping handled at the application level using session/user identifiers.

  - Booking Confirmation
    - On confirmation, the system:
    - Validates lock ownership.
    - Marks seats as booked in the database.
    - Releases seat locks.

  - Concurrency Handling
    - Handled with Java concurrent collections and atomic operations to ensure thread-safe updates.
    - Multiple users can perform seat operations without interfering with each other’s state.

  - Database Persistence
    - Seat states (available, locked, booked) are persisted in the database.
    - Transactions ensure atomic updates during booking.

  - Exception and Timeout Handling
    - Graceful handling of exceptions during lock, unlock, and booking steps.
    - Time-based expiry for stale locks.
    - Simulated Multi-threading Environment for Testing
    - Used thread pools to simulate multiple users booking seats simultaneously.

  - Unit tests created to verify thread safety and correctness.
