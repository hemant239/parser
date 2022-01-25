package grammar;

import java.util.HashSet;

public class Rule {

    private String symbol;
    private HashSet<String> productions;

    public Rule(String symbol, HashSet<String> productions) {
        this.symbol = symbol;
        this.productions = productions;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void addProductions(String symbols){
        productions.add(symbols);
    }

    public HashSet<String> getProductions() {
        return productions;
    }

    public void setProductions(HashSet<String> productions) {
        this.productions = productions;
    }

    public void print(){
        System.out.print(symbol+" -> ");
        for(String production:productions){
            System.out.print(production+" | ");
        }
        System.out.println();
    }

    public String writeInFile(){
        StringBuilder toWrite= new StringBuilder(symbol+" -> ");
        for(String production:productions){
            toWrite.append(production).append(" | ");
        }

        return toWrite.toString();

    }
}
