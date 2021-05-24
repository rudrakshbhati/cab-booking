package booking.service;

import booking.models.Booking;
import notification.NotificationService;
import queue.QueueProducerClient;
import storage.StorageService;
import vehicle.services.VehicleService;

import java.util.Collections;
import java.util.List;

public class BookingServiceImpl implements BookingService{

    private final StorageService storageService;
    private final QueueProducerClient queueProducerClient;
    private final VehicleService vehicleService;
    private final NotificationService notificationService;


    public BookingServiceImpl(StorageService storageService,
                              QueueProducerClient queueProducerClient, VehicleService vehicleService,
                              NotificationService notificationService) {
        this.storageService = storageService;
        this.queueProducerClient = queueProducerClient;
        this.vehicleService = vehicleService;
        this.notificationService = notificationService;
    }

    public Booking book(String riderUserId, String vehicleId){
        Booking booking = new Booking();
        booking.setVehicleId(vehicleId);
        booking.setRiderUserId(riderUserId);
        storageService.book(booking);
        return booking;
    }


    public List<Booking> history(String riderUserId){
        return Collections.emptyList();
    }

    public boolean endTrip(Long timeStamp, String bookingId){
        return true;
    }


    public String requestRide(String riderUserId){
        String message = riderUserId;
        int ttl = 30;
        //todo ttl to be dynamic based on drivers in the geohash at the time of request and riders in that geohash
        queueProducerClient.sendMessage(message,ttl);

        // to either notify user or put the request back in the requestQueue
        queueProducerClient.scheduleMessage(riderUserId,ttl);

        return "Ride Requested";
    }

    public boolean acceptRide(String vehicleId,String riderId,String aggregationId){
        Booking booking = book(riderId,vehicleId);
        //todo separate thread
        vehicleService.unblockVehicles( aggregationId);
        notificationService.notifyRider(riderId);
        return true;
    }
}
