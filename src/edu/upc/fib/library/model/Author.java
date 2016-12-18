package edu.upc.fib.library.model;

import javax.print.Doc;
import java.io.Serializable;
import java.util.*;

public class Author implements Comparable<Author>, Serializable {
    private Sentence mName; // Nombre del autor
    //private String mName;
    //private Vector<Document> mDocuments; // Lista de documentos de los que es autor
    //private Hashtable<String, Document> mDocuments; // Lista de documentos de los que es autor <titulo, Document>
    private TreeMap<String, Document> mDocuments; // Lista de documentos de los que es autor <titulo, Document>

    public Author(String name) {
        mName = new Sentence(name);
        //mName = name;
        //mDocuments = new Vector<>();
        //mDocuments = new Hashtable<>();
        mDocuments = new TreeMap<>();
    }

    // Gestión del autor

    public void modifyName(String newName) {
        mName = new Sentence(newName);
        //mName = newName;
    }

    public boolean addDocument(Document document) {
        // Comprobar que un documento con el mismo título no existe ya ???????????????????????????????????????
        String title = document.getTitle().toString();
        if (!mDocuments.containsKey(title)) {
            mDocuments.put(title, document);
            return true;
        }
        return false;
    }

    public boolean removeDocument(String title){
        if (mDocuments.containsKey(title)) {
            mDocuments.remove(title);
            return true;
        }
        return false;
    }

    public Sentence getName() {
    //public String getName() {
        return mName;
    }

    public Set<String> getDocumentTitles() {
        return mDocuments.keySet();
    }

    @Override
    public int compareTo(Author o) {
        return mName.toString().compareTo(o.getName().toString());
        //return mName.compareTo(o.getName());
    }

    public SortedMap<String, Document> getAuthorDocumentsByPrefix(String titlePrefix){
        if (titlePrefix == null) return mDocuments;
        return mDocuments.subMap(titlePrefix, titlePrefix + Character.MAX_VALUE);
    }

}