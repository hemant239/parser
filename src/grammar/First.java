package grammar;

import java.util.HashSet;

public class First {

    private String symbol;
    private HashSet<String> setOfFirst=new HashSet<>();

    public First(){
    }
    public First(String symbol){
        this.symbol=symbol;
    }


    public First(String symbol,HashSet<String> setOfFirst) {
        this.symbol = symbol;
        this.setOfFirst = setOfFirst;
    }

    public void addToFirst(String symbol){
        setOfFirst.add(symbol);
    }

    public void addAllToFirst(HashSet<String> setOfFirst){
        this.setOfFirst.addAll(setOfFirst);
    }

    public HashSet<String> getSetOfFirst() {
        return setOfFirst;
    }

    public void print(){
        System.out.println("First(" + symbol+")"+" : "+setOfFirst);
    }

    public String writeInFile(){
        return "First("+ symbol+")"+" : "+setOfFirst;
    }
}
