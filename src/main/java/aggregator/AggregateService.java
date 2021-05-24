package aggregator;

import vehicle.models.Vehicle;

import java.util.List;

public interface AggregateService {


    List<Vehicle> fetchSuitableRides(Double lat, Double lon);
    boolean sendNotificationToPartners(List<String> partnerIds,int numberOfPartnersToBrodCast);
    void aggregateRides();
}
