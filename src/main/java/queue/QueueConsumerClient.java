package queue;


public interface QueueConsumerClient extends QueueClient {
     String pollMessage();
     void receiveMessage(String message);
}
