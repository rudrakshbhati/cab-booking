package notification;

import java.util.List;

public interface NotificationService {

    boolean notifyRider(String riderId);
    boolean notifyPartner(String partnerId);
    boolean notifySelectedPartners(List<String> partnerIdList,String message);
}
