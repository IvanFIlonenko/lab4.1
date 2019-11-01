package lab4;


import javafx.util.Pair;

public class Message {
    private Pair<Integer, JsonPackage> message;

    public Message(int a, JsonPackage pack){
        this.message = new Pair<>(a, pack);
    }

    public Pair<Integer, JsonPackage> getMessage(){
        return this.message;
    }
}
