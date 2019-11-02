package lab4;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import javafx.util.Pair;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class SolveTestActor extends AbstractActor {
    private static final String JS_ENGINE = "nashorn";

    public Receive createReceive(){
        return ReceiveBuilder.create().match(Message.class, message -> {
            Pair<Integer, JsonPackage> msg = message.getMessage();
            int index = msg.getKey();
            JsonPackage pack = msg.getValue();
            Test test = pack.getTests()[index];
            ScriptEngine engine = new ScriptEngineManager().getEngineByName(JS_ENGINE);
            try {
                engine.eval(pack.getJsScript());
            } catch (ScriptException e){
            }
            Invocable invocable = (Invocable) engine;
            String res = invocable.invokeFunction(pack.getFunctionName(), test.getParams()).toString();
            String check = "false";
            if (res.equals(test.getExpectedResult())) {
                check = "true";
            }
            TestResult result = new TestResult(test.getTestName(), test.getExpectedResult(), test.getParams(), res, check);
            pack.writeResult(index, res);
            getSender().tell(pack, ActorRef.noSender());
        }).build();
    }
}
