package grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class FirstAndFollow {
    private final HashMap<String,First> allFirsts=new HashMap<>();
    private final HashMap<String,Follow> allFollows=new HashMap<>();
    private final Grammar grammar;


    FirstAndFollow(Grammar grammar){
        this.grammar=grammar;
        firsts();
        follows();

    }

    private void firstForTerminals(){
        for(String terminal:grammar.getTerminals()){
            First first=new First(terminal);
            first.addToFirst(terminal);
            allFirsts.put(terminal,first);
        }
    }

    private boolean firstForSingleNonTerminal(String nonTerminal){
        First first=new First(nonTerminal);
        Rule rule=grammar.getRules().get(nonTerminal);
        boolean derivesNull=false;
        for(String production:rule.getProductions()){
            derivesNull= production.equalsIgnoreCase("NULL");
            ArrayList<String> symbols= new ArrayList<> (Arrays.asList(production.split(" ")));
            boolean isNull=true;
            int i=0;
            while(isNull && i< symbols.size()){
                String symbol=symbols.get(i);

                if(allFirsts.containsKey(symbol)){
                    isNull= allFirsts.get(symbol).getSetOfFirst().contains("NULL");

                }
                else{
                    allFirsts.put(nonTerminal,first);
                    isNull=firstForSingleNonTerminal(symbol);
                }

                first.addAllToFirst(allFirsts.get(symbol).getSetOfFirst());
                i++;
            }
        }
        allFirsts.put(nonTerminal,first);
        return derivesNull;
    }


    private void firstForNonTerminals(){
        HashSet<String> nonTerminals= grammar.getNonTerminals();

        for(String nonTerminal:nonTerminals){
            firstForSingleNonTerminal(nonTerminal);
        }

    }

    private void firsts(){
        firstForTerminals();
        firstForNonTerminals();
    }

    public void printFirsts(){
        for(First first:allFirsts.values()){
            first.print();
        }
    }


    private HashSet<String> firstForExpression(ArrayList<String> symbols,int index){
        HashSet<String> firsts=new HashSet<>();

        while(index<symbols.size() ){
            firsts.addAll(allFirsts.get(symbols.get(index)).getSetOfFirst());
            if(!firsts.contains("NULL")){
                break;
            }
            index++;
        }
        if(index!=symbols.size()){
            firsts.remove("NULL");
        }

        return firsts;
    }

    private void followForSingleNonTerminal(String nonTerminal){
        Follow follow=new Follow(nonTerminal);
        for(Rule rule:grammar.getRules().values()){
            for(String production:rule.getProductions()){
                ArrayList<String> symbols=new ArrayList<>(Arrays.asList(production.split("\\s")));
                int index=symbols.indexOf(nonTerminal);
                if(index== symbols.size()-1){
                    if(allFollows.containsKey(rule.getSymbol())){
                        follow.addAllToFollow(allFollows.get(rule.getSymbol()).getSetOfFollow());
                    }
                }
                else if(index>=0){
                    HashSet<String> firsts=firstForExpression(symbols,index+1);

                    if(firsts.contains("NULL")){
                        if(allFollows.containsKey(rule.getSymbol())){
                            follow.addAllToFollow(allFollows.get(rule.getSymbol()).getSetOfFollow());
                        }
                        firsts.remove("NULL");
                    }
                    follow.addAllToFollow(firsts);
                }
            }
        }
        allFollows.put(nonTerminal,follow);
    }


    private void followForNonTerminals(){
        for(String nonTerminal:grammar.getNonTerminals()){
            followForSingleNonTerminal(nonTerminal);
        }
    }

    private void follows(){

        followForNonTerminals();
        allFollows.get(grammar.getStartSymbol()).addToFollow("$");
        followForNonTerminals();
        followForNonTerminals();
    }

    public HashMap<String, First> getAllFirsts() {
        return allFirsts;
    }

    public HashMap<String, Follow> getAllFollows() {
        return allFollows;
    }

    public void printFollows(){
        for(Follow follow:allFollows.values()){
            follow.print();
        }
    }
}
