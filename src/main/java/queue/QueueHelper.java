package queue;

import rider.services.RiderService;
import vehicle.services.VehicleService;

public class QueueHelper {

    private final VehicleService vehicleService;
    private final RiderService riderService;


    public QueueHelper(VehicleService vehicleService,
                       RiderService riderService){
        this.vehicleService = vehicleService;
        this.riderService = riderService;
    }


    // message contains aggregation id or rider id
    public void processMessageFromQueue(String message){
        switch (message){
            case "aggreagationId":
                vehicleService.unblockVehicles(message);
                break;
            case "riderId":
                riderService.notifyRider(message);
                break;
            default:
                break;
        }


    }
}
