package lab4;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import java.util.*;

public class Storage extends AbstractActor {
    private HashMap<Integer,ArrayList<TestResult>> data = new HashMap<>();

    public Receive createReceive() {
        return ReceiveBuilder.create().match(GetResult.class,
                request -> getSender().tell(data.get(request.getPackageId()).toArray(), ActorRef.noSender()))
                .match(StorageTestResult.class, msg -> {
                    if (data.containsKey(msg.getPackageId())){
                        ArrayList<TestResult> tests = data.get(msg.getPackageId());
                        tests.add(msg.getTestResult());
                        data.put(msg.getPackageId(), tests);
                    } else {
                        ArrayList<TestResult> tests = new ArrayList<>();
                        tests.add(msg.getTestResult());
                        data.put(msg.getPackageId(), tests);
                    }
                    System.out.println(data.get(msg.getPackageId()).get(0).getTestName());
                    System.out.println(msg.getPackageId());
                })
                .build();
    }
}
