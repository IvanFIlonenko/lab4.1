package lab4;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.routing.RoundRobinPool;

public class Manager extends AbstractActor {
    private final ActorRef executors;
    private final ActorRef storage;

    public Manager(){
        executors = getContext().actorOf(new RoundRobinPool(5).props(Props.create(SolveTestActor.class)));
        storage = getContext().actorOf(Props.create(Storage.class));
    }

    public Receive createReceive() {
        return receiveBuilder().create().match(JsonPackage.class, pack -> {
            for(int i=0; i<pack.getTests().length; i++){
                executors.tell(new Message(i,pack), storage);
            }
        }).match(Message.class, request -> storage.tell(request, sender())).build();
    }
}
