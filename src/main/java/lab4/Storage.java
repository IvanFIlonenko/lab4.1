package lab4;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import java.util.*;

public class Storage extends AbstractActor {
    private ArrayList<JsonPackage> data = new ArrayList<>();

    public Receive createReceive() {
        return ReceiveBuilder.create().match(GetResult.class,
                request -> getSender().tell(data.get(request.getPackageId()), ActorRef.noSender()))
                .match(JsonPackage.class, pack -> {
                    data.add(pack);
                })
                .build();
    }
}
