package queue;


public class QueueConsumerClientImpl implements QueueConsumerClient {

    public final QueueHelper queueHelper;

    public QueueConsumerClientImpl(QueueHelper queueHelper){
        this.queueHelper = queueHelper;
    }


    public String pollMessage(){
        // poll from queue to get rider id
        return "riderID";
    }

    public boolean clientHealthCheck(){
        return true;
    }

    //scheduled messages arrive here
    public void receiveMessage(String message){
        queueHelper.processMessageFromQueue(message);
    }

}
