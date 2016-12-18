package edu.upc.fib.library.model;

import java.io.Serializable;
import java.util.*;

public class Authors implements Serializable {
    private TreeMap<String, Author> mAuthors; // Estructura que contiene todos los autores <autor, Author>

    public Authors() {
        mAuthors = new TreeMap<>();
    }

    // Gestión de autores

    public boolean addAuthor(String authorName) {
        if (!existsAuthor(authorName)) {
            mAuthors.put(authorName, new Author(authorName));
            return true;
        }
        return false;
    }

    public boolean modifyAuthor(String authorName, String newAuthorName) {
        if (existsAuthor(authorName)) {
            Author newAuthor = mAuthors.get(authorName);
            newAuthor.modifyName(newAuthorName);
            mAuthors.remove(authorName);
            mAuthors.put(newAuthorName, newAuthor);
            return true;
        }
        return false;
    }

    public boolean removeAuthor(String authorName, Documents documents) {
        if (existsAuthor(authorName)) {
            // Delete all his documents
            List<String> titles = new ArrayList<>(getAuthor(authorName).getDocumentTitles());
            for (String title : titles) {
                documents.removeDocument(getAuthor(authorName), title);
            }
            mAuthors.remove(authorName);
            return true;
        }
        return false;
    }

    // Métodos auxiliares de gestión de autores

    public boolean existsAuthor(String authorName) {
        return mAuthors.containsKey(authorName);
    }

    // Consultas de autores

    public Author getAuthor(String authorName) {
        return mAuthors.get(authorName);
    }

    public Set<String> getAuthorNames() {
        return mAuthors.keySet();
    }

    public Set<String> getAuthorDocumentTitles(String author) {
        return getAuthor(author).getDocumentTitles();
    }

    public SortedMap<String, Author> getAuthorsByPrefix(String prefix){
        if (prefix == null) return mAuthors;
        return mAuthors.subMap(prefix, prefix + Character.MAX_VALUE);
    }

}
