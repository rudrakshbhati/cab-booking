package aggregator;

import notification.NotificationService;
import queue.QueueConsumerClient;
import queue.QueueProducerClient;
import rider.models.Rider;
import storage.StorageService;
import vehicle.models.Vehicle;
import vehicle.services.VehicleService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AggregateServiceImpl implements AggregateService{

    private final QueueConsumerClient queueConsumerClient;
    private final StorageService storageService;
    private final NotificationService notificationService;
    private final VehicleService vehicleService;
    private final QueueProducerClient queueProducerClient;
    private final int SCHEDULED_SECONDS_FOR_PARTNER_MESSAGES = 30;


    public AggregateServiceImpl(QueueConsumerClient queueConsumerClient, StorageService storageService,
                                NotificationService notificationService,VehicleService vehicleService,
                                QueueProducerClient queueProducerClient) {
        this.queueConsumerClient = queueConsumerClient;
        this.storageService = storageService;
        this.notificationService = notificationService;
        this.vehicleService = vehicleService;
        this.queueProducerClient = queueProducerClient;
    }



    //todo have a logic which fetch vehicle not just on similar eta but also on other factors like ratings,car requirement
    @Override
    public List<Vehicle> fetchSuitableRides(Double lat, Double lon){
        return storageService.findVehicleBySimilarEta(lat,lon);
    }


    /**
     *todo check if queue is healthy
     */
    @Override
    public void aggregateRides(){

        while (true){
            String aggregationId = UUID.randomUUID().toString();
            String riderId = queueConsumerClient.pollMessage();
            Rider rider = storageService.getRider(riderId);
            final List<Vehicle> vehicleList = fetchSuitableRides(rider.getLat(),rider.getLon());
            Integer ridersNearby = storageService.getRidersInGeoHash(rider.getLat(),rider.getLon());
            int numberOfPartnersToBrodCast = vehicleList.size()/ridersNearby;
            if(numberOfPartnersToBrodCast<1){
                numberOfPartnersToBrodCast = 1;
            }
            List<String> partnerIds = vehicleList.stream().map(Vehicle::getPartnerId).collect(Collectors.toList());
            List<String> vehicleIds = vehicleList.stream().map(Vehicle::getId).collect(Collectors.toList());
            boolean partnersNotified = sendNotificationToPartners(partnerIds,numberOfPartnersToBrodCast);
            if(partnersNotified){
                vehicleService.blockVehicles(vehicleIds,aggregationId);
                //todo scheduling time to be dynamic based on drivers in the geohash at the time of request and riders in that geohash
                queueProducerClient.scheduleMessage(aggregationId,SCHEDULED_SECONDS_FOR_PARTNER_MESSAGES);
            }
        }
    }

    @Override
    public boolean sendNotificationToPartners(List<String> partnerIds,int numberOfPartnersToBrodCast){
        return notificationService.notifySelectedPartners(partnerIds.subList(0,numberOfPartnersToBrodCast),"new Booking");
    }

}
