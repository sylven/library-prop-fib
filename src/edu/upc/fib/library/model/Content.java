package edu.upc.fib.library.model;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Content implements Serializable {
    private Vector<Sentence> mContent;

    public Content(String content) {
        mContent = new Vector<>();

        Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)|[\\r?\\n]", Pattern.MULTILINE | Pattern.COMMENTS);
        Matcher reMatcher = re.matcher(content);
        while (reMatcher.find()) {
            mContent.add(new Sentence(reMatcher.group()));
        }
    }

    public Hashtable<String, Double> getWordFrequency(Vector<String> connectorWords){
        Hashtable<String, Double> wordFrequency = new Hashtable<>();
        double maxFrequency = 0;
        for (Sentence sentence : mContent) {
            for (int i = 0; i < sentence.getSize(); i++) {
                String word = sentence.getWord(i);
                // Si no es una palabra funcional y su longitud es mayor a 1 carácter => la guardamos
                if(!connectorWords.contains(word.toLowerCase()) && word.length() > 1) {
                    if (wordFrequency.containsKey(word)) {
                        wordFrequency.put(word.toLowerCase(), wordFrequency.get(word) + 1);
                    } else {
                        wordFrequency.put(word.toLowerCase(), (double) 1);
                    }
                    if (wordFrequency.get(word.toLowerCase()) > maxFrequency) {
                        maxFrequency = wordFrequency.get(word.toLowerCase());
                    }
                }
            }
        }
        for (Map.Entry<String, Double> entry : wordFrequency.entrySet()) {
            wordFrequency.replace(entry.getKey(), entry.getValue()/maxFrequency);
        }
        return wordFrequency;
    }

    public String toString() {
        String content = "";
        boolean afterBreak = true;
        for (Sentence sentence : mContent) {
            if (!afterBreak && !sentence.isLineBreak()) content += " ";
            content += sentence.toString();
            if (sentence.isLineBreak()) afterBreak = true;
            else afterBreak = false;
        }
        return content;
    }

    public Vector<String> toStrings() {
        Vector<String> vContent = new Vector<>();
        for (Sentence sentence : mContent) {
            vContent.add(sentence.toString());
        }
        return vContent;
    }
}