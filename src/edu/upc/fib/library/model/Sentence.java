package edu.upc.fib.library.model;

import java.io.Serializable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sentence implements Serializable {
    private Vector<String> mWords; // Vector de las palabras de la frase
    private boolean isLineBreak;

    public Sentence(String sentence) {
        isLineBreak = false;
        // Expresión regular para partir la frase en palabras, signos y espacios.
        Pattern pattern = Pattern.compile("([A-Za-z'ÁáÄäÀàÉéËëÈèÍíÏïÌìÓóÖöÒòÚúÜüÙùÑñÇç-])+|[0-9]+|[^ ]|[\\r?\\n]");
        Matcher matcher = pattern.matcher(sentence);
        mWords = new Vector<>();
        while (matcher.find()) {
            if (matcher.group().equals("\n") || matcher.group().equals("\r\n")) {
                isLineBreak = true;
                mWords.add(matcher.group());
            } else if (!matcher.group().equals(" ")) {
                mWords.add(matcher.group());
            }
        }
    }

    public boolean isLineBreak() {
        return isLineBreak;
    }

    public int getSize() {
        return mWords.size();
    }

    public String getWord(int index) {
        return mWords.get(index);
    }

    public Vector<String> getVector() {
        return mWords;
    }

    public String toString() {
        // Usar StringBuilder ??????????????????????????????????????????????????????????????????????????
        String ret = "";
        boolean openQuotes = false;
        for (String word : mWords) {

            if (ret.equals("")) {
                ret = word;
                if (word.equals("\"")) openQuotes = true;
            } else if (word.equals("\"")) {
                if (openQuotes) {
                    ret += "\"";
                    openQuotes = false;
                } else {
                    ret += " \"";
                    openQuotes = true;
                }
            } else if (ret.substring(ret.length() - 1).equals("(") || ret.substring(ret.length() - 1).equals("«")
                    || ret.substring(ret.length() - 1).equals("¡") || ret.substring(ret.length() - 1).equals("¿")
                    //|| (ret.substring(ret.length() - 1).equals("\"") && ((ret.length() == 1) || (ret.length() > 1 && ret.substring(ret.length() - 2).equals(" "))))
                    || (ret.substring(ret.length() - 1).equals("\"") && openQuotes)
                    || word.equals("!") || word.equals("?") || word.equals(",") || word.equals(".")
                    || word.equals(":") || word.equals(")") || word.equals("»")) {
                // Controlar de otra forma los momentos en los que no hay que poner espacio
                ret += word;
            } else {
                ret += " " + word;
            }
        }
        return ret;
    }

//    // Needed?
//    // Revisar que funcione
//    @Override
//    public int compareTo(Sentence o) {
//        for (int i = 0; i < Math.min(getSize(), o.getSize()); i++) {
//            if (!mWords.get(i).equals(o.getWord(i)))
//                return mWords.get(i).toLowerCase().compareTo(o.getWord(i).toLowerCase());
//        }
//        if (getSize() < o.getSize()) return -1;
//        else if (getSize() > o.getSize()) return 1;
//        return 0;
//    }

}
