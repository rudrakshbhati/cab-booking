package rider.services;

import rider.models.Rider;

public interface RiderService {
    boolean register(Rider rider);
    boolean updateRiderLocation(String riderId,Double lat,Double lon);
    void notifyRider(String riderId);
}
