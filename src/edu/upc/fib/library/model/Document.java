package edu.upc.fib.library.model;

import javax.print.Doc;
import java.io.Serializable;
import java.util.*;

public class Document implements Comparable<Document>, Serializable {
    private Author mAuthor;
    private Sentence mTitle;
    private Content mContent;
    private Vector<String> mConnectorWords;

    private Hashtable<String, Double> mWordFrequency; // Frecuencia de cada palabra en este documento
    private Hashtable<String, Double> mWordsWeight; // Peso de cada palabra en este documento
    //private HashMap<Document, Integer> mSimilarDocuments; // Documentos parecidos a este

    public Document(Author author, String title, String content, Vector<String> connectorWords) {
        mAuthor = author;
        mTitle = new Sentence(title);
        mContent = new Content(content);
        mConnectorWords = connectorWords;
        calculateWordFrequency();
    }

    public void calculateWordFrequency() {
        mWordFrequency = mContent.getWordFrequency(mConnectorWords);
    }

    public void calculateWordsWeight(Hashtable<String, Double> inverseDocumentFrequency) {
        Hashtable<String, Double> newWordsWeight = new Hashtable<>();
        for (Map.Entry<String, Double> entry : mWordFrequency.entrySet()) {
            newWordsWeight.put(entry.getKey(), (0.5 + 0.5 * entry.getValue()) * inverseDocumentFrequency.get(entry.getKey()));
        }
        mWordsWeight = newWordsWeight;
    }

    public Sentence getTitle() {
        return mTitle;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public Content getContent() {
        return mContent;
    }

    public void setAuthor(Author author) {
        mAuthor = author;
    }

    public void setTitle(Sentence title) {
        mTitle = title;
    }

    public void setContent(Content content) {
        mContent = content;
        calculateWordFrequency();
    }

    public Hashtable<String, Double> getWordFrequency() {
        return mWordFrequency;
    }

    public double getCosinus(Document document) {
        double sum = 0;
        double sumD1 = 0;
        double sumD2 = 0;
        List<String> alreadyProcessedWords = new ArrayList<>();

        //if (mWordsWeight.size() < document.mWordsWeight.size()) {
        for (Map.Entry<String, Double> entry : mWordsWeight.entrySet()) {
            if (document.mWordsWeight.containsKey(entry.getKey())) {
                sum += (entry.getValue() * document.mWordsWeight.get(entry.getKey()));
                sumD2 += Math.pow(document.mWordsWeight.get(entry.getKey()), 2);
            }
            sumD1 += Math.pow(entry.getValue(), 2);
            alreadyProcessedWords.add(entry.getKey());
        }
        for (Map.Entry<String, Double> entry : document.mWordsWeight.entrySet()) {
            if (!alreadyProcessedWords.contains(entry.getKey())) {
                sumD2 += entry.getValue();
            }
        }
        return sum/(Math.sqrt(sumD1) * Math.sqrt(sumD2));
        //}
        //return 0;
    }

    @Override
    public int compareTo(Document o) {
        if (!mTitle.toString().equals(o.getTitle().toString())) {
            return mTitle.toString().compareTo(o.getTitle().toString());
        } else if (!mAuthor.getName().toString().equals(o.getAuthor().getName().toString())) {
            return mAuthor.getName().toString().compareTo(o.getAuthor().getName().toString());
        }
        return 0;
    }


//    public void calculateInverseDocumentFrequency() {
//        for(Map.Entry<String, Integer> word : mWordFrequency.entrySet()) {
//
//            mWordsWeight.put(word.getKey(), Math.log10(mDocuments.size()/word.getValue()));
//        }
//    }
//


}