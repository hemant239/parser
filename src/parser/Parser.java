package parser;

import grammar.FirstAndFollow;
import grammar.Grammar;
import grammar.ParsingTable;

import javax.swing.plaf.synth.SynthLookAndFeel;
import java.io.IOException;
import java.util.*;

public class Parser {

    Stack<String> stack=new Stack<>();
    Grammar grammar;
    private HashMap<String, HashMap<String,ArrayList<String>>> parsingTable=new HashMap<>();


    public Parser() throws IOException {
        grammar=new Grammar();
        grammar.constructGrammarFromFile("C:\\Users\\HEMANT CHAUDHARY\\Desktop\\Parser\\LL1parser\\src\\grammar\\sample.txt");

        System.out.println("The Parsing table is:  ");
        parsingTable=(new ParsingTable(grammar)).getParsingTable();





    }

    private void parse(ArrayList<String> input){
        int index=0;
        stack.push("$");
        stack.push(grammar.getStartSymbol());

        while(!stack.isEmpty()){
            String terminal=input.get(index);
            String top=stack.peek();
            if(top.equals("NULL")){
                stack.pop();
                continue;
            }


            if(top.equals("$")||grammar.getTerminals().contains(top)){
                if(terminal.equals(top)){
                    index++;
                    stack.pop();
                    continue;
                }
                else{
                    error();
                    return;
                }
            }
            if(parsingTable.get(top).containsKey(terminal)){
                stack.pop();
                ArrayList<String> symbols=parsingTable.get(top).get(terminal);
                for(int i=symbols.size()-1;i>=0;i--){
                    stack.add(symbols.get(i));
                }

            }
            else{
                error();
                return;
            }
        }
        System.out.println("Success");
    }

    private void error(){
        System.out.println("ERROR");

    }

    public static void main(String[] args) throws IOException {
        Parser parser=new Parser();
        String inputString="id + ( id * ( id ) * id + id  $";
        ArrayList<String> input=new ArrayList<>(Arrays.asList(inputString.split("\\s")));


        System.out.println("\nThe result for input String : "+inputString+" is ");

        parser.parse(input);

    }

}
