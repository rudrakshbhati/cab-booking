package queue;


public class QueueProducerClientImpl implements QueueProducerClient {


    public void sendMessage(String message,int ttl){
        // for the requestQueue
    }

    public boolean clientHealthCheck() {
        return true;
    }


    public void scheduleMessage(String message,int scheduledSeconds){
        // another queue for time based applications
    }
}
