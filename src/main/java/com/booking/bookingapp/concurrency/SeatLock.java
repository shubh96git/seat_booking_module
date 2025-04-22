package com.booking.bookingapp.concurrency;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import lombok.*;

@Getter @Setter @ToString
public class SeatLock {

    private final ReentrantLock lock = new ReentrantLock();
    private final Long seatId;
    private String lockedByUserId;
    private LocalDateTime lockedAt;
    private boolean isLocked; 

    public SeatLock(Long seatId) {
        this.seatId = seatId;
        this.isLocked = false;
    }

    // 1. Locking the seat before moving to details and payment
    public boolean lock(String userName) throws InterruptedException {
        if (lock.tryLock(2000, TimeUnit.MILLISECONDS)) {
            try {

                // Check if seat is already locked in our custom tracking
                if (!isLocked) { 

                    // locked seat using custom locking for provided userName
                    this.isLocked = true;
                    this.lockedByUserId = userName;
                    this.lockedAt = LocalDateTime.now();
                    return true;
                }
            } finally {
                // Release the ReentrantLock
                lock.unlock(); 
            }
        }
        return false;
    }

    // 2. Unlocking the seat once all confirmation is done
    public boolean unlock(String userId) {

        // Acquire the ReentrantLock to ensure thread safety
        lock.lock(); 
        try {

            // Only unlock if the current lock is held by the same userId
            if (isLocked && userId.equals(this.lockedByUserId)) {
                reset();
                return true;
            }
        } finally {
            lock.unlock();
        }
        return false;
    }

    // 3. This will be used to clear the locks after time expiry
    public boolean forceUnlock() {
        
        lock.lock(); 
        try {
            if (isLocked) {
                reset();

                System.out.println("scheduler clear the locks after time expiry");
                return true;
            }
        } finally {
            lock.unlock();
        }
        return false;
    }

    // Helper method :
    public boolean isExpired(LocalDateTime now) {
        int timeoutInSeconds = 2 * 60;
        return isLocked && lockedAt != null && lockedAt.plusSeconds(timeoutInSeconds).isBefore(now);
    }

    private void reset() {
        this.lockedByUserId = null;
        this.lockedAt = null;
        this.isLocked = false;
    }

    public Long getSeatId() {
        return seatId;
    }
}
