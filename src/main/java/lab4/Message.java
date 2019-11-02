package lab4;


import javafx.util.Pair;

public class Message {
    private Pair<Integer, FunctionPackage> message;

    public Message(int a, FunctionPackage pack){
        this.message = new Pair<>(a, pack);
    }

    public Pair<Integer, FunctionPackage> getMessage(){
        return this.message;
    }
}
