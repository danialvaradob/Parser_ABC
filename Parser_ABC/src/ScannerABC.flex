/* Secci�n de declaraciones de JFlex */
%%
%caseless 
%line
%ignorecase
%public
%class ScannerABC
%{
 
 /* C�digo personalizado */
 
 // Se agreg� una propiedad para verificar si existen tokens pendientes
 private boolean _existenTokens = false;
 
 public boolean existenTokens(){
 return this._existenTokens;
 }
 
%}
 
 /* Al utilizar esta instrucci�n, se le indica a JFlex que devuelva objetos del tipo TokenPersonalizado */
%type Token
 
%init{
 /* C�digo que se ejecutar� en el constructor de la clase */
%init}
 
%eof{
 
 /* C�digo a ejecutar al finalizar el an�lisis, en este caso cambiaremos el valor de una variable bandera */
 this._existenTokens = false;
 
%eof}
 
/* Inicio de Expresiones regulares */
 
 Space = " "
 NewLine = \n|\r|\r\n
 
NewLine         =       \n|\r|\r\n
InputChar       =       [^\n\r]
SpaceChar       =       [\ | \t]*
LineChar        =       \n | \r | \r | \n  

   
Alpha           =       [A-Za-z_] 
Digit           =       [0-9]
AlphaNumeric    =       {Alpha}|{Digit}
Identifier      =       {Alpha}({AlphaNumeric})*
Number          =       ({Digit})+
WhiteSpace      =       ([\ \n\r\t\f])+ 
Zero            =       0
Integer         =       [1-9][0-9]*(\.){Zero} | {Zero} \. {Zero}
Float1          =       [0-9]+ \. [0-9]+

Exponent        =       [E] [\+ \-]? [0-9]+
ScienNot        =       {Integer}|{Float1} {Exponent} 
Float1          =       [0-9]+ \. [0-9]+ {Exponent}?
Float2          =       \. [0-9]+ {Exponent}?
Float3          =       [0-9]+ \. {Exponent}? 
Float           =       ( {Float1} | {Float2} | {Float3} )


LogicalOp       =       (AND)|(OR)|(NOT)|(XOR)|(DIV)|(MOD)

BlockComment    =       \( \* ([^\}]|{NewLine})* \* \) | \{ ([^\}]|{NewLine})* \}
LineComment     =       \/ \/ (.)*

ReservedWords   =       (ARRAY)|(BEGIN)|(BOOLEAN)|(BYTE)|(CASE)|(CHAR)|
                        (CONST)|(DO)|(DOWNTO)|(ELSE)|(END)|(FALSE)|(FILE)|
                        (FOR)|(FORWARD)|(FUNCTION)|(GOTO)|(IF)|(IN)|(INLINE)|
                        (INT)|(LABEL)|(LONGINT) |(NIL)|(OF)|
                        (PACKED)|(PROCEDURE)|(PROGRAM)|(READ)|(REAL)|(RECORD)|
                        (REPEAT)|(SET)|(SHORTINT)|(STRING)|(THEN)|(TO)|(TRUE)|
                        (TYPE)|(UNTIL)|(VAR)|(WHILE)|(WITH)|(WRITE)

Operators       =       (\,)|(\;)|(\++)|(\--)|(\>=)|(\>)|(\<=)|(\<)|
                        (\<>)|(\=)|(\+)|(\-)|(\*)|(\/)|(\()|(\))|
                        (\[)|(\])|(\:=)|(\.)|(\:)|(\+=)|(\-=)|(\*=)|
                        (\/=)|(\>>)|(\<<)|(\<<=)|(\>>=)

Symbols         =       (\@) | (\#) | (\%) | (\$)
                        | (\^) | (\&) | (\() | (\)) 
                        | (\º) | (\á) | (\!) | (\¿)
                        | (\¡) | (\*)

InvalidSymbols  =       (\@) | (\%) | (\$)
                        | (\^) | (\&)
                        | (\º) | (\á) | (\!) | (\¿)
                        | (\¡)



String          =       \" ([^\"] |{NewLine})* \"
Char            =       \" ([^\"] |{NewLine}) \"

NumericChar     =       \# {Number}

Null            =       \0
//EOF             =       <<EOF>>



//Errors
FloatError1     =       [0-9]+ \. 
FloatError2     =       (\. {Number})

ExponentError        =       [E] [\+ \-]? Float1
ScienNotError        =       {Integer}|{Float1} [\E] {Digit}  [\.] \{Digit} ]



IdentifierError =       ({Digit}|{Char} | {String} | {ScienNot} | {Float1} | 
                        {NumericChar} | {Symbols})+

                        {Alpha}+ | {Symbols}(Idenfitifer)

IntegerError    =       {Digit}+
StringError     =       \" ([^\"] |{NewLine})* 

InvalidCharacter =      ({Alpha})*({InvalidSymbols})+({AlphaNumeric})*

CommentError    =       \( \* ([^\*)]|{NewLine})* | \{ ([^\}]|{NewLine})*

NoMatch         =       (.)




 
%%
{SpaceChar} {
    //Espacios y tabuladores
}


{CommentError} {
    Token t = new Token(yytext(), Types.ERROR_COMMENT, yyline);
    this._existenTokens = true;
    return t;
}

{InvalidCharacter} {
    Token t = new Token(yytext(), Types.ERROR_INVALID_CHARACTER, yyline);
    this._existenTokens = true;
    return t;
}


{IdentifierError} {
    Token t = new Token(yytext(), Types.ERROR_IDENTIFIER, yyline);
    this._existenTokens = true;
    return t;
}

{NumericChar} {
    Token t = new Token(yytext(), Types.NUMERIC_CHAR_LITERAL, yyline);
    this._existenTokens = true;
    return t;
}

{Char} {
    Token t = new Token(yytext(), Types.CHAR_LITERAL, yyline);
    this._existenTokens = true;
    return t;
}

{String} {
    Token t = new Token(yytext(), Types.STRING_LITERAL, yyline);
    this._existenTokens = true;
    return t;
}

{StringError} {
    Token t = new Token(yytext(), Types.ERROR_STRING, yyline);
    this._existenTokens = true;
    return t;
}


{LogicalOp} {
    Token t = new Token(yytext(), Types.LOGICAL_OPERATOR, yyline);
    this._existenTokens = true;
    return t;
}

{ReservedWords} {
    Token t = new Token(yytext(), Types.RESERVED, yyline);
    this._existenTokens = true;
    return t;
}

{Integer} {
    Token t = new Token(yytext(), Types.INTEGER_NUMERIC_LITERAL, yyline);
    this._existenTokens = true;
    return t;
}


{ScienNotError} {
    Token t = new Token(yytext(), Types.ERROR_FLOATING_POINT, yyline);
    this._existenTokens = true;
    return t;
}

{ScienNot} {
    Token t = new Token(yytext(), Types.SCIENTIFIC_NOTATION_NUMERIC_LITERAL, yyline);
    this._existenTokens = true;
    return t;
}

 
{Identifier} {
    Token t = new Token(yytext(), Types.IDENTIFIER, yyline);
    if (yytext().length()>127){
        t.setType(Types.ERROR_INVALID_LENGTH);
    }
     this._existenTokens = true;
     return t;
}
 

{FloatError1} {
    Token t = new Token(yytext(), Types.ERROR_FLOATING_POINT, yyline);
    this._existenTokens = true;
    return t;
}

{FloatError2} {
    Token t = new Token(yytext(), Types.ERROR_FLOATING_POINT, yyline);
    this._existenTokens = true;
    return t;
}

{Float} {
    Token t = new Token(yytext(), Types.FLOATING_POINT_NUMERIC_LITERAL, yyline);
    this._existenTokens = true;
    return t;
}

/*{EOF} {
 Token t = new Token(yytext(), Types.EOF);
 this._existenTokens = true;
 return t;
}*/



{Operators} {
    Token t = new Token(yytext(), Types.OPERATOR, yyline);
    this._existenTokens = true;
    return t;
}

{IntegerError} {
    Token t = new Token(yytext(), Types.ERROR_INTEGER, yyline);
    this._existenTokens = true;
    return t;
}
 

{BlockComment} {
 // Comentario de bloque 1
}
{LineComment} {
 // Comentario de bloque 1
}
{Space} {
 // Ignorar cuando se ingrese un espacio
}
 
{NewLine} {
 /*Token t = new Token("Enter", Types.IDENTIFIER);
 this._existenTokens = true;
 return t;*/
}

{NoMatch} {
    Token t = new Token(yytext(), Types.ERROR_INVALID_CHARACTER, yyline);
    this._existenTokens = true;
    return t;
}