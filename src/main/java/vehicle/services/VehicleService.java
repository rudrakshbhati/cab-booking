package vehicle.services;

import vehicle.models.Vehicle;

import java.util.List;

public interface VehicleService {
    boolean registerVehicle(Vehicle vehicle);
    boolean updateLocation(String vehicleId,Double lat, Double lon);
    List<Vehicle> findNearby(Double lat, Double lon);
    void blockVehicles(List<String> vehicleIds,String aggregationId);
    void unblockVehicle(String vehicleId);
    void unblockVehicles(String aggregationId);

}
