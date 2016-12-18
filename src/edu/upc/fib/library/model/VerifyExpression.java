package edu.upc.fib.library.model;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyExpression {
    public static boolean verifyExpression(String sentenceToTest, String expressionToTest){
        Vector<String> expression_cut = new Sentence(expressionToTest).getVector();

        // Expresión regular para partir la frase en palabras, signos y espacios.
        Pattern pattern = Pattern.compile("([A-Za-z0-9'ÁáÄäÀàÉéËëÈèÍíÏïÌìÓóÖöÒòÚúÜüÙùÑñÇç-])+|[^ ]");
        Matcher matcher = pattern.matcher(sentenceToTest);
        Vector<String> expression_cut_2 = new Vector<>();
        while (matcher.find()) {
            if (!matcher.group().equals(" ")) {
                expression_cut_2.add(matcher.group());
            }
        }
        pattern = Pattern.compile("([A-Za-z'ÁáÄäÀàÉéËëÈèÍíÏïÌìÓóÖöÒòÚúÜüÙùÑñÇç-])+|[0-9]+|[^ ]");
        matcher = pattern.matcher(expressionToTest);
        Vector<String> expressionToTest_2 = new Vector<>();
        while (matcher.find()) {
            if (!matcher.group().equals(" ")) {
                expressionToTest_2.add(matcher.group());
            }
        }
        return verifyExpression_rec(expression_cut_2, expressionToTest_2);

    }

    private static boolean verifyExpression_rec(Vector<String> expression_cut, Vector<String> expressionToTest) {
        if (expression_cut.size() == 0){return false;}
        boolean negate_result= false;
        int prof = 0;//----------------------------------------------------------------------------------------cambiar por pilaaaa y subir a no recursiva!! ;)
        for (int i = 0; i < expression_cut.size(); i++) {
            if (prof < 0) {
                return false;//si se cierra un parentesi no abierto la expresión es incorrecta.
            }
            else if (expression_cut.elementAt(i).equals("(")) {
                prof++;
            }
            else if (expression_cut.elementAt(i).equals(")")) {
                prof--;
            }
        }
        if(prof != 0){return false;}//si la profundidad no es 0, falta un parentesis como minimo, expresion incorrecta
        int pos_op = 0;
        prof = 0;
        //empieza por paréntesi
        if (expression_cut.elementAt(pos_op).equals("(")) {
            prof++;
            for (int i = 1; i < expression_cut.size() || prof!=0; i++) {
                if(prof == 0){
                    pos_op = i;
                    i = expression_cut.size();
                }
                else if (expression_cut.elementAt(i).equals("(")) {
                    prof++;
                } else if (expression_cut.elementAt(i).equals(")")) {
                    prof--;
                }
                if(prof == 0){pos_op = i+1;i = expression_cut.size();}
            }
        }
        //pos_op contiene operando que separa 2 casos recursivos
        else if (expression_cut.elementAt(pos_op).equals("{")){//ha de tener todas las palabras de este conjunto de palabras
            for (int i = 0; i < expression_cut.size(); i++){
                if (expression_cut.elementAt(i).equals("}")){pos_op = i+1;}
            }
        }
        else if (expression_cut.elementAt(pos_op).equals("\"")){//ha de tener este conjunto de palabras tal cual
            for (int i = 1; i < expression_cut.size(); i++){
                if (expression_cut.elementAt(i).equals("\"")){pos_op = i+1;}
            }
        }

        if (pos_op >= expression_cut.size()){//principio y final es mismo parentesi o expresion[ "- - -", {- - -}, (- - -) ]
            //recortar esos signos y retornar llamada pues estamos en caso base
            // si hay paréntesis, podría haber mas operaciones internas, en cambio, los otros, son operacion molecular
            if (expression_cut.elementAt(0).equals("(")) {
                Vector<String> expression_cut_1 = new Vector<>();
                for (int i = 1; i < pos_op-1; ++i) {
                    expression_cut_1.add(expression_cut.get(i));
                }
                return verifyExpression_rec(expression_cut_1,expressionToTest);
            }
            if (expression_cut.elementAt(0).equals("{")) {return test_brackets(expression_cut, expressionToTest);}
            if (expression_cut.elementAt(0).equals("\"")) {return test_quotes(expression_cut, expressionToTest);}
        }
        else{//cortar y llamar con 2 trozos
            if(expression_cut.elementAt(0).equals("!")){
                if(expression_cut.elementAt(1).equals("(")){
                    prof =1;
                    for (int i = 2; i < expression_cut.size() || prof!=0; i++) {
                        if(prof == 0){
                            pos_op = i;
                            i = expression_cut.size();
                        }
                        else if (expression_cut.elementAt(i).equals("(")) {
                            prof++;
                        } else if (expression_cut.elementAt(i).equals(")")) {
                            prof--;
                        }
                        if(prof == 0){pos_op = i+1;i = expression_cut.size();}
                    }
                }
                else if(expression_cut.elementAt(1).equals("{")){
                    for(int i = 0; i < expression_cut.size() && pos_op == 0; i++){
                        if(expression_cut.elementAt(i).equals("}")){
                            pos_op = i+1; i =expression_cut.size();
                        }
                    }
                }
                else if(expression_cut.elementAt(1).equals("\"")){
                    for(int i = 0; i < expression_cut.size() && pos_op == 0; i++){
                        if(expression_cut.elementAt(i).equals("\"")){
                            pos_op = i+1; i =expression_cut.size();
                        }
                    }
                }
                else{//contiene solo un elemento simple a negar
                    pos_op = 2;
                }
                if(pos_op >= expression_cut.size()){//un elemento a negar
                    Vector<String> expression_cut_1 = new Vector<>();
                    for (int i = 1; i < pos_op; ++i) {
                        expression_cut_1.add(expression_cut.get(i));
                    }
                    return !verifyExpression_rec(expression_cut_1, expressionToTest);
                }
                else{//dos o mas
                    Vector<String> expression_cut_1 = new Vector<>();
                    for (int i = 1; i < pos_op; ++i) {
                        expression_cut_1.add(expression_cut.get(i));
                    }

                    Vector<String> expression_cut_2 = new Vector<>();
                    for (int i = pos_op + 1; i < expression_cut.size(); ++i) {
                        expression_cut_2.add(expression_cut.get(i));
                    }
                    if(expression_cut.elementAt(pos_op).equals("|")){return ( !verifyExpression_rec(expression_cut_1, expressionToTest) || verifyExpression_rec(expression_cut_2, expressionToTest) );}
                    else {return ( !verifyExpression_rec(expression_cut_1, expressionToTest) && verifyExpression_rec(expression_cut_2, expressionToTest) );}
                }
            }
            else{
                for(int i = 0; i < expression_cut.size() && pos_op == 0; i++){
                    if(expression_cut.elementAt(i).equals("|") || expression_cut.elementAt(i).equals("&")){
                        pos_op = i; i =expression_cut.size();
                    }
                }

                Vector<String> expression_cut_1 = new Vector<>();
                for (int i = 0; i < pos_op; ++i) {
                    expression_cut_1.add(expression_cut.get(i));
                }

                Vector<String> expression_cut_2 = new Vector<>();
                for (int i = pos_op + 1; i < expression_cut.size(); ++i) {
                    expression_cut_2.add(expression_cut.get(i));
                }

                if (expression_cut.elementAt(pos_op).equals("|")){
                    return ( verifyExpression_rec(expression_cut_1, expressionToTest) || verifyExpression_rec(expression_cut_2, expressionToTest) );
                }
                else if (expression_cut.elementAt(pos_op).equals("&")){
                    return ( verifyExpression_rec(expression_cut_1, expressionToTest) && verifyExpression_rec(expression_cut_2, expressionToTest) );
                }
            }
        }
        /*if(expression_cut.size() == 1){*/return expressionToTest.contains(expression_cut.elementAt(0));//}
        /*else {return false;//}*/
    }
    private static boolean test_quotes(Vector<String> expression_cut, Vector<String> expressionToTest) {//work
        boolean contents_all = false;
        for (int i = 0; i < expressionToTest.size() && !contents_all; i++) {
            //if(elementos coincidien)for{si llego al final return true}
            for(int j = 1; j < expression_cut.size()-1 && (i+j <= expressionToTest.size()) && (expressionToTest.elementAt(i+j-1).equals(expression_cut.elementAt(j))) ;j++) {
                if (j == (expression_cut.size() - 2)) {
                    contents_all = true;
                }
            }
        }
        return contents_all;
    }
    private static boolean test_brackets(Vector<String> expression_cut, Vector<String> expressionToTest) {//work
        boolean contents_all = true;
        for(int i = 1; i < expression_cut.size()-1 && contents_all; i++) {
            contents_all = expressionToTest.contains(expression_cut.elementAt(i));
        }
        return contents_all;
    }
}
