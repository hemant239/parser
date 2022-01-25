package grammar;

import parser.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Grammar {

    private final HashMap<String,Rule> rules=new HashMap<>();
    private final HashSet<String> nonTerminals=new HashSet<>();
    private final HashSet<String> terminals=new HashSet<>();

    private String startSymbol;

    public Grammar(){

    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public void constructGrammarFromFile(String filename) throws IOException {
        File file=new File(filename);
        BufferedReader br=new BufferedReader(new FileReader(file));

        HashMap<String,HashSet<String>> productions=new HashMap<>();


        String line;
        int counter=0;
        while((line=br.readLine())!=null){

            if(counter==0){
                ArrayList<String> symbols=new ArrayList<>(Arrays.asList(line.split("[\\s,]+")));
                startSymbol=symbols.get(0);
                nonTerminals.addAll(symbols);
                counter++;
            }
            else if(counter==1){
                terminals.addAll(Arrays.asList(line.split("[\\s,]+")));
                counter++;
            }
            else{

                int index=line.indexOf("->");
                if(index<0){
                    System.out.println("Error in Grammar");
                }
                String leftSide=line.substring(0,index).trim();
                HashSet<String> rightSide=new HashSet<>();
                ArrayList<String> rightSideTemp=new ArrayList<>(Arrays.asList(line.substring(index+2).split("\\|")));

                for(String string:rightSideTemp){
                    rightSide.add(string.trim());
                }


                if(productions.containsKey(leftSide)){
                    productions.get(leftSide).addAll(rightSide);
                }
                else{
                    productions.put(leftSide,rightSide);
                }

            }
        }





        for(String symbol:productions.keySet()){
            Rule newRule=new Rule(symbol,productions.get(symbol));
            rules.put(symbol,newRule);
        }

    }

    public HashSet<String> getTerminals() {
        return terminals;
    }

    public HashSet<String> getNonTerminals() {
        return nonTerminals;
    }

    public HashMap<String, Rule> getRules() {
        return rules;
    }

    public void print(){
        System.out.println("Non terminals: "+nonTerminals);
        System.out.println("Terminals: "+terminals);
        System.out.println("Rules: ");
        for(Rule rule:rules.values()){
            rule.print();
        }
    }

    public void writeInFile(String filename) throws IOException {
        PrintWriter printWriter=new PrintWriter(new FileWriter(filename));

        printWriter.println("Non terminals: "+nonTerminals);
        printWriter.println("Terminals: "+terminals);
        printWriter.println("Rules: ");

        for(Rule rule:rules.values()){
            printWriter.print(rule.writeInFile()+"\n");
        }

    }


}
