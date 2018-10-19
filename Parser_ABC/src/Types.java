/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CASA
 */
public enum Types {
    IDENTIFIER, 
    LOGICAL_OPERATOR, OPERATOR, 
    RESERVED, 
    FLOATING_POINT_NUMERIC_LITERAL, INTEGER_NUMERIC_LITERAL, 
    SCIENTIFIC_NOTATION_NUMERIC_LITERAL,
    STRING_LITERAL, CHAR_LITERAL,NUMERIC_CHAR_LITERAL,EOF,
    OTHER,
    
    ERROR_COMMENT,
    ERROR_FLOATING_POINT,
    ERROR_IDENTIFIER,
    ERROR_INTEGER,
    ERROR_STRING,
    ERROR_INVALID_CHARACTER,
    ERROR_INVALID_LENGTH
    
}
