package storage;

import booking.models.Booking;
import partner.models.Partner;
import rider.models.Rider;
import vehicle.models.Vehicle;

import java.util.*;

public class StorageServiceImpl implements StorageService {
    private final Map<String, Rider> riderStorage;
    private final Map<String, Partner> partnerStorage;
    private final Map<String, Vehicle> vehicleStorage;
    private final Map<String, Booking> bookingStorage;
    private final Map<String, Integer> vehicleGeoHashGrid;
    private final Map<String, Integer> riderGeoHashGrid;
    private final Map<String,List<String>> aggregationMap;


    public StorageServiceImpl() {
        this.riderStorage = new HashMap<>();
        this.partnerStorage = new HashMap<>();
        this.vehicleStorage = new HashMap<>();
        this.bookingStorage = new HashMap<>();
        this.vehicleGeoHashGrid = new HashMap<>();
        this.riderGeoHashGrid = new HashMap<>();
        this.aggregationMap = new HashMap<>();
    }

    public boolean saveRider(Rider rider) {
        return true;
    }

    @Override
    public boolean savePartner(Partner partner) {
        return true;
    }

    @Override
    public boolean saveVehicle(Vehicle vehicle) {
        return true;
    }

    @Override
    public boolean updateLocation(String vehicleId, Double lat,Double lon) {
        if(vehicleStorage.get(vehicleId) == null){
            throw new RuntimeException("Vehicle does not exist in the system");
        }
        Vehicle vehicleInDb = vehicleStorage.get(vehicleId);
        vehicleInDb.setLat(lat);
        vehicleInDb.setLon(lon);
        vehicleStorage.put(vehicleId, vehicleInDb);
        String geohash = getGeoHash(lat,lon);
        vehicleGeoHashGrid.merge(geohash, 1, Integer::sum);
        return true;
    }

    @Override
    public boolean updateRiderLocation(String riderId, Double lat,Double lon) {
        if(riderStorage.get(riderId) == null){
            throw new RuntimeException("rider does not exist in the system");
        }
        Rider riderInDb = riderStorage.get(riderId);
        riderInDb.setLat(lat);
        riderInDb.setLon(lon);
        riderStorage.put(riderId, riderInDb);
        String geohash = getGeoHash(lat,lon);
        riderGeoHashGrid.merge(geohash, 1, Integer::sum);
        return true;
    }

    @Override
    public boolean book(Booking booking) {
        bookingStorage.put(booking.getBookingId(), booking);
        Rider rider = riderStorage.get(booking.getRiderUserId());
        List<String> bookingHistory = rider.getBookingHistory();
        if(bookingHistory == null){
            bookingHistory = new ArrayList<>();
        }
        bookingHistory.add(booking.getBookingId());
        rider.setBookingHistory(bookingHistory);
        riderStorage.put(booking.getRiderUserId(), rider);
        System.out.print("new booking created for rider");
        return true;
    }

    @Override
    public List<Vehicle> findVehicleBySimilarEta(Double lat, Double lon) {
        // check isAvailable true
        // similar eta would also include ongoing rides which will end nearby the rider
        return new ArrayList<>();
    }

    @Override
    public List<Vehicle> findVehicles(Double lat, Double lon,Double maxDistance) {
        return new ArrayList<>();
    }

    @Override
    public List<Booking> rideHistory(String riderId) {
        return new ArrayList<>(0);
    }

    @Override
    public boolean endTrip(Long timeStamp, String bookingId) {
        return true;
    }

    @Override
    public Rider getRider(String riderId){
        if(riderStorage.get(riderId) == null){
            throw new RuntimeException("rider does not exist in the system");
        }
        return riderStorage.get(riderId);
    }


    @Override
    public boolean blockVehicles(List<String> vehicleIds,String aggregationId){
        List<String> vehicleIdList = new ArrayList<>();
        for(String vehicleId: vehicleIds){
            Vehicle vehicle =  vehicleStorage.get(vehicleId);
            if(vehicle == null){
                continue;
            }
            vehicle.setAvailable(false);
            vehicleIdList.add(vehicleId);
            vehicleStorage.put(vehicleId,vehicle);
        }
        aggregationMap.put(aggregationId,vehicleIdList);
        return true;
    }

    @Override
    public boolean unblockVehicle(String vehicleId){
        Vehicle vehicle =  vehicleStorage.get(vehicleId);
        if(vehicle == null){
            throw new RuntimeException("vehicle does not exist in the system");
        }
        vehicle.setAvailable(true);
        vehicleStorage.put(vehicleId,vehicle);
        return true;
    }

    @Override
    public boolean unblockVehicles(String aggregationId){
        List<String> vehicleIds = aggregationMap.get(aggregationId);
        for(String vehicleId: vehicleIds){
            Vehicle vehicle =  vehicleStorage.get(vehicleId);
            if(vehicle == null){
                continue;
            }
            vehicle.setAvailable(true);
            vehicleStorage.put(vehicleId,vehicle);
        }
        return true;
    }

    public Integer getRidersInGeoHash(Double lat, Double lon){
        return riderGeoHashGrid.get(getGeoHash(lat,lon));
    }

    private String getGeoHash(Double lat, Double lon){
        return "geoHash";
    }
}
