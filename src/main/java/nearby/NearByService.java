package nearby;

import vehicle.models.Vehicle;
import vehicle.services.VehicleService;

import java.util.Collections;
import java.util.List;

public class NearByService {
    private final VehicleService vehicleService;

    public NearByService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

     public List<Vehicle> getNearbyVehicles(Double lat, Double lon){
        try {
            return vehicleService.findNearby(lat, lon);
        }catch (Exception e){
            System.out.print("error in fetching nearby vehicles");
            return Collections.emptyList();
        }
     }


}
