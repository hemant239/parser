package grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class ParsingTable {

    private final HashMap<String,HashMap<String,ArrayList<String>>> parsingTable=new HashMap<>();
    private final HashMap<String,First> allFirsts;
    private final HashMap<String,Follow> allFollows;
    Grammar grammar;


    public ParsingTable(Grammar grammar){
        this.grammar=grammar;
        FirstAndFollow firstAndFollow=new FirstAndFollow(grammar);
        allFirsts=firstAndFollow.getAllFirsts();
        allFollows= firstAndFollow.getAllFollows();

        constructParsingTable();

        printParsingTable();

    }

    public HashMap<String, HashMap<String, ArrayList<String>>> getParsingTable() {
        return parsingTable;
    }

    private void constructParsingTable(){
        for(String nonTerminal: grammar.getNonTerminals()){
            constructParsingTableForNonTerminal(nonTerminal);
        }
    }

    private void constructParsingTableForNonTerminal(String nonTerminal){
        parsingTable.put(nonTerminal,new HashMap<>());
        for(String production:grammar.getRules().get(nonTerminal).getProductions()){
            constructParsingTableForProduction(nonTerminal,production);
        }
    }

    private void constructParsingTableForProduction(String nonTerminal,String production){
        ArrayList<String> symbols=new ArrayList<>(Arrays.asList(production.split("\\s")));
        HashSet<String> firsts=firstForExpression(symbols,0);



        if(firsts.contains("NULL")){
            HashSet<String> follows=allFollows.get(nonTerminal).getSetOfFollow();
            for(String terminal:follows){
                parsingTable.get(nonTerminal).put(terminal,symbols);
            }
            firsts.remove("NULL");
        }

        for(String terminal:firsts){
            parsingTable.get(nonTerminal).put(terminal,symbols);
        }


    }

    private HashSet<String> firstForExpression(ArrayList<String> symbols, int index){
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


    public void printParsingTable(){
        for(String terminal: grammar.getTerminals()){
            System.out.print("\t\t"+terminal);
        }
        System.out.println();

        for(String nonTerminal:grammar.getNonTerminals()){
            System.out.print(nonTerminal+"\t\t");

            for(String terminal:grammar.getTerminals()){
                if(parsingTable.get(nonTerminal).get(terminal)==null){
                    System.out.print("\t\t");
                    continue;
                }
                for(String symbol:parsingTable.get(nonTerminal).get(terminal)){
                    System.out.print(symbol+" ");
                }
                System.out.print("\t\t");
            }
            System.out.println();
        }
    }
}
