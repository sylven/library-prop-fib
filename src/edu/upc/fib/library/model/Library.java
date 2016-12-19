package edu.upc.fib.library.model;

import javafx.util.Pair;
import sun.reflect.generics.tree.Tree;

import javax.print.Doc;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Library implements Serializable {
    private Documents mDocuments;
    private Authors mAuthors;

    public Library() {
        mDocuments = new Documents();
        mAuthors = new Authors();
        loadStatus();

        /*String content;
        content = "Donald John Trump (Nueva York, 14 de junio de 1946) es un empresario, político, personalidad televisiva y escritor estadounidense.\n\nSiendo el presidente electo de los Estados Unidos de América; se convertirá en el 45° presidente de la Unión tras su toma de posesión, programada para el 20 de enero de 2017. Es presidente de la Trump Organization y fundador de la empresa de hotel y juegos de azar Trump Entertainment Resorts, que es ahora propiedad de Carl Icahn. Trump es una celebridad televisiva, y entre otras cosas fue el presentador del reality show The Apprentice, de la NBC, entre 2004 y 2015. Es hijo de un empresario inmobiliario de Nueva York,4 en cuya compañía, Elizabeth Trump & Son, trabajó mientras estudiaba en la Escuela de Negocios Wharton de la Universidad de Pensilvania. En 1968, se unió oficialmente a esa sociedad,5 que controla desde 1971, cuando la renombró Trump Organization. En los años 1990 la empresa entró en bancarrota comercial, pero en la década siguiente se recuperó, lo que le reportó una fortuna de varios miles de millones de dólares. Su campaña para obtener la candidatura republicana a la Casa Blanca para las elecciones de 2016 se vio caracterizada desde su inicio por una gran atención mediática a nivel nacional e internacional debido a la sucesión de declaraciones polémicas por parte de Trump. Sus propuestas más repetidas consisten en la construcción de un muro a lo largo de la frontera con México y una política dura contra la inmigración ilegal, además de una prohibición temporal de la entrada de musulmanes en los Estados Unidos. En lo económico, aboga por modificar la política comercial del país y fortalecer la producción nacional en detrimento de la deslocalización, en consonancia con posiciones proteccionistas.";
        addDocument("Wikipediaa", "Donald Trump", content);

        content = "El presidente de los Estados Unidos (en inglés, President of the United States; acrónimo: POTUS) es el jefe de Estado y de Gobierno de los Estados Unidos. Es el más alto cargo político del país por influencia y reconocimiento. El presidente lidera el poder ejecutivo del Gobierno federal. Entre otros poderes y responsabilidades, el Artículo II de la Constitución de los Estados Unidos encarga al presidente la «fiel ejecución» de la ley federal, hace del presidente el comandante en jefe de las Fuerzas Armadas, lo autoriza a nombrar oficiales ejecutivos y judiciales con el consejo y consentimiento del Senado, lo sitúa al frente de la política exterior de los Estados Unidos, y permite al presidente conceder indultos o moratorias. El presidente es elegido mediante sufragio indirecto por un colegio electoral (o por la Cámara de Representantes si el colegio electoral no concede la mayoría de votos a ningún candidato) para un mandato de cuatro años. Desde la ratificación de la Vigesimosegunda Enmienda en 1951, ninguna persona puede ser elegida para el cargo de presidente más de dos veces. En caso de muerte, destitución, dimisión o renuncia de un presidente, el vicepresidente asume la presidencia. Hubo cuarenta y tres personas que asumieron el cargo y cuarenta y cuatro presidencias. De las personas elegidas para el cargo, cuatro murieron durante su mandato por causas naturales, uno dimitió y cuatro fueron asesinados. El primer presidente fue George Washington, que fue investido en 1789 después de un voto unánime del colegio electoral. William Henry Harrison fue el que menos tiempo permaneció en el cargo, con tan solo 32 días, y Franklin D. Roosevelt, con sus 12 años en el puesto, fue el que permaneció por más tiempo y el único presidente que sirvió por más de dos mandatos (ganó cuatro veces las elecciones presidenciales). El actual presidente es el demócrata Barack Obama, que fue investido para el puesto el 20 de enero de 2009. El 9 de noviembre de 2016, el candidato por el Partido Republicano, Donald Trump, resultó electo en las elecciones presidenciales. Desde principios del siglo XX, el papel hegemónico de los Estados Unidos en el escenario político y económico internacional ha llevado al presidente de este país a ser una figura conocida a nivel global y, debido a la condición del país como única superpotencia, en 2009 la revista Forbes calificaba a su titular como «la persona más poderosa del mundo».";
        addDocument("Silviu", "Presidente de los Estados Unidos", content);

        content = "Android es un sistema operativo basado en el núcleo Linux. Fue diseñado principalmente para dispositivos móviles con pantalla táctil, como teléfonos inteligentes, tablets o tabléfonos; y también para relojes inteligentes, televisores y automóviles. Inicialmente fue desarrollado por Android Inc., empresa que Google respaldó económicamente y más tarde, en 2005, la compró. Android fue presentado en 2007 junto la fundación del Open Handset Alliance (un consorcio de compañías de hardware, software y telecomunicaciones) para avanzar en los estándares abiertos de los dispositivos móviles. El primer móvil con el sistema operativo Android fue el HTC Dream y se vendió en octubre de 2008. Los dispositivos de Android venden más que las ventas combinadas de Windows Phone e IOS. El éxito del sistema operativo se ha convertido en objeto de litigios sobre patentes en el marco de las llamadas «Guerras por patentes de teléfonos inteligentes» (en inglés, Smartphone patent wars) entre las empresas de tecnología. Según documentos secretos filtrados en 2013 y 2014, el sistema operativo es uno de los objetivos de las agencias de inteligencia internacionales. La versión básica de Android es conocida como Android Open Source Project (AOSP). El 25 de junio de 2014 en la Conferencia de Desarrolladores Google I/O, Google mostró una evolución de la marca Android, con el fin de unificar tanto el hardware como el software y ampliar mercados.";
        addDocument("Silviu", "Android", content);

        content = "iOS es un sistema operativo móvil de la multinacional Apple Inc. Originalmente desarrollado para el iPhone (iPhone OS), después se ha usado en dispositivos como el iPod touch y el iPad. No permite la instalación de iOS en hardware de terceros. Tenía el 26 % de cuota de mercado de sistemas operativos móviles vendidos en el último cuatrimestre de 2010, detrás de Android y Windows Phone. Actualmente su sistema operativo se encuentra en la décima versión, mejor conocida como iOS 10. Apple anunció el lunes 21 de septiembre de 2015 que su nuevo sistema operativo iOS 9 ya ha superado el 70 % de adopción dentro de sus dispositivos compatibles. Según la marca de Cupertino, 2 de cada 3 dispositivos tienen iOS 9 instalado. El 23 de septiembre de 2015 (tan solo una semana después de la salida de iOS 9), Apple saca iOS 9.0.1 para solucionar problemas relacionados con las alarmas y temporizadores. Los elementos de control consisten de deslizadores, interruptores y botones. La respuesta a las órdenes del usuario es inmediata y provee una interfaz fluida. La interacción con el sistema operativo incluye gestos como deslices, toques, pellizcos, los cuales tienen definiciones diferentes dependiendo del contexto de la interfaz. Se utilizan acelerómetros internos para hacer que algunas aplicaciones respondan a sacudir el dispositivo (por ejemplo, para el comando deshacer) o rotarlo en tres dimensiones (un resultado común es cambiar de modo vertical al apaisado u horizontal). En el marco de las filtraciones acerca de los programas de vigilancia mundial de 2013-2014 de Edward Snowden, Der Spiegel publicó que la NSA estadounidense tiene grupos de trabajo dedicados a descifrar los sistemas de seguridad de iOS; además tiene pequeños programas conocidos como scripts que permiten a la agencia vigilar a los usuarios de las distintas versiones del sistema iOS su geolocalización, notas de voz, fotos y otras aplicaciones como Google Earth, Facebook o Yahoo! Messenger. iOS se deriva de macOS, que a su vez está basado en Darwin BSD, y por lo tanto es un sistema operativo Tipo Unix. iOS cuenta con cuatro capas de abstracción: la capa del núcleo del sistema operativo, la capa de \"Servicios Principales\", la capa de \"Medios\" y la capa de \"Cocoa Touch\".";
        addDocument("Wikipediaa", "iOs", content);

        content = "Un automóvil de turismo, también conocido simplemente como turismo o automóvil o automotor, es el tipo de automóvil destinado al transporte de personas, con al menos cuatro ruedas y un máximo de nueve plazas incluido el conductor.";
        addDocument("Wikipedia", "Automóvil de turismo", content);*/

        //List<Pair<String, String>> letssee = getBooleanDocuments("automóvil & !Android & {transporte personas ruedas} & (\"nueve plazas\" | \"cinco plazas\")");

    }
     
	public void restartStatus(){
        mDocuments = new Documents();
        mAuthors = new Authors();
	}

	// Gestión de autores

    public void saveStatus() {
        try {
            FileOutputStream fout = new FileOutputStream("library-persistence.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(mDocuments);
            oos.writeObject(mAuthors);
            oos.close();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    private void loadStatus() {
        try {
            File file = new File("library-persistence.dat");
            if (file.exists()) {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fin);
                mDocuments = (Documents) ois.readObject();
                mAuthors = (Authors) ois.readObject();
                ois.close();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public boolean addAuthor(String authorName) {
        return mAuthors.addAuthor(authorName);
    }

    public boolean modifyAuthor(String authorName, String newAuthorName) {
        return mAuthors.modifyAuthor(authorName, newAuthorName);
    }

    public boolean removeAuthor(String authorName) {
        return mAuthors.removeAuthor(authorName, mDocuments);
    }

    public Set<String> getAuthorNames() {
        return mAuthors.getAuthorNames();
    }

    public boolean addDocument(String authorName, String title, String content) {
        addAuthor(authorName);
        return mDocuments.addDocument(mAuthors.getAuthor(authorName), title, content);
    }

    public boolean removeDocument(String authorName, String title) {
        return mDocuments.removeDocument(mAuthors.getAuthor(authorName), title);
    }

    public Set<String> getDocumentTitles() {
        return mDocuments.getDocumentTitles();
    }

    public List<Pair<String, String>> getAllDocuments() {
        return mDocuments.getDocumentInfos();
    }

    //public Vector<String> getDocumentContent(String authorName, String title) {
    public String getDocumentContent(String authorName, String title) {
        if (existsDocument(authorName, title)) {
            return mDocuments.getDocumentContent(mAuthors.getAuthor(authorName), title);
        }
        //return new Vector<>();
        return "";
    }

    public List<Pair<String, Pair<String, Double>>> getSimilarDocuments(String authorName, String title, int nDocuments) {
        if (existsDocument(authorName, title)) {
            List<Map.Entry<Document, Double>> similarDocuments = mDocuments.getSimilarDocuments(mAuthors.getAuthor(authorName), title);
            List<Pair<String, Pair<String, Double>>> resultsList = new ArrayList<>();
            int i = 0;
            for (Map.Entry<Document, Double> result : similarDocuments) {
                if (i >= nDocuments) {
                    break;
                }
                resultsList.add(new Pair(result.getKey().getTitle().toString(), new Pair<>(result.getKey().getAuthor().getName().toString(), result.getValue())));
                i++;
            }
             return resultsList;
        }
        return new ArrayList<>();
    }

    // Mejorar para incluir a los autores?
    public Set<String> getAuthorDocumentTitles(String authorName) {
        if (existsAuthor(authorName)) return mAuthors.getAuthorDocumentTitles(authorName);
        else {
            Set<String> s = Collections.emptySet();
            return s;
        }
    }

    public boolean existsAuthor(String authorName) {
        return mAuthors.existsAuthor(authorName);
    }

    public Set<String> getAuthorsByPrefix(String prefix) {
        return mAuthors.getAuthorsByPrefix(prefix).keySet();
    }

    /*public List<Pair<String, String>> getDocumentsByPrefix(String prefix) {
        List<Pair<String, String>> response = new ArrayList<>();
        for (Map.Entry<String, Hashtable<Author, Document>> titleSet : mDocuments.getDocumentsByPrefix(prefix).entrySet()) {
            for (Map.Entry<Author, Document> doc : titleSet.getValue().entrySet()) {
                response.add(new Pair<>(titleSet.getKey(), doc.getKey().getName().toString()));
            }
        }
        return response;
    }*/

    /*public List<Pair<String, String>> getDocumentsByTitle(String title) {
        List<Pair<String, String>> response = new ArrayList<>();
        for (Map.Entry<String, Hashtable<Author, Document>> titleSet : mDocuments.getDocumentsByTitle(title).entrySet()) {
            for (Map.Entry<Author, Document> doc : titleSet.getValue().entrySet()) {
                response.add(new Pair<>(titleSet.getKey(), doc.getKey().getName().toString()));
            }
        }
        return response;
    }*/

    public boolean existsDocument(String authorName, String title) {
        return mDocuments.existsDocument(mAuthors.getAuthor(authorName), title);
    }

    public boolean modifyDocumentAuthor(String authorName, String title, String newAuthorName) {
        addAuthor(newAuthorName);
        if (mAuthors.existsAuthor(authorName)
                && mAuthors.existsAuthor(newAuthorName)
                && !existsDocument(newAuthorName, title)) {
            mDocuments.modifyDocumentAuthor(mAuthors.getAuthor(authorName), title, mAuthors.getAuthor(newAuthorName));
            return true;
        }
        return false;
    }

    public boolean modifyDocumentTitle(String authorName, String title, String newTitle) {
        if (mAuthors.existsAuthor(authorName)
                && !existsDocument(authorName, newTitle)) {
            mDocuments.modifyDocumentTitle(mAuthors.getAuthor(authorName), title, newTitle);
            return true;
        }
        return false;
    }

    public boolean modifyDocumentContent(String authorName, String title, String newContent) {
        if (existsDocument(authorName, title)) {
            mDocuments.modifyDocumentContent(mAuthors.getAuthor(authorName), title, newContent);
            return true;
        }
        return false;
    }

    //
    // GETTERS
    //
    /*public List<Pair<String, String>> getDocumentsOfAuthorsWithPrefix(String authorPrefix) {
        List<Pair<String, String>> response = new ArrayList<>();
        Set<String> authors = getAuthorsByPrefix(authorPrefix);
        for (String author : authors) {
            Set<String> authorDocs = getAuthorDocumentTitles(author);
            for (String docTitle : authorDocs) {
                response.add(new Pair<>(docTitle, author));
            }
        }
        return response;
    }*/

    /*public List<Pair<String, String>> getDocumentsByPrefixes(String authorPrefix, String titlePrefix) {
        List<Pair<String, String>> response = new ArrayList<>();
        SortedMap<String, Author> authors = mAuthors.getAuthorsByPrefix(authorPrefix);
        for (Map.Entry<String, Author> author : authors.entrySet()) {
            //for (String docTitle : author.getValue().getDocumentTitlesByPrefix(titlePrefix)) {
            //    response.add(new Pair<>(docTitle, author.getKey()));
            //}
            // De momento no funciona como deberia
            for (String docTitle : author.getValue().getDocumentTitles()) {
                response.add(new Pair<>(docTitle, author.getKey()));
            }


            //Set<String> authorDocs = getAuthorDocumentTitles(author);
            //authorDocs.subMap(prefix, prefix + Character.MAX_VALUE);
            //for (String docTitle : authorDocs) {
            //    response.add(new Pair<>(docTitle, author));
            //}
        }


        //List<Pair<String, String>> response = new ArrayList<>();
        //Set<String> authors = getAuthorsByPrefix(authorPrefix);
        //for (String author : authors) {
        //    Set<String> authorDocs = getAuthorDocumentTitles(author);
        //    for (String docTitle : authorDocs) {
        //        response.add(new Pair<>(docTitle, author));
        //   }
        }
        //return response;
    }*/


    //((a|b)|(c|d)|e|f)

    public static Vector<Document> getDocumentExpression_rec(Vector<String> expression_cut){

        int a = 0;
        int z = expression_cut.size()-1;
        int numparentesis = 0;
        for(int i = 0; i < expression_cut.size(); i++){
            if (expression_cut.get(i).equals("(")) {numparentesis++;}
        }

        int cont=0;
        int separador=0;
        for(int i=a; i<z;++i){
            if (expression_cut.get(i).equals("(")) ++cont;
            else if (expression_cut.get(i).equals(")")) --cont;
            else if((expression_cut.get(i).equals("|") | expression_cut.get(i).equals("&")) & cont==0) separador=i;
        }

        if(separador==0 & expression_cut.get(a).equals("(") & expression_cut.get(z).equals(")")){
            expression_cut.remove(a);
            expression_cut.remove(z);
            getDocumentExpression_rec(expression_cut);
        }

        if(separador!=0) {
            Vector<String> s1 = new Vector<>();
            for (int i = a; i < separador; ++i) {
                s1.add(expression_cut.get(i));
            }

            Vector<String> s2 = new Vector<>();
            for (int i = separador + 1; i <= z; ++i) {
                s2.add(expression_cut.get(i));
            }

            Vector<Document> d1 = getDocumentExpression_rec(s1);
            Vector<Document> d2 = getDocumentExpression_rec(s2);

            if (expression_cut.get(separador).equals("|")) {

            }

            if (expression_cut.get(separador).equals("&")) {

            }
        }

        else if(separador==0){
            if(expression_cut.get(0).equals("{")){

            }
            else if(expression_cut.equals("!")){

            }
            else {

            }
        }

        return null;
    }

    public static HashMap<String, Vector<String>> getDocumentExpression(String expression){
        HashMap<String, Vector<String>> results = new HashMap<>();
        //-----------------------------------------------------------------------------------------
        Vector<String> expression_cut = new Sentence(expression).getVector();

        // Expresión regular para partir la frase en palabras, signos y espacios.
        Pattern pattern = Pattern.compile("([A-Za-z0-9'ÁáÄäÀàÉéËëÈèÍíÏïÌìÓóÖöÒòÚúÜüÙùÑñÇç-])+|[^ ]");
        Matcher matcher = pattern.matcher(expression);
        expression_cut = new Vector<>();
        while (matcher.find()) {
            if (!matcher.group().equals(" ")) {
                expression_cut.add(matcher.group());
            }
        }

        Vector<Document> result= getDocumentExpression_rec(expression_cut);

        //-----------------------------------------------------------------------------------------
        return results;
    }

	public boolean verifyExpression(String expression1, String expression2){
        return VerifyExpression.verifyExpression(expression1, expression2);
    }

    public List<Pair<String,String>> getBooleanDocuments(String expressionToTest) {
            return mDocuments.getBoleanDocuments(expressionToTest);
    }

    public List<Pair<String,String>> getDocumentsByPrefix(String titlePrefix){
        List<Pair<String, String>> response = new ArrayList<>();
        SortedMap<String, Hashtable<Author, Document>> documentList = mDocuments.getDocumentsByPrefix(titlePrefix);
        for (Map.Entry<String, Hashtable<Author, Document>> docEntry : documentList.entrySet()) {
            for (Author author : docEntry.getValue().keySet())
                response.add(new Pair<>(docEntry.getKey(), author.getName().toString()));
        }
        return response;
    }

    public List<Pair<String,String>> getDocumentsByPrefixes(String authorPrefix, String titlePrefix){
        if ((authorPrefix == null || authorPrefix.equals("")) && (titlePrefix == null || titlePrefix.equals(""))) {
            return getAllDocuments();
        } else if (authorPrefix == null || authorPrefix.equals("")) {
            return getDocumentsByPrefix(titlePrefix);
        } else {
            List<Pair<String, String>> response = new ArrayList<>();
            SortedMap<String, Author> authors = mAuthors.getAuthorsByPrefix(authorPrefix);
            for (Map.Entry<String, Author> author : authors.entrySet()) {
                for (String docTitle : author.getValue().getAuthorDocumentsByPrefix(titlePrefix).keySet()) {
                    response.add(new Pair<>(docTitle, author.getKey()));
                }

                /*Set<String> authorDocs = getAuthorDocumentTitles(author);
                authorDocs.subMap(prefix, prefix + Character.MAX_VALUE);
                for (String docTitle : authorDocs) {
                    response.add(new Pair<>(docTitle, author));
                }*/
            }
            return response;
        }
    }

}


