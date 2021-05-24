package vehicle.services;

import storage.StorageService;
import vehicle.models.Vehicle;

import java.util.List;

public class VehicleServiceImpl implements VehicleService {
    private final StorageService storageService;

    private static final double MAX_DISTANCE = 3D;

    public VehicleServiceImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public boolean registerVehicle(Vehicle vehicle) {
        this.storageService.saveVehicle(vehicle);
        return true;
    }
    /**
     * location of vehicle to be updated in every x seconds
     */
    @Override
    public boolean updateLocation(String vehicleId,Double lat,Double lon) {
        this.storageService.updateLocation(vehicleId,lat,lon);
        return true;
    }
    @Override
    public List<Vehicle> findNearby(Double lat, Double lon) {
        List<Vehicle> vehicleList = this.storageService.findVehicles(lat, lon, MAX_DISTANCE);
        if( vehicleList== null || vehicleList.isEmpty()){
            throw new RuntimeException("Vehicle not available");
        }
        return vehicleList;
    }

    @Override
    public void blockVehicles(List<String> vehicleIds,String aggregationId){
        storageService.blockVehicles(vehicleIds,aggregationId);

    }

    public void unblockVehicle(String vehicleId){
        storageService.unblockVehicle(vehicleId);
    }

    public void unblockVehicles(String aggregationId){
        storageService.unblockVehicles(aggregationId);
    }

}
