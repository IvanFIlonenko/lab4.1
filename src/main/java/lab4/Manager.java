package lab4;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.RoundRobinPool;

public class Manager extends AbstractActor {
    private final ActorRef executors;
    private final ActorRef storage;
    private final int NUMBER_OF_POOLS = 5;

    public Manager(){
        executors = getContext().actorOf(new RoundRobinPool(NUMBER_OF_POOLS).props(Props.create(SolveTestActor.class)));
        storage = getContext().actorOf(Props.create(Storage.class));
    }

    public Receive createReceive() {
        return ReceiveBuilder.create().match(JsonPackage.class, pack -> {
            for(int i=0; i<pack.getTests().length; i++){
                executors.tell(new Message(i,pack), storage);
            }
        }).match(GetResult.class, request -> storage.tell(request, sender())).build();
    }
}
