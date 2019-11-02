package lab4;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import com.sun.tools.javac.util.Pair;

import java.util.*;

public class Storage extends AbstractActor {
    private HashMap<Integer,ArrayList<TestResult>> data = new HashMap<>();

    public Receive createReceive() {
        return ReceiveBuilder.create().match(GetResult.class,
                request -> getSender().tell(new Pair<Integer,ArrayList<TestResult>>(request.getPackageId(),data.get(request.getPackageId())), ActorRef.noSender()))
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
                    data.
                })
                .build();
    }
}
