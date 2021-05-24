package rider.services;

import notification.NotificationService;
import rider.models.Rider;
import storage.StorageService;

public class RiderServiceImpl implements RiderService {
    private final StorageService storageService;
    private final NotificationService notificationService;


    public RiderServiceImpl(StorageService storageService,NotificationService notificationService) {
        this.storageService = storageService;
        this.notificationService = notificationService;
    }

    public boolean register(Rider rider) {
        this.storageService.saveRider(rider);
        return true;
    }

    /**
     * location of rider to be updated in every x seconds whenever app is started
     */
    @Override
    public boolean updateRiderLocation(String riderId,Double lat,Double lon) {
        this.storageService.updateRiderLocation(riderId,lat,lon);
        return true;
    }

    /**
     * to notify rider for ride found or ride not found
     * @param riderId
     */
    @Override
    public void notifyRider(String riderId){
        notificationService.notifyRider(riderId);
    }


}
