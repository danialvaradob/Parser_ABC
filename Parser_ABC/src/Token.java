/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author CASA
 */

public class Token {
    Types token;
    String lexema;
    int line = 0;
    
    int occurrences = 1;
    
    
    
    public String getLexema(){
        return this.lexema;
    }

    public Types getToken(){
        return this.token;
    }
    
    public void setType(Types t){
        this.token = t;
    }
   
    public void setLine(int _l){
        this.line = _l;        
    }
    
    public int getLine() {
        return this.line;
    }

    public Token (String _lexema, Types _token){
        this.lexema = _lexema;
        this.token = _token;
    }
    
    public Token (String _lexema, Types _token, int _line){
        this.lexema = _lexema;
        this.token = _token;
        this.line = _line+1;
    }
    
    public void addOccurrence() {
        this.occurrences++;
    }

    public void setOcurrences(int _occurrences) {
        this.occurrences = _occurrences;
    }
    
    public String toString(){
        return "Lexema: " + this.lexema + " Token: " + this.token.toString() +
                " Line: " + this.line +";";
    }
}
