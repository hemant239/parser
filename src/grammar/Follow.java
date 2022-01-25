package grammar;

import java.util.HashSet;

public class Follow {

    private String symbol;
    private HashSet<String> setOfFollow=new HashSet<>();

    public Follow(){
    }
    public Follow(String symbol){
        this.symbol=symbol;
    }

    public Follow(String symbol, HashSet<String> setOfFollow) {
        this.symbol = symbol;
        this.setOfFollow = setOfFollow;
    }

    public void addToFollow(String symbol){
        setOfFollow.add(symbol);
    }

    public void addAllToFollow(HashSet<String> setOfFollow){
        this.setOfFollow.addAll(setOfFollow);
    }

    public HashSet<String> getSetOfFollow() {
        return setOfFollow;
    }

    public void print(){
        System.out.println("Follow(" + symbol+")"+" : "+setOfFollow);
    }

    public String writeInFile(){
        return "Follow("+ symbol+")"+" : "+setOfFollow;
    }
}
