package edu.upc.fib.library.model;

import javafx.util.Pair;
import sun.reflect.generics.tree.Tree;

import javax.print.Doc;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Documents implements Serializable {
    //private TreeMap<String, Vector<Document>> mDocuments; // Vector de documentos con el mismo título
    //private TreeMap<String, HashMap<String, Document>> mDocuments; // Vector de documentos con el mismo título <título, <autor, Document>>
    private TreeMap<String, Hashtable<Author, Document>> mDocuments; // Vector de documentos con el mismo título <título, <Author, Document>>
    //private TreeMap<String, String> mDocumentTitles;

    private Hashtable<String, Double> mDocumentFrequency; // Frecuencia de cada palabra en todos los documentos <palabra, apariciones>

    private Hashtable<String, Double> mInverseDocumentFrequency; // Peso de cada palabra en todos los documentos

    // MEJORABLE ????????????????????????????????????????????????????????????????
    private Hashtable<String, Vector<Document>> mWordDocuments; // Documentos en los que la palabra aparece <título, Vector<Document>>

    private Vector<String> connectorWords; // Lista de palabras funcionales

    public Documents() {
        mDocuments = new TreeMap<>();
        //mDocumentTitles = new TreeMap<>();
        mDocumentFrequency = new Hashtable<>();
        mWordDocuments = new Hashtable<>();
        mInverseDocumentFrequency = new Hashtable<>();

        // Cargar palabras funcionales
        connectorWords = new Vector<>();
        InputStream iS = this.getClass().getResourceAsStream("/files/connector-words.sp");
        if (iS != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(iS))) {
                String line = br.readLine();
                while (line != null) {
                    connectorWords.add(new String(line).toLowerCase());
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Gestión de documentos

    public boolean addDocument(Author author, String title, String content) {
        Document newDocument = new Document(author, title, content, connectorWords);
        if (author.addDocument(newDocument)) {
            //Vector<Document> vDocuments;
            //HashMap<String, Document> hDocuments;
            Hashtable<Author, Document> hDocuments;
            if (mDocuments.containsKey(title)) {
                hDocuments = mDocuments.get(title);
            } else {
                //hDocuments = new Vector<>();
                hDocuments = new Hashtable<>();
            }
            //hDocuments.add(newDocument);
            //hDocuments.put(author.getName().toString(), newDocument);
            hDocuments.put(author, newDocument);
            mDocuments.put(title, hDocuments);
            //if (!mDocumentTitles.containsKey(title)) mDocumentTitles.put(title, title);

            // Puede ser que no envie la direccion de memoria que tendra en mDocuments? => FIX IT
            updateWordFrequency(newDocument, true);
            return true;
        }
        return false;
    }

    //> Actualiza mDocumentFrequency y mWordDocuments
    // Recalcular la frecuencia global de todas las palabras despues de cada modificacion no es eficiente
    // Esta funcion se usara para añadir y quitar frecuencias
    // Cuando haya modificaciones o borrados, se llamara primero a esta funcion para quitar la frencuencia de las palabras
    public void updateWordFrequency(Document document, boolean increase) {
        Hashtable<String, Double> wordFrequency = document.getWordFrequency();
        for (Map.Entry<String, Double> entry : wordFrequency.entrySet()) {
            String word = entry.getKey();
            // Cuantas veces aparece cada palabra en todos los documentos?
            if (!increase) {
                if (mDocumentFrequency.get(word) == 1) {
                    mDocumentFrequency.remove(word);
                } else {
                    mDocumentFrequency.put(word, mDocumentFrequency.get(word) - 1);
                }
            } else if (mDocumentFrequency.containsKey(word)) {
                mDocumentFrequency.put(word, mDocumentFrequency.get(word) + 1);
            } else {
                mDocumentFrequency.put(word, (double) 1);
            }
            // En que documentos aparece cada palabra?
            if (!increase) {
                if (mWordDocuments.get(word).size() == 1) {
                    mWordDocuments.remove(word);
                } else {
                    Vector<Document> newVDocuments = mWordDocuments.get(word);
                    newVDocuments.remove(document);
                    mWordDocuments.put(word, newVDocuments);
                }
            } else if (mWordDocuments.containsKey(word)) {
                Vector<Document> newVDocuments = mWordDocuments.get(word);
                newVDocuments.add(document);
                mWordDocuments.put(word, newVDocuments);
            } else {
                Vector<Document> newVDocuments = new Vector<>();
                newVDocuments.add(document);
                mWordDocuments.put(word, newVDocuments);
            }
        }
        // Cual es el peso global de cada palabra?
        calculateInverseDocumentFrequency();

        // Actualizar el peso de todas las palabras dentro de cada document
        if (increase) {
            for (Map.Entry<String, Hashtable<Author, Document>> titleSet : mDocuments.entrySet()) {
                for (Map.Entry<Author, Document> authorSet : titleSet.getValue().entrySet()) {
                    authorSet.getValue().calculateWordsWeight(mInverseDocumentFrequency);
                }
            }
        }
    }

    public void calculateInverseDocumentFrequency() {
        Hashtable<String, Double> newInverseDocumentFrequency = new Hashtable<>();
        for(Map.Entry<String, Double> word : mDocumentFrequency.entrySet()) {
            newInverseDocumentFrequency.put(word.getKey(), Math.log10(mDocuments.size()/word.getValue()));
        }
        mInverseDocumentFrequency = newInverseDocumentFrequency;
    }

    public void modifyDocumentAuthor(Author author, String title, Author newAuthor) {
        Hashtable<Author, Document> newDocuments = mDocuments.get(title);
        Document document = newDocuments.get(author);
        document.setAuthor(newAuthor);
        newDocuments.put(newAuthor, document);
        newDocuments.remove(author);
        mDocuments.replace(title, newDocuments);

        author.removeDocument(title);
        newAuthor.addDocument(document);
    }

    public void modifyDocumentTitle(Author author, String title, String newTitle) {
        Document document = mDocuments.get(title).get(author);
        document.setTitle(new Sentence(newTitle));

        // Borrar documento de donde estaba
        mDocuments.get(title).remove(author);
        if (mDocuments.get(title).size() == 0) {
            mDocuments.remove(title);
            //mDocumentTitles.remove(title);
        }

        // Añadir el documento donde tiene que estar
        Hashtable<Author, Document> newDocuments;
        if (mDocuments.containsKey(newTitle)) {
            newDocuments = mDocuments.get(newTitle);
        } else {
            newDocuments = new Hashtable<>();
        }
        newDocuments.put(author, document);
        mDocuments.put(newTitle, newDocuments);
        //mDocumentTitles.put(newTitle, newTitle);

        // Modificarlo dentro del autor
        author.removeDocument(title);
        author.addDocument(document);
    }

    public void modifyDocumentContent(Author author, String title, String newContent) {
        updateWordFrequency(mDocuments.get(title).get(author), false);
        mDocuments.get(title).get(author).setContent(new Content(newContent));
        updateWordFrequency(mDocuments.get(title).get(author), true);
    }

    public boolean removeDocument(Author author, String title) {
        if (mDocuments.containsKey(title)) {
            if (mDocuments.get(title).containsKey(author)) {
                Document removedDocument = mDocuments.get(title).get(author);
                updateWordFrequency(removedDocument, false);
                mDocuments.get(title).remove(author);

                if (mDocuments.get(title).size() == 0) {
                    mDocuments.remove(title);
                    //mDocumentTitles.remove(title);
                }

                author.removeDocument(title);
                return true;
            }
        }
        return false;
    }

    public Set<String> getDocumentTitles() {
        return mDocuments.keySet();
    }

    public List<Pair<String, String>> getDocumentInfos() {
        List<Pair<String, String>> response = new ArrayList<>();
        for (Map.Entry<String, Hashtable<Author, Document>> titleSet: mDocuments.entrySet()) {
            String title = titleSet.getKey();
            for (Map.Entry<Author, Document> authorSet : titleSet.getValue().entrySet()) {
                response.add(new Pair(title, authorSet.getKey().getName().toString()));
            }
        }
        return response;
    }

	public Vector<String> getDocumentContent_vector(Author author, String title) {
        return mDocuments.get(title).get(author).getContent().toStrings();
    }

    public String getDocumentContent(Author author, String title) {
        return mDocuments.get(title).get(author).getContent().toString();
    }

    public List<Map.Entry<Document, Double>> getSimilarDocuments(Author author, String title) {
        TreeMap<Document, Double> similarDocuments = new TreeMap<>();
        Document document = mDocuments.get(title).get(author);
        for (Map.Entry<String, Hashtable<Author, Document>> titleSet : mDocuments.entrySet()) {
            for (Map.Entry<Author, Document> authorSet : titleSet.getValue().entrySet()) {
                if (authorSet.getValue() != document) {
                    similarDocuments.put(authorSet.getValue(), authorSet.getValue().getCosinus(document));
                }
            }
        }

        // Convertir a List y ordenar por valor
        List<Map.Entry<Document, Double>> similarDocumentsList = new ArrayList(similarDocuments.entrySet());
        Collections.sort(similarDocumentsList, (obj1, obj2) -> ((Comparable)((Map.Entry)(obj2)).getValue()).compareTo(((Map.Entry)(obj1)).getValue()));

        return similarDocumentsList;
    }

    /*public Hashtable<Author, Document> getDocumentsByTitle(String title) {
        return mDocuments.get(title);
    }*/

    public boolean existsDocument(Author author, String title) {
        if (mDocuments.containsKey(title)) {
            if (mDocuments.get(title).containsKey(author)) {
                return true;
            }
        }
        return false;
	}

	public List<Pair<String,String>> getBoleanDocuments(String expressionToTest) {
        List<Pair<String, String>> response = new ArrayList<>();
        for (Map.Entry<String, Hashtable<Author, Document>> titleSet: mDocuments.entrySet()) {
            String title = titleSet.getKey();
            for (Map.Entry<Author, Document> authorSet : titleSet.getValue().entrySet()) {
                Vector<String> content = getDocumentContent_vector(authorSet.getKey(), title);
                for(int i = 0; i  < content.size(); i++){
                    if( VerifyExpression.verifyExpression(expressionToTest, content.elementAt(i).toString())){
                        response.add(new Pair(title, authorSet.getKey().getName().toString()));
                        i = content.size();
                    }
                }
            }
        }
        return response;
    }

    public SortedMap<String, Hashtable<Author, Document>> getDocumentsByPrefix(String titlePrefix){
        return mDocuments.subMap(titlePrefix, titlePrefix + Character.MAX_VALUE);
    }

}
