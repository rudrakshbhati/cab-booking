package notification;

import java.util.List;

public class NotificationServiceImpl implements NotificationService {


    @Override
    public boolean notifyRider(String riderId){
        return true;
    }

    @Override
    public boolean notifyPartner(String partnerId){
        return true;
    }

    @Override
    public boolean notifySelectedPartners(List<String> partnerIdList,String message){
        return true;
    }
}
