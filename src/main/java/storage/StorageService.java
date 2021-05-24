package storage;

import booking.models.Booking;
import partner.models.Partner;
import rider.models.Rider;
import vehicle.models.Vehicle;

import java.util.List;

public interface StorageService {
    boolean saveRider(Rider rider);
    boolean savePartner(Partner partner);
    boolean saveVehicle(Vehicle vehicle);
    boolean updateLocation(String vehicleId, Double lat,Double lon);
    boolean updateRiderLocation(String  riderId,Double lat,Double lon );
    boolean book(Booking booking);
    List<Vehicle> findVehicleBySimilarEta(Double lat, Double lon);
    List<Vehicle> findVehicles(Double lat, Double lon, Double maxDistance);
    List<Booking> rideHistory(String riderId);
    boolean endTrip(Long timeStamp, String bookingId);
    Rider getRider(String riderId);
    Integer getRidersInGeoHash(Double lat, Double lon);
    boolean blockVehicles(List<String> vehicleIds,String aggregationId);
    boolean unblockVehicle(String vehicleId);
    boolean unblockVehicles(String aggregationId);

}
