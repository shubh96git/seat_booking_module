package com.booking.bookingapp.concurrency;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.booking.bookingapp.dao.ConfirmSeatRepository;
import com.booking.bookingapp.entity.ConfirmedSeatEntity;

// Entity managing all activities related to providing seats to threads
@Component
public class SeatManager {

    //
    @Autowired
    private ConfirmSeatRepository confirmSeatRepository;

    // Collection holding locks for the seats
    private final ConcurrentHashMap<Long, SeatLock> locksForSeat = new ConcurrentHashMap<>();
    private final int lockTimeoutInSeconds = 5 ;

    // 
    public boolean getSeatForThread(Long seatId, String userName) throws InterruptedException{
        
        //
        Optional<ConfirmedSeatEntity> confirmedSeatEntity = confirmSeatRepository.findById(seatId);
        Thread.sleep(2000);
        if (!confirmedSeatEntity.isPresent()) {

            SeatLock lock = locksForSeat.computeIfAbsent(seatId, (id)->new SeatLock(id));
            if (lock.lock(userName)) {
                System.out.println("Seat " + seatId + " locked by User " + userName);
                return true;
            }

        } 
        System.out.println("Seat " + seatId + " is already locked and cannot be locked by User " + userName);
        return false;
    }

        
    public boolean unlockTheSeat(Long seatId, String userName){

        // get the lock of seat
        SeatLock lock = locksForSeat.get(seatId);
        if(lock != null && !lock.isExpired(LocalDateTime.now()) && lock.unlock(userName)){

            locksForSeat.remove(seatId);
            System.out.println("Seat " + seatId + " unlocked by " + userName);
            return true; 
        }
        System.out.println("Seat " + seatId + " unlocking failed by " + userName);
        return false;
    }

    // 
    @Scheduled(fixedRate = 2000 * 60 ) 
    public void releaseExpiredLocks() {

        //
        LocalDateTime now = LocalDateTime.now();
        locksForSeat.forEach((seatId, seatLock) -> {

            if(seatLock.getLockedAt().plusSeconds(lockTimeoutInSeconds).isBefore(now)){
                boolean result = seatLock.forceUnlock();
                if(result){
                    locksForSeat.remove(seatId);
                }
            }
        });
    }    
}
