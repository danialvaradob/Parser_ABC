
import java.awt.print.PrinterException;
import java.util.ArrayList;
import javax.swing.JTable;
import static jdk.nashorn.internal.objects.NativeString.toLowerCase;
import static jdk.nashorn.internal.objects.NativeString.toUpperCase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author danielalvarado
 */
public class TokenTable {
    private static ArrayList<Token> tokens = new ArrayList<>();
    
    
    private ArrayList<Token> noRepeatedTokens = new ArrayList<>();
    
    private static String[][] data;
    private static String[] columnNames = {"Token",
                        "Tipo de Token",
                        "Linea"};
    
    private ArrayList<String> lexemas = new ArrayList<>();
    
    public  JTable tokensTable;
    


    public TokenTable() {}
    
    
    public TokenTable(ArrayList<Token> _tokens) {
        tokens = _tokens;
        
    }
    
    public void addToken(Token _token) {
        tokens.add(_token);
    }
    
    
    private void addAllLineOccurrences() {
        int size = tokens.size();
        Token t1,t2;
        
        lexemas = new ArrayList<>();
      
        // esta parte le cambia la cantidad de veces que aparece un token por linea
        // es decir si aparece while dos veces, cada token va a tener que en esa linea
        // tiene occurrences = 2 
        for (int i = 0; i < size; i++) {
            t1 = tokens.get(i);
            int line = t1.getLine();
            
            // busca las repeticiones de ese mismo token
            for (int j = 0; j < size; j++) {
                t2 = tokens.get(j);
                
                // si se repite en la misma linea
                if ((t2.getLine() == line)&& ( toUpperCase(t1.getLexema()).equals(toUpperCase(t2.getLexema()))) && !(t1.equals(t2))  ) {
                    
                    
                    
                    t1.addOccurrence();
                }
                if (!lexemas.contains(toLowerCase(t2.getLexema()))) {
                        lexemas.add(toLowerCase(t2.getLexema()));
                    }
            }            
        }
    }
    
    /**
     * Retorna nada mas si mabos tokens tienen el mismo lexema y estan en la misma lina
     * retorna true
     * @return 
     */
    private boolean tokenInLine(Token t1, Token t2) {
        String lex1,lex2;
        int l1,l2;
        
        boolean condition1,condition2;
        
        
        lex1 = toUpperCase(t1.getLexema());
        lex2 = toUpperCase(t2.getLexema());
        l1 = t1.getLine();
        l2 = t2.getLine();
        
        condition1 = (toUpperCase(t1.getLexema()).equals(toUpperCase(t2.getLexema())));
        condition2 = (t1.getLine() == t2.getLine());
        
        
        return condition1 
                && condition2;
    }
    
    
    private void elimRepeatedTokens() {
        
        Token t1,t2;
        
        for (int i = 0; i < tokens.size(); i++) {
            boolean bandera = true;
            t1 = tokens.get(i);
            if (i == 0) {this.noRepeatedTokens.add(t1);}     
            // busca las repeticiones de ese mismo token
            
            
            for (int j = 0; j < this.noRepeatedTokens.size(); j++) {
                t2 = noRepeatedTokens.get(j);
                
                if (noRepeatedTokens.contains(t1)) {   
                    bandera = false;
                }
               if ((t1.getLine() == t2.getLine()) && (toUpperCase(t1.getLexema()).equals(toUpperCase(t2.getLexema()))) ) {
                    bandera = false;
                }
            }
            
            
            if (bandera) {
                this.noRepeatedTokens.add(t1);
                
            }
        }
               
    }
    
    //CAMBIAR A PRIVADO
    public void createTable() {
        
        this.addAllLineOccurrences();
        this.elimRepeatedTokens();
        
        String lexema,type,occrr;
        occrr = " ";
        type = " ";
        lexema = " ";
        Token t1,t2;
        
        //list of used tokens 
        ArrayList<String> usedLexemas = new ArrayList<>();
        
        
        data = new String[lexemas.size()][3];
        
        
        
        for (int token = 0; token < lexemas.size(); token ++) {
            
            for (int i = 0; i < this.noRepeatedTokens.size(); i++) {
                //token el cual se esta analizando
                t1 = noRepeatedTokens.get(i);
                // se obtiene el lexema
                lexema = toLowerCase(t1.getLexema());
    
                //se obtienen la linea
                occrr = Integer.toString(t1.getLine());
                //obtiene el tipo
                type = t1.getToken().toString();    
                //si tiene mas de una ocurrencia encontrado en la funcion previa
                // se agrega a occrr
                if (t1.occurrences > 1) {
                    occrr += "(" + t1.occurrences + ")";
                }
                if (!usedLexemas.contains(lexema)) {
                    usedLexemas.add(lexema);
                    // busca las repeticiones de ese mismo token
                    for (int j = 0; j < this.noRepeatedTokens.size(); j++) {
                        t2 = noRepeatedTokens.get(j);
                        // si es el mismo lexema pero no en la misma linea, lo agrega al string de repeticiones
                        if (!(t2.equals(t1)) &&  (toUpperCase(t1.getLexema()).equals(toUpperCase(t2.getLexema())))){
                            occrr += "," + t2.getLine();
                            if (t2.occurrences > 1) {
                                occrr += "(" + t2.occurrences + ")";
                            }
                        }              
                    }
                    String prueba = "prueba";
                  //PRINTS IN CONSOLE
                   
                   System.out.println(lexema + "  |  " + type + "   |   " + occrr);
             // lexema
                   data[token][0] = lexema;
                   // tipo de token (type)
                   data[token][1] = type;
                   // apareiciones en las lineas
                   data[token][2] = occrr;
                    
                    
 
                }
            }
            
                
            
                   
                   
                   
                   
        }
        tokensTable = new JTable(data, columnNames);

    }
    
    /*
    
    String[] columnNames = {"Token",
                        "Tipo de Token",
                        "Linea"};
Its data is initialized and stored in a two-dimensional Object array:

Object[][] data = {
    {"Kathy", "Smith",
     "Snowboarding", new Integer(5), new Boolean(false)},
    {"John", "Doe",
     "Rowing", new Integer(3), new Boolean(true)},
    {"Sue", "Black",
     "Knitting", new Integer(2), new Boolean(false)},
    {"Jane", "White",
     "Speed reading", new Integer(20), new Boolean(true)},
    {"Joe", "Brown",
     "Pool", new Integer(10), new Boolean(false)}
};
    
    */
    
    
    public void printTable() throws PrinterException {
        createTable();
        //System.out.print( this.tokensTable.toString());
    }
    
}
