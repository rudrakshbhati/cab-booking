package booking.service;

import booking.models.Booking;

import java.util.List;

public interface BookingService {
    Booking book(String riderUserId, String vehicleId);
    List<Booking> history(String riderUserId);
    boolean endTrip(Long timeStamp, String bookingId);
    String requestRide(String riderUserId);
}
