package queue;


public interface QueueProducerClient extends QueueClient {
    void sendMessage(String message,int ttl);
    void scheduleMessage(String message,int scheduledSeconds);
}
