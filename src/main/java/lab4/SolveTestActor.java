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

    public Receive createReceive(){
        return ReceiveBuilder.create().match(Message.class, message -> {
            Pair<Integer, JsonPackage> msg = message.getMessage();
            int index = msg.getKey();
            JsonPackage pack = msg.getValue();
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
            try {
                engine.eval(pack.getJsScript());
            } catch (ScriptException e){
            }
            Invocable invocable = (Invocable) engine;
            String params = "";
            for(int i=0; i<pack.getTests()[index].getParams().length; i++){
                params += pack.getTests()[index].getParams()[i] + ",";
            }
            params = params.substring(0, params.length()-1);

            String res = invocable.invokeFunction(pack.getFunctionName(), Object[] pack.getTests()[index].getParams()).toString();
            pack.writeResult(index, res);
            System.out.println(res);
            getSender().tell(pack, ActorRef.noSender());
        }).build();
    }
}
