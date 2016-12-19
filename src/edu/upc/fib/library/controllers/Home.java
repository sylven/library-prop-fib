package edu.upc.fib.library.controllers;


import edu.upc.fib.library.model.Library;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Home implements Initializable {
    private Library mLibrary;

    @FXML private TabPane documentsTab;

    @FXML private ComboBox dT1Author;
    @FXML private ComboBox dT1Title;
    @FXML private TableView<Integer> dT1TableView;
    @FXML private TableColumn<Integer, String> dT1TableViewTitle;
    @FXML private TableColumn<Integer, String> dT1TableViewAuthor;
    private List<Pair<String, String>> dT1ResultDocuments;
    @FXML private TextField dT1DocAuthor;
    @FXML private TextField dT1DocTitle;
    @FXML private TextArea dT1DocContent;
    private String dT1CurrentAuthor, dT1CurrentTitle, dT1CurrentContent;

    @FXML private TextField dT2Expression;
    @FXML private TableView<Integer> dT2TableView;
    @FXML private TableColumn<Integer, String> dT2TableViewTitle;
    @FXML private TableColumn<Integer, String> dT2TableViewAuthor;
    private List<Pair<String, String>> dT2ResultDocuments;
    @FXML private TextField dT2DocAuthor;
    @FXML private TextField dT2DocTitle;
    @FXML private TextArea dT2DocContent;
    private String dT2CurrentAuthor, dT2CurrentTitle, dT2CurrentContent;

    @FXML private TextField dT3DocAuthor;
    @FXML private TextField dT3DocTitle;
    @FXML private TextArea dT3DocContent;
    @FXML private Pane dT3Drag;

    @FXML private ComboBox dT4Author;
    @FXML private ComboBox dT4Title;
    @FXML private ComboBox dT4Algorithm;
    @FXML private TextField dT4NResults;
    @FXML private TableView<Integer> dT4TableView;
    @FXML private TableColumn<Integer, String> dT4TableViewTitle;
    @FXML private TableColumn<Integer, String> dT4TableViewAuthor;
    @FXML private TableColumn<Integer, String> dT4TableViewSimilarity;
    private List<Pair<String, Pair<String, Double>>> dT4ResultDocuments;
    @FXML private TextField dT4DocAuthor;
    @FXML private TextField dT4DocTitle;
    @FXML private TextArea dT4DocContent;
    private String dT4CurrentAuthor, dT4CurrentTitle, dT4CurrentContent;

    @FXML private TabPane authorsTab;

    @FXML private ComboBox aT1Author;
    @FXML private TableView<Integer> aT1TableView;
    @FXML private TableColumn<Integer, String> aT1TableViewAuthor;
    private List<String> aT1ResultAuthors;
    @FXML private TextField aT1AuthName;
    private String aT1CurrentAuthor;

    @FXML private TextField aT2AuthName;

    @FXML private Label status;
    @FXML private ProgressBar progressBar;

    public Home() {
        mLibrary = new Library();
        mLibrary.addDocument("a", "a", "aaa coche");
        mLibrary.addDocument("b", "b", "aaa cosa");
        mLibrary.addDocument("c", "c", "cosa coche");

//        String content;
//        content = "Donald John Trump (Nueva York, 14 de junio de 1946) es un empresario, político, personalidad televisiva y escritor estadounidense.\n\nSiendo el presidente electo de los Estados Unidos de América; se convertirá en el 45° presidente de la Unión tras su toma de posesión, programada para el 20 de enero de 2017. Es presidente de la Trump Organization y fundador de la empresa de hotel y juegos de azar Trump Entertainment Resorts, que es ahora propiedad de Carl Icahn. Trump es una celebridad televisiva, y entre otras cosas fue el presentador del reality show The Apprentice, de la NBC, entre 2004 y 2015. Es hijo de un empresario inmobiliario de Nueva York,4 en cuya compañía, Elizabeth Trump & Son, trabajó mientras estudiaba en la Escuela de Negocios Wharton de la Universidad de Pensilvania. En 1968, se unió oficialmente a esa sociedad,5 que controla desde 1971, cuando la renombró Trump Organization. En los años 1990 la empresa entró en bancarrota comercial, pero en la década siguiente se recuperó, lo que le reportó una fortuna de varios miles de millones de dólares. Su campaña para obtener la candidatura republicana a la Casa Blanca para las elecciones de 2016 se vio caracterizada desde su inicio por una gran atención mediática a nivel nacional e internacional debido a la sucesión de declaraciones polémicas por parte de Trump. Sus propuestas más repetidas consisten en la construcción de un muro a lo largo de la frontera con México y una política dura contra la inmigración ilegal, además de una prohibición temporal de la entrada de musulmanes en los Estados Unidos. En lo económico, aboga por modificar la política comercial del país y fortalecer la producción nacional en detrimento de la deslocalización, en consonancia con posiciones proteccionistas.";
//        mLibrary.addDocument("Wikipediaa", "Donald Trump", content);
//
//        content = "El presidente de los Estados Unidos (en inglés, President of the United States; acrónimo: POTUS) es el jefe de Estado y de Gobierno de los Estados Unidos. Es el más alto cargo político del país por influencia y reconocimiento. El presidente lidera el poder ejecutivo del Gobierno federal. Entre otros poderes y responsabilidades, el Artículo II de la Constitución de los Estados Unidos encarga al presidente la «fiel ejecución» de la ley federal, hace del presidente el comandante en jefe de las Fuerzas Armadas, lo autoriza a nombrar oficiales ejecutivos y judiciales con el consejo y consentimiento del Senado, lo sitúa al frente de la política exterior de los Estados Unidos, y permite al presidente conceder indultos o moratorias. El presidente es elegido mediante sufragio indirecto por un colegio electoral (o por la Cámara de Representantes si el colegio electoral no concede la mayoría de votos a ningún candidato) para un mandato de cuatro años. Desde la ratificación de la Vigesimosegunda Enmienda en 1951, ninguna persona puede ser elegida para el cargo de presidente más de dos veces. En caso de muerte, destitución, dimisión o renuncia de un presidente, el vicepresidente asume la presidencia. Hubo cuarenta y tres personas que asumieron el cargo y cuarenta y cuatro presidencias. De las personas elegidas para el cargo, cuatro murieron durante su mandato por causas naturales, uno dimitió y cuatro fueron asesinados. El primer presidente fue George Washington, que fue investido en 1789 después de un voto unánime del colegio electoral. William Henry Harrison fue el que menos tiempo permaneció en el cargo, con tan solo 32 días, y Franklin D. Roosevelt, con sus 12 años en el puesto, fue el que permaneció por más tiempo y el único presidente que sirvió por más de dos mandatos (ganó cuatro veces las elecciones presidenciales). El actual presidente es el demócrata Barack Obama, que fue investido para el puesto el 20 de enero de 2009. El 9 de noviembre de 2016, el candidato por el Partido Republicano, Donald Trump, resultó electo en las elecciones presidenciales. Desde principios del siglo XX, el papel hegemónico de los Estados Unidos en el escenario político y económico internacional ha llevado al presidente de este país a ser una figura conocida a nivel global y, debido a la condición del país como única superpotencia, en 2009 la revista Forbes calificaba a su titular como «la persona más poderosa del mundo».";
//        mLibrary.addDocument("Silviu", "Presidente de los Estados Unidos", content);
//
//        content = "Android es un sistema operativo basado en el núcleo Linux. Fue diseñado principalmente para dispositivos móviles con pantalla táctil, como teléfonos inteligentes, tablets o tabléfonos; y también para relojes inteligentes, televisores y automóviles. Inicialmente fue desarrollado por Android Inc., empresa que Google respaldó económicamente y más tarde, en 2005, la compró. Android fue presentado en 2007 junto la fundación del Open Handset Alliance (un consorcio de compañías de hardware, software y telecomunicaciones) para avanzar en los estándares abiertos de los dispositivos móviles. El primer móvil con el sistema operativo Android fue el HTC Dream y se vendió en octubre de 2008. Los dispositivos de Android venden más que las ventas combinadas de Windows Phone e IOS. El éxito del sistema operativo se ha convertido en objeto de litigios sobre patentes en el marco de las llamadas «Guerras por patentes de teléfonos inteligentes» (en inglés, Smartphone patent wars) entre las empresas de tecnología. Según documentos secretos filtrados en 2013 y 2014, el sistema operativo es uno de los objetivos de las agencias de inteligencia internacionales. La versión básica de Android es conocida como Android Open Source Project (AOSP). El 25 de junio de 2014 en la Conferencia de Desarrolladores Google I/O, Google mostró una evolución de la marca Android, con el fin de unificar tanto el hardware como el software y ampliar mercados.";
//        mLibrary.addDocument("Silviu", "Android", content);
//
//        content = "iOS es un sistema operativo móvil de la multinacional Apple Inc. Originalmente desarrollado para el iPhone (iPhone OS), después se ha usado en dispositivos como el iPod touch y el iPad. No permite la instalación de iOS en hardware de terceros. Tenía el 26 % de cuota de mercado de sistemas operativos móviles vendidos en el último cuatrimestre de 2010, detrás de Android y Windows Phone. Actualmente su sistema operativo se encuentra en la décima versión, mejor conocida como iOS 10. Apple anunció el lunes 21 de septiembre de 2015 que su nuevo sistema operativo iOS 9 ya ha superado el 70 % de adopción dentro de sus dispositivos compatibles. Según la marca de Cupertino, 2 de cada 3 dispositivos tienen iOS 9 instalado. El 23 de septiembre de 2015 (tan solo una semana después de la salida de iOS 9), Apple saca iOS 9.0.1 para solucionar problemas relacionados con las alarmas y temporizadores. Los elementos de control consisten de deslizadores, interruptores y botones. La respuesta a las órdenes del usuario es inmediata y provee una interfaz fluida. La interacción con el sistema operativo incluye gestos como deslices, toques, pellizcos, los cuales tienen definiciones diferentes dependiendo del contexto de la interfaz. Se utilizan acelerómetros internos para hacer que algunas aplicaciones respondan a sacudir el dispositivo (por ejemplo, para el comando deshacer) o rotarlo en tres dimensiones (un resultado común es cambiar de modo vertical al apaisado u horizontal). En el marco de las filtraciones acerca de los programas de vigilancia mundial de 2013-2014 de Edward Snowden, Der Spiegel publicó que la NSA estadounidense tiene grupos de trabajo dedicados a descifrar los sistemas de seguridad de iOS; además tiene pequeños programas conocidos como scripts que permiten a la agencia vigilar a los usuarios de las distintas versiones del sistema iOS su geolocalización, notas de voz, fotos y otras aplicaciones como Google Earth, Facebook o Yahoo! Messenger. iOS se deriva de macOS, que a su vez está basado en Darwin BSD, y por lo tanto es un sistema operativo Tipo Unix. iOS cuenta con cuatro capas de abstracción: la capa del núcleo del sistema operativo, la capa de \"Servicios Principales\", la capa de \"Medios\" y la capa de \"Cocoa Touch\".";
//        mLibrary.addDocument("Wikipediaa", "iOs", content);
//
//        content = "Un automóvil de turismo, también conocido simplemente como turismo o automóvil o automotor, es el tipo de automóvil destinado al transporte de personas, con al menos cuatro ruedas y un máximo de nueve plazas incluido el conductor.";
//        mLibrary.addDocument("Wikipedia", "Automóvil de turismo", content);

        //mLibrary.removeAuthor("Wikipediaa");
    }

    public void saveStatus() {
        mLibrary.saveStatus();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Llenar los comboboxes
        ObservableList<String> dT1TitleComboItems = FXCollections.observableArrayList();
        dT1Title.setItems(dT1TitleComboItems);
        dT1TitleComboItems.addAll(mLibrary.getDocumentTitles());
        ObservableList<String> dT1AuthorComboItems = FXCollections.observableArrayList();
        dT1Author.setItems(dT1AuthorComboItems);
        dT1AuthorComboItems.addAll(mLibrary.getAuthorNames());

        dT4UpdateAuthorCombo();
        dT4UpdateTitleCombo();
        ObservableList<String> dT4AlgorithmComboItems = FXCollections.observableArrayList();
        dT4Algorithm.setItems(dT4AlgorithmComboItems);
        dT4AlgorithmComboItems.add("A");
        dT4AlgorithmComboItems.add("B");
        dT4Algorithm.setValue("A");

        ObservableList<String> aT1AuthorComboItems = FXCollections.observableArrayList();
        aT1Author.setItems(aT1AuthorComboItems);
        aT1AuthorComboItems.addAll(mLibrary.getAuthorNames());

        // Llenar las listas de resultados
        dT1LoadDocumentResults(false);
        aT1LoadAuthorResults(true);
    }

    public void updateCombos() {
        dT1UpdateAuthorCombo();
        dT1UpdateTitleCombo();
        dT4UpdateAuthorCombo();
        dT4UpdateTitleCombo();
        aT1UpdateAuthorCombo();
    }


    // dT1

    // DONE ############
    public void dT1AuthorClear(ActionEvent actionEvent) {
        dT1Author.setValue("");
    }

    // DONE ############
    public void dT1UpdateAuthorCombo() {
        String current = (String) dT1Author.getValue();
        ObservableList<String> authorComboItems = FXCollections.observableArrayList();
        dT1Author.setItems(authorComboItems);
        authorComboItems.addAll(mLibrary.getAuthorNames());
        dT1Author.setValue(current);
    }

    // DONE ############
    @FXML
    public void dT1TitleClear(ActionEvent actionEvent) {
        dT1Title.setValue("");
    }

    // DONE ############
    @FXML
    private void dT1UpdateTitleCombo() {
        String current = (String) dT1Title.getValue();
        ObservableList<String> titleComboItems = FXCollections.observableArrayList();
        dT1Title.setItems(titleComboItems);
        titleComboItems.addAll(mLibrary.getDocumentTitles());
        dT1Title.setValue(current);
    }

    // DONE ############
    public void dT1Search(ActionEvent actionEvent) {
        dT1LoadDocumentResults(false);
    }

    // DONE ############
    public void dT1LoadDocumentResults(boolean isReload) {
        if (!isReload) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);
        }

        Task<Void> task = new Task<Void>() {
            @Override public Void call() throws InterruptedException {
                if (!isReload) updateMessage("Realizando búsqueda de documentos...");
                //updateProgress(1, 2);

                dT1TableView.getItems().clear();

                //status.setText("Realizando búsqueda de documentos...");

                // Si solo esta puesto el autor => Buscar todos los autores con ese prefijo => Mostrar los documentos de todos ellos
                if (dT1Title.getValue() == null && dT1Author.getValue() != null) {
                    String author = dT1Author.getValue().toString();
                    dT1ResultDocuments = mLibrary.getDocumentsByPrefixes(author, null);
                    for(int i = 0; i < dT1ResultDocuments.size(); i++){
                        dT1TableView.getItems().add(i);
                    }
                    dT1TableViewTitle.setCellValueFactory(cellData -> {
                        Integer titleIndex = cellData.getValue();
                        return new ReadOnlyStringWrapper(dT1ResultDocuments.get(titleIndex).getKey());
                    });
                    dT1TableViewAuthor.setCellValueFactory(cellData -> {
                        Integer titleIndex = cellData.getValue();
                        return new ReadOnlyStringWrapper(dT1ResultDocuments.get(titleIndex).getValue());
                    });
                    //status.setText("Búsqueda finalizada.");
                    if (!isReload) updateMessage("Búsqueda finalizada.");
                }
                // Si solo esta puesto el titulo => Mostrar todos los documentos con ese prefijo
                else if (dT1Title.getValue() != null && dT1Author.getValue() == null) {
                    String title = dT1Title.getValue().toString();
                    dT1ResultDocuments = mLibrary.getDocumentsByPrefixes(null, title);
                    for(int i = 0; i < dT1ResultDocuments.size(); i++){
                        dT1TableView.getItems().add(i);
                    }
                    dT1TableViewTitle.setCellValueFactory(cellData -> {
                        Integer titleIndex = cellData.getValue();
                        return new ReadOnlyStringWrapper(dT1ResultDocuments.get(titleIndex).getKey());
                    });
                    dT1TableViewAuthor.setCellValueFactory(cellData -> {
                        Integer titleIndex = cellData.getValue();
                        return new ReadOnlyStringWrapper(dT1ResultDocuments.get(titleIndex).getValue());
                    });
                    //status.setText("Búsqueda finalizada.");
                    if (!isReload) updateMessage("Búsqueda finalizada.");
                }
                // Si esta puesto el titulo y el autor => Buscar todos los autores con ese prefijo y mostrar su documentos con ese prefijo
                else if (dT1Title.getValue() != null && dT1Author.getValue() != null) {
                    String author = dT1Author.getValue().toString();
                    String title = dT1Title.getValue().toString();
                    dT1ResultDocuments = mLibrary.getDocumentsByPrefixes(author, title);
                    for(int i = 0; i < dT1ResultDocuments.size(); i++){
                        dT1TableView.getItems().add(i);
                    }
                    dT1TableViewTitle.setCellValueFactory(cellData -> {
                        Integer titleIndex = cellData.getValue();
                        return new ReadOnlyStringWrapper(dT1ResultDocuments.get(titleIndex).getKey());
                    });
                    dT1TableViewAuthor.setCellValueFactory(cellData -> {
                        Integer titleIndex = cellData.getValue();
                        return new ReadOnlyStringWrapper(dT1ResultDocuments.get(titleIndex).getValue());
                    });
                    //status.setText("Búsqueda finalizada.");
                    if (!isReload) updateMessage("Búsqueda finalizada.");
                }
                // Si no esta puesto ninguno => Mostrar todos los documentos
                else if (dT1Title.getValue() == null && dT1Author.getValue() == null) {
                    dT1ResultDocuments = mLibrary.getDocumentsByPrefixes(null, null);
                    for(int i = 0; i < dT1ResultDocuments.size(); i++){
                        dT1TableView.getItems().add(i);
                    }
                    //TableColumn<Integer, String> dT1TableViewTitle = new TableColumn<>("Título");
                    //dT1TableViewTitle.setPrefWidth(394);
                    dT1TableViewTitle.setCellValueFactory(cellData -> {
                        Integer titleIndex = cellData.getValue();
                        return new ReadOnlyStringWrapper(dT1ResultDocuments.get(titleIndex).getKey());
                    });
                    //TableColumn<Integer, String> dT1TableViewAuthor = new TableColumn<>("Autor");
                    //dT1TableViewTitle.setPrefWidth(176);
                    dT1TableViewAuthor.setCellValueFactory(cellData -> {
                        Integer titleIndex = cellData.getValue();
                        return new ReadOnlyStringWrapper(dT1ResultDocuments.get(titleIndex).getValue());
                    });
                    //status.setText("Carga de lista completa de documentos finalizada (campos de búsqueda vacíos).");
                    if (!isReload) updateMessage("Se muestra la lista completa de documentos.");
                    //dT1TableView.getColumns().addAll(dT1TableViewTitle, dT1TableViewAuthor);
                }

                //updateMessage("Datos del programa guardados satisfactoriamente.");
                //updateProgress(2, 2);

                return null;
            }
        };

        task.setOnSucceeded(event -> {
            if (!isReload) {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();
            }
            //status.textProperty().unbind();
            //status.setText("Documento añadido satisfactoriamente.");
        });

        if (!isReload) {
            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());
        }

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    // DONE ############
    public void dT1Remove(ActionEvent actionEvent) {
        if (dT1TableView.getSelectionModel().getSelectedIndex() != -1) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override public Boolean call() throws InterruptedException {
                    updateMessage("Eliminando documento...");

                    int itemIndex = dT1TableView.getSelectionModel().getSelectedIndex();
                    String documentTitle = dT1ResultDocuments.get(itemIndex).getKey();
                    String authorName = dT1ResultDocuments.get(itemIndex).getValue();

                    Boolean success = mLibrary.removeDocument(authorName, documentTitle);
                    // Si se estaba visualizando ese documento => vaciar la vista de documento
                    if (authorName.equals(dT1DocAuthor.getText()) && documentTitle.equals(dT1DocTitle.getText())) {
                        dT1DocAuthor.clear();
                        dT1DocTitle.clear();
                        dT1DocContent.clear();
                    }
                    return success;
                }
            };

            task.setOnSucceeded(event -> {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();

                if (task.getValue()) status.setText("Documento eliminado satisfactoriamente.");
                else status.setText("El documento no existe.");

                // Actualizar lista de resultados
                updateCombos();
                dT1LoadDocumentResults(true);
            });

            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            status.setText("Por favor, selecciona un documento para poder eliminarlo.");
        }
    }

    // DONE ############
    public void dT1Show(ActionEvent actionEvent) {
        if (dT1TableView.getSelectionModel().getSelectedIndex() != -1) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                public Boolean call() throws InterruptedException {
                    updateMessage("Abriendo documento...");

                    int itemIndex = dT1TableView.getSelectionModel().getSelectedIndex();
                    dT1CurrentTitle = dT1ResultDocuments.get(itemIndex).getKey();
                    dT1CurrentAuthor = dT1ResultDocuments.get(itemIndex).getValue();

                    Boolean success = false;
                    String content = mLibrary.getDocumentContent(dT1CurrentAuthor, dT1CurrentTitle);
                    if (content != null) {
                        success = true;
                        dT1CurrentContent = content;
                        dT1DocTitle.setText(dT1CurrentTitle);
                        dT1DocAuthor.setText(dT1CurrentAuthor);
                        dT1DocContent.setText(dT1CurrentContent);
                    }
                    return success;
                }
            };

            task.setOnSucceeded(event -> {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();

                if (task.getValue()) status.setText("Documento abierto satisfactoriamente.");
                else status.setText("El documento no existe.");
            });

            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            status.setText("Por favor, selecciona un documento para poder verlo.");
        }
    }

    // DONE  ############
    public void dT1DocClear(ActionEvent actionEvent) {
        dT1DocAuthor.clear();
        dT1DocTitle.clear();
        dT1DocContent.clear();
    }

    // DONE  ############
    public void dT1Save(ActionEvent actionEvent) {
        if (!dT1DocAuthor.getText().isEmpty() && !dT1DocTitle.getText().isEmpty() && !dT1DocContent.getText().isEmpty()) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                public Boolean call() throws InterruptedException {
                    updateMessage("Guardando documento...");

                    if (!dT1DocAuthor.getText().equals(dT1CurrentAuthor)) {
                        mLibrary.modifyDocumentAuthor(dT1CurrentAuthor, dT1CurrentTitle, dT1DocAuthor.getText());
                        dT1CurrentAuthor = dT1DocAuthor.getText();
                    }
                    if (!dT1DocTitle.getText().equals(dT1CurrentTitle)) {
                        mLibrary.modifyDocumentTitle(dT1CurrentAuthor, dT1CurrentTitle, dT1DocTitle.getText());
                        dT1CurrentTitle = dT1DocTitle.getText();
                    }
                    if (!dT1DocContent.getText().equals(dT1CurrentContent)) {
                        mLibrary.modifyDocumentContent(dT1CurrentAuthor, dT1CurrentTitle, dT1DocContent.getText());
                        dT1CurrentContent = dT1DocContent.getText();
                    }

                    return true;
                }
            };

            task.setOnSucceeded(event -> {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();

                if (task.getValue()) status.setText("Documento guardado satisfactoriamente.");
                else status.setText("El documento no se ha podido guardar.");

                // Actualizar lista de resultados
                updateCombos();
                dT1LoadDocumentResults(true);
            });

            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            status.setText("Por favor, edita un documento para poder guardarlo.");
        }
    }

    // DONE  ############
    public void dT1Similars(ActionEvent actionEvent) {
        if (!dT1DocAuthor.getText().isEmpty() && !dT1DocTitle.getText().isEmpty() && !dT1DocContent.getText().isEmpty()) {
            dT4Author.setValue(dT1CurrentAuthor);
            dT4Title.setValue(dT1CurrentTitle);
            dT4DocClear();
            dT4LoadDocumentResults(false);
            documentsTab.getSelectionModel().select(3);
        } else {
            status.setText("Por favor, visualiza un documento para poder buscar parecidos.");
        }
    }


    // dT2

    // DONE  ############
    public void dT2ExpressionClear(ActionEvent actionEvent) {
        dT2Expression.clear();
    }

    // DONE  ############
    public void dT2Search(ActionEvent actionEvent) {
        dT2LoadDocumentResults(false);
    }

    // DONE  ############
    public void dT2LoadDocumentResults(boolean isReload) {
        if (!isReload) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);
        }

        Task<Void> task = new Task<Void>() {
            @Override public Void call() throws InterruptedException {
                if (!isReload) updateMessage("Realizando búsqueda de documentos...");
                //updateProgress(1, 2);

                dT2TableView.getItems().clear();

                //status.setText("Realizando búsqueda de documentos...");

                // Si solo esta puesto el autor => Buscar todos los autores con ese prefijo => Mostrar los documentos de todos ellos
                if (dT2Expression.getText().equals("")) {
                    if (!isReload) updateMessage("Debes introducir una expresión para realizar la búsqueda.");
                } else {
                    String expression = dT2Expression.getText();
                    dT2ResultDocuments = mLibrary.getBooleanDocuments(expression);
                    if (dT2ResultDocuments.size() == 0) {
                        if (!isReload) updateMessage("No se han encontrado resultados.");
                    } else {
                        if (!isReload) updateMessage("Búsqueda finalizada.");
                        for(int i = 0; i < dT2ResultDocuments.size(); i++){
                            dT2TableView.getItems().add(i);
                        }
                        dT2TableViewTitle.setCellValueFactory(cellData -> {
                            Integer titleIndex = cellData.getValue();
                            return new ReadOnlyStringWrapper(dT2ResultDocuments.get(titleIndex).getKey());
                        });
                        dT2TableViewAuthor.setCellValueFactory(cellData -> {
                            Integer titleIndex = cellData.getValue();
                            return new ReadOnlyStringWrapper(dT2ResultDocuments.get(titleIndex).getValue());
                        });
                    }
                }
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            if (!isReload) {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();
            }
        });

        if (!isReload) {
            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());
        }

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    // DONE  ############
    public void dT2Remove(ActionEvent actionEvent) {
        if (dT2TableView.getSelectionModel().getSelectedIndex() != -1) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                public Boolean call() throws InterruptedException {
                    updateMessage("Eliminando documento...");

                    int itemIndex = dT2TableView.getSelectionModel().getSelectedIndex();
                    String documentTitle = dT2ResultDocuments.get(itemIndex).getKey();
                    String authorName = dT2ResultDocuments.get(itemIndex).getValue();

                    Boolean success = mLibrary.removeDocument(authorName, documentTitle);
                    // Si se estaba visualizando ese documento => vaciar la vista de documento
                    if (authorName.equals(dT2DocAuthor.getText()) && documentTitle.equals(dT2DocTitle.getText())) {
                        dT2DocAuthor.clear();
                        dT2DocTitle.clear();
                        dT2DocContent.clear();
                    }
                    return success;
                }
            };

            task.setOnSucceeded(event -> {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();

                if (task.getValue()) status.setText("Documento eliminado satisfactoriamente.");
                else status.setText("El documento no existe.");

                // Actualizar lista de resultados
                updateCombos();
                dT1LoadDocumentResults(true);
                dT2LoadDocumentResults(true);
            });

            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            status.setText("Por favor, selecciona un documento para poder eliminarlo.");
        }
    }

    // DONE  ############
    public void dT2Show(ActionEvent actionEvent) {
        if (dT2TableView.getSelectionModel().getSelectedIndex() != -1) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                public Boolean call() throws InterruptedException {
                    updateMessage("Abriendo documento...");

                    int itemIndex = dT2TableView.getSelectionModel().getSelectedIndex();
                    dT2CurrentTitle = dT2ResultDocuments.get(itemIndex).getKey();
                    dT2CurrentAuthor = dT2ResultDocuments.get(itemIndex).getValue();

                    Boolean success = false;
                    String content = mLibrary.getDocumentContent(dT2CurrentAuthor, dT2CurrentTitle);
                    if (content != null) {
                        success = true;
                        dT2CurrentContent = content;
                        dT2DocTitle.setText(dT2CurrentTitle);
                        dT2DocAuthor.setText(dT2CurrentAuthor);
                        dT2DocContent.setText(dT2CurrentContent);
                    }
                    return success;
                }
            };

            task.setOnSucceeded(event -> {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();

                if (task.getValue()) status.setText("Documento abierto satisfactoriamente.");
                else status.setText("El documento no existe.");
            });

            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            status.setText("Por favor, selecciona un documento para poder verlo.");
        }
    }

    // DONE  ############
    public void dT2DocClear(ActionEvent actionEvent) {
        dT2DocAuthor.clear();
        dT2DocTitle.clear();
        dT2DocContent.clear();
    }

    // DONE  ############
    public void dT2Save(ActionEvent actionEvent) {
        if (!dT2DocAuthor.getText().isEmpty() && !dT2DocTitle.getText().isEmpty() && !dT2DocContent.getText().isEmpty()) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                public Boolean call() throws InterruptedException {
                    updateMessage("Guardando documento...");

                    if (!dT2DocAuthor.getText().equals(dT2CurrentAuthor)) {
                        mLibrary.modifyDocumentAuthor(dT2CurrentAuthor, dT2CurrentTitle, dT2DocAuthor.getText());
                        dT2CurrentAuthor = dT2DocAuthor.getText();
                    }
                    if (!dT2DocTitle.getText().equals(dT2CurrentTitle)) {
                        mLibrary.modifyDocumentTitle(dT2CurrentAuthor, dT2CurrentTitle, dT2DocTitle.getText());
                        dT2CurrentTitle = dT2DocTitle.getText();
                    }
                    if (!dT2DocContent.getText().equals(dT2CurrentContent)) {
                        mLibrary.modifyDocumentContent(dT2CurrentAuthor, dT2CurrentTitle, dT2DocContent.getText());
                        dT2CurrentContent = dT2DocContent.getText();
                    }

                    return true;
                }
            };

            task.setOnSucceeded(event -> {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();

                if (task.getValue()) status.setText("Documento guardado satisfactoriamente.");
                else status.setText("El documento no se ha podido eliminar.");

                // Actualizar lista de resultados
                updateCombos();
                dT1LoadDocumentResults(true);
                dT2LoadDocumentResults(true);
            });

            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            status.setText("Por favor, edita un documento para poder guardarlo.");
        }
    }

    // DONE  ############
    public void dT2Similars(ActionEvent actionEvent) {
        if (!dT2DocAuthor.getText().isEmpty() && !dT2DocTitle.getText().isEmpty() && !dT2DocContent.getText().isEmpty()) {
            dT4Author.setValue(dT2CurrentAuthor);
            dT4Title.setValue(dT2CurrentTitle);
            dT4DocClear();
            dT4LoadDocumentResults(false);
            documentsTab.getSelectionModel().select(3);
        } else {
            status.setText("Por favor, visualiza un documento para poder buscar parecidos.");
        }
    }

    // dT3

    // DONE  ############
    public void dT3AddFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);

        progressBar.setProgress(0);
        progressBar.setVisible(true);

        Task<Boolean> task = new Task<Boolean>() {
            @Override public Boolean call() throws InterruptedException {
                updateMessage("Cargando documento desde fichero...");

                boolean success = false;

                int nFiles = 1;
                int currentFile = 1;

                if (nFiles > 0) {
                    success = true;
                    updateProgress(currentFile, nFiles);
                    dT3AddDocumentFile(file.toString());
                }
                return success;
            }
        };

        task.setOnSucceeded(event -> {
            progressBar.progressProperty().unbind();
            progressBar.setVisible(false);
            status.textProperty().unbind();

            Boolean success = task.getValue();
            if (success) status.setText("Documento añadido satisfactoriamente.");
            else status.setText("El documento no se ha podido añadir.");

            updateCombos();
            dT1LoadDocumentResults(true);
            aT1LoadAuthorResults(true);
        });

        status.textProperty().bind(task.messageProperty());
        progressBar.progressProperty().bind(task.progressProperty());

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();


    }

    // DONE  ############
    public void dT3AddFolder(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Stage stage = new Stage();
        List<File> files = fileChooser.showOpenMultipleDialog(stage);

        progressBar.setProgress(0);
        progressBar.setVisible(true);

        Task<Boolean> task = new Task<Boolean>() {
            @Override public Boolean call() throws InterruptedException {
                updateMessage("Cargando documentos desde ficheros...");
                //updateProgress(1, 2);

                boolean success = false;

                int nFiles = files.size();
                int currentFile = 1;

                if (nFiles > 0) {
                    success = true;
                    for (File file : files) {
                        updateProgress(currentFile, nFiles);
                        dT3AddDocumentFile(file.toString());
                        currentFile++;
                    }
                }
                return success;
            }
        };

        task.setOnSucceeded(event -> {
            progressBar.progressProperty().unbind();
            progressBar.setVisible(false);
            status.textProperty().unbind();

            Boolean success = task.getValue();
            if (success) status.setText("Documentos añadidos satisfactoriamente.");
            else status.setText("Los documentos no se han podido añadir.");

            updateCombos();
            dT1LoadDocumentResults(true);
            aT1LoadAuthorResults(true);
        });

        status.textProperty().bind(task.messageProperty());
        progressBar.progressProperty().bind(task.progressProperty());

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    // DONE  ############
    public void dT3DragOver(DragEvent e) {
        final Dragboard db = e.getDragboard();

        //final boolean isAccepted = db.getFiles().get(0).getName().toLowerCase().endsWith(".txt")
        //        || db.getFiles().get(0).getName().toLowerCase().endsWith(".txtt");
        final boolean isAccepted = db.getFiles().get(0).getName().toLowerCase().endsWith(".txt");

        if (db.hasFiles()) {
            if (isAccepted) {
                dT3Drag.setStyle("-fx-border-color: #6eca18;"
                        + "-fx-border-width: 5;"
                        + "-fx-background-color: #C6C6C6;"
                        + "-fx-border-style: dashed;");
                e.acceptTransferModes(TransferMode.COPY);
            }
        } else {
            e.consume();
        }
    }

    // DONE  ############
    public void dT3DragExited(DragEvent e) {
        dT3Drag.setStyle("-fx-border-color: #C6C6C6;");
    }

    // DONE  ############
    public void dT3DragDropped(DragEvent e) {
        final Dragboard db = e.getDragboard();
        List<File> files = db.getFiles();

        progressBar.setProgress(0);
        progressBar.setVisible(true);

        Task<Boolean> task = new Task<Boolean>() {
            @Override public Boolean call() throws InterruptedException {
                updateMessage("Cargando documentos desde ficheros...");
                //updateProgress(1, 2);

                boolean success = false;
                //if (db.hasFiles()) {

                int nFiles = files.size();
                int currentFile = 1;
                if (nFiles > 0) {
                    success = true;
                    // Only get the first file from the list
                    for (File file : files) {
                        updateProgress(currentFile, nFiles);
                        dT3AddDocumentFile(file.toString());
                        currentFile++;
                    }
                }
                return success;
            }
        };

        task.setOnSucceeded(event -> {
            progressBar.progressProperty().unbind();
            progressBar.setVisible(false);
            status.textProperty().unbind();

            Boolean success = task.getValue();
            e.setDropCompleted(success);
            e.consume();
            if (success) status.setText("Documentos añadidos satisfactoriamente.");
            else status.setText("Los documentos no se han podido añadir.");

            // Update combos and views
            updateCombos();
            dT1LoadDocumentResults(true);
            aT1LoadAuthorResults(true);
        });

        status.textProperty().bind(task.messageProperty());
        progressBar.progressProperty().bind(task.progressProperty());

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    // DONE  ############
    public void dT3AddDocumentFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String author = br.readLine();
            //author = author.substring(1);
            String title = br.readLine();
            String content = "";
            String line = br.readLine();
            int i = 0;
            while (line != null) {
                if (i != 0) content += "\n"+line;
                else content = line;
                line = br.readLine();
                i++;
            }
            mLibrary.addDocument(author, title, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // DONE  ############
    public void dT3Add(ActionEvent actionEvent) {
        if (!dT3DocAuthor.getText().isEmpty() && !dT3DocTitle.getText().isEmpty() && !dT3DocContent.getText().isEmpty()) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override public Boolean call() throws InterruptedException {
                    updateMessage("Añadiendo documento...");

                    boolean success = false;

                    String docAuthor = dT3DocAuthor.getText();
                    String docTitle = dT3DocTitle.getText();
                    String docContent = dT3DocContent.getText();
                    if (!docAuthor.equals("") && !docTitle.equals("") && !docContent.equals("")) {
                        success = true;
                        mLibrary.addDocument(docAuthor, docTitle, docContent);
                    }

                    // Clear form
                    dT3DocAuthor.clear();
                    dT3DocTitle.clear();
                    dT3DocContent.clear();

                    return success;
                }
            };

            task.setOnSucceeded(event -> {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();

                Boolean success = task.getValue();
                if (success) status.setText("Documento añadido satisfactoriamente.");
                else status.setText("El documento no se ha podido añadir.");

                updateCombos();
                dT1LoadDocumentResults(true);
                aT1LoadAuthorResults(true);
            });

            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            status.setText("Por favor, introduce los datos para poder añadir el documento.");
        }
    }


    // dT4

    // DONE  ############
    public void dT4UpdateAuthorCombo() {
        String current = (String) dT4Author.getValue();
        ObservableList<String> authorComboItems = FXCollections.observableArrayList();
        dT4Author.setItems(authorComboItems);
        authorComboItems.addAll(mLibrary.getAuthorNames());
        dT4Author.setValue(current);
    }

    // DONE  ############
    public void dT4UpdateTitleCombo() {
        String current = (String) dT4Title.getValue();
        ObservableList<String> titleComboItems = FXCollections.observableArrayList();
        dT4Title.setItems(titleComboItems);
        titleComboItems.addAll(mLibrary.getDocumentTitles());
        dT4Title.setValue(current);
    }

    // DONE  ############
    public void dT4Search(ActionEvent actionEvent) {
        dT4LoadDocumentResults(false);
    }

    // DONE  ############
    public void dT4LoadDocumentResults(boolean isReload) {
        if (!isReload) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);
        }

        Task<Void> task = new Task<Void>() {
            @Override public Void call() throws InterruptedException {
                if (!isReload) updateMessage("Realizando búsqueda de documentos parecidos...");
                //updateProgress(1, 2);

                dT4TableView.getItems().clear();

                if (dT4Author.getValue() != null && dT4Title.getValue() != null && !dT4NResults.getText().equals("")) {
                    String author = dT4Author.getValue().toString();
                    String title = dT4Title.getValue().toString();
                    String algorithm = dT4Algorithm.getValue().toString();
                    int nResults = Integer.parseInt(dT4NResults.getText());
                    dT4ResultDocuments = mLibrary.getSimilarDocuments(author, title, nResults);
                    if (dT4ResultDocuments.size() == 0) {
                        if (!isReload) updateMessage("No se han encontrado documentos parecidos.");
                    } else {
                        if (!isReload) updateMessage("Búsqueda finalizada.");
                        for (int i = 0; i < dT4ResultDocuments.size(); i++) {
                            dT4TableView.getItems().add(i);
                        }
                        dT4TableViewTitle.setCellValueFactory(cellData -> {
                            Integer titleIndex = cellData.getValue();
                            return new ReadOnlyStringWrapper(dT4ResultDocuments.get(titleIndex).getKey());
                        });
                        dT4TableViewAuthor.setCellValueFactory(cellData -> {
                            Integer titleIndex = cellData.getValue();
                            return new ReadOnlyStringWrapper(dT4ResultDocuments.get(titleIndex).getValue().getKey());
                        });
                        dT4TableViewSimilarity.setCellValueFactory(cellData -> {
                            Integer titleIndex = cellData.getValue();
                            return new ReadOnlyStringWrapper(String.valueOf(String.format("%.2f", dT4ResultDocuments.get(titleIndex).getValue().getValue()*100))+" %");
                        });
                    }
                } else {
                    if (!isReload) updateMessage("Debes introducir los parámetros para realizar una búsqueda.");
                }
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            if (!isReload) {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();
            }
        });

        if (!isReload) {
            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());
        }

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    // DONE  ############
    public void dT4Remove(ActionEvent actionEvent) {
        if (dT4TableView.getSelectionModel().getSelectedIndex() != -1) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                public Boolean call() throws InterruptedException {
                    updateMessage("Eliminando documento...");

                    int itemIndex = dT4TableView.getSelectionModel().getSelectedIndex();
                    String documentTitle = dT4ResultDocuments.get(itemIndex).getKey();
                    String authorName = dT4ResultDocuments.get(itemIndex).getValue().getKey();

                    Boolean success = mLibrary.removeDocument(authorName, documentTitle);
                    // Si se estaba visualizando ese documento => vaciar la vista de documento
                    if (authorName.equals(dT4DocAuthor.getText()) && documentTitle.equals(dT4DocTitle.getText())) {
                        dT4DocAuthor.clear();
                        dT4DocTitle.clear();
                        dT4DocContent.clear();
                    }
                    return success;
                }
            };

            task.setOnSucceeded(event -> {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();

                if (task.getValue()) status.setText("Documento eliminado satisfactoriamente.");
                else status.setText("El documento no existe.");

                // Actualizar lista de resultados
                updateCombos();
                dT1LoadDocumentResults(true);
                dT4LoadDocumentResults(true);
                aT1LoadAuthorResults(true);
            });

            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            status.setText("Por favor, selecciona un documento para poder eliminarlo.");
        }
    }

    // DONE  ############
    public void dT4Show(ActionEvent actionEvent) {
        if (dT4TableView.getSelectionModel().getSelectedIndex() != -1) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                public Boolean call() throws InterruptedException {
                    updateMessage("Abriendo documento...");

                    int itemIndex = dT4TableView.getSelectionModel().getSelectedIndex();
                    dT4CurrentTitle = dT4ResultDocuments.get(itemIndex).getKey();
                    dT4CurrentAuthor = dT4ResultDocuments.get(itemIndex).getValue().getKey();

                    Boolean success = false;
                    String content = mLibrary.getDocumentContent(dT4CurrentAuthor, dT4CurrentTitle);
                    if (content != null) {
                        success = true;
                        dT4CurrentContent = content;
                        dT4DocTitle.setText(dT4CurrentTitle);
                        dT4DocAuthor.setText(dT4CurrentAuthor);
                        dT4DocContent.setText(dT4CurrentContent);
                    }
                    return success;
                }
            };

            task.setOnSucceeded(event -> {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();

                if (task.getValue()) status.setText("Documento abierto satisfactoriamente.");
                else status.setText("El documento no existe.");
            });

            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            status.setText("Por favor, selecciona un documento para poder verlo.");
        }
    }

    // DONE  ############
    public void dT4DocClear() {
        dT4DocAuthor.clear();
        dT4DocTitle.clear();
        dT4DocContent.clear();
    }

    // DONE  ############
    public void dT4DocClear(ActionEvent actionEvent) {
        dT4DocClear();
    }

    // DONE  ############
    public void dT4Save(ActionEvent actionEvent) {
        if (!dT4DocAuthor.getText().isEmpty() && !dT4DocTitle.getText().isEmpty() && !dT4DocContent.getText().isEmpty()) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                public Boolean call() throws InterruptedException {
                    updateMessage("Guardando documento...");

                    if (!dT4DocAuthor.getText().equals(dT4CurrentAuthor)) {
                        mLibrary.modifyDocumentAuthor(dT4CurrentAuthor, dT4CurrentTitle, dT4DocAuthor.getText());
                        dT4CurrentAuthor = dT4DocAuthor.getText();
                    }
                    if (!dT4DocTitle.getText().equals(dT4CurrentTitle)) {
                        mLibrary.modifyDocumentTitle(dT4CurrentAuthor, dT4CurrentTitle, dT4DocTitle.getText());
                        dT4CurrentTitle = dT1DocTitle.getText();
                    }
                    if (!dT4DocContent.getText().equals(dT4CurrentContent)) {
                        mLibrary.modifyDocumentContent(dT4CurrentAuthor, dT4CurrentTitle, dT4DocContent.getText());
                        dT4CurrentContent = dT4DocContent.getText();
                    }

                    return true;
                }
            };

            task.setOnSucceeded(event -> {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();

                if (task.getValue()) status.setText("Documento guardado satisfactoriamente.");
                else status.setText("El documento no se ha podido eliminar.");

                // Actualizar lista de resultados
                updateCombos();
                dT1LoadDocumentResults(true);
                dT4LoadDocumentResults(true);
                aT1LoadAuthorResults(true);
            });

            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            status.setText("Por favor, edita un documento para poder guardarlo.");
        }
    }

    // DONE  ############
    public void dT4Similars(ActionEvent actionEvent) {
        if (!dT4DocAuthor.getText().isEmpty() && !dT4DocTitle.getText().isEmpty() && !dT4DocContent.getText().isEmpty()) {
            dT4Author.setValue(dT4CurrentAuthor);
            dT4Title.setValue(dT4CurrentTitle);
            dT4LoadDocumentResults(false);
        } else {
            status.setText("Por favor, visualiza un documento para poder buscar parecidos.");
        }
    }

    // aT1

    // DONE  ############
    public void aT1AuthorClear(ActionEvent actionEvent) {
        aT1Author.setValue("");
    }

    // DONE  ############
    public void aT1UpdateAuthorCombo() {
        String current = (String) aT1Author.getValue();
        ObservableList<String> authorComboItems = FXCollections.observableArrayList();
        aT1Author.setItems(authorComboItems);
        authorComboItems.addAll(mLibrary.getAuthorNames());
        aT1Author.setValue(current);
    }

    // DONE  ############
    public void aT1Search(ActionEvent actionEvent) {
        aT1LoadAuthorResults(false);
    }

    // DONE  ############
    public void aT1LoadAuthorResults(boolean isReload) {
        if (!isReload) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);
        }

        Task<Void> task = new Task<Void>() {
            @Override public Void call() throws InterruptedException {
                if (!isReload) updateMessage("Realizando búsqueda de autores...");
                //updateProgress(1, 2);

                aT1TableView.getItems().clear();



                // Si esta puesto el autor => Buscar todos los autores con ese prefijo
                if (aT1Author.getValue() != null) {
                    String author = aT1Author.getValue().toString();
                    aT1ResultAuthors = new ArrayList<>(mLibrary.getAuthorsByPrefix(author));

                    if (aT1ResultAuthors.size() == 0) {
                        if (!isReload) updateMessage("No se han encontrado autores.");
                    } else {
                        if (!isReload) updateMessage("Búsqueda finalizada.");
                        for(int i = 0; i < aT1ResultAuthors.size(); i++){
                            aT1TableView.getItems().add(i);
                        }
                        aT1TableViewAuthor.setCellValueFactory(cellData -> {
                            Integer titleIndex = cellData.getValue();
                            return new ReadOnlyStringWrapper(aT1ResultAuthors.get(titleIndex));
                        });
                    }
                }
                // Si no esta puesto el autor => Mostrar todos los autores
                else if (aT1Author.getValue() == null) {
                    aT1ResultAuthors = new ArrayList<>(mLibrary.getAuthorNames());
                    if (!isReload) updateMessage("Se muestra la lista completa de autores.");

                    for(int i = 0; i < aT1ResultAuthors.size(); i++){
                        aT1TableView.getItems().add(i);
                    }
                    aT1TableViewAuthor.setCellValueFactory(cellData -> {
                        Integer titleIndex = cellData.getValue();
                        return new ReadOnlyStringWrapper(aT1ResultAuthors.get(titleIndex));
                    });
                }

                return null;
            }
        };

        task.setOnSucceeded(event -> {
            if (!isReload) {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();
            }
        });

        if (!isReload) {
            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());
        }

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    // DONE  ############
    public void aT1Remove(ActionEvent actionEvent) {
        if (aT1TableView.getSelectionModel().getSelectedIndex() != -1) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                public Boolean call() throws InterruptedException {
                    updateMessage("Eliminando autor...");

                    int itemIndex = aT1TableView.getSelectionModel().getSelectedIndex();
                    String authorName = aT1ResultAuthors.get(itemIndex);


                    Boolean success = mLibrary.removeAuthor(authorName);
                    // Si se estaba visualizando ese autor => vaciar la vista de documento
                    if (authorName.equals(aT1AuthName.getText())) {
                        aT1AuthName.clear();
                    }
                    return success;
                }
            };

            task.setOnSucceeded(event -> {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();

                if (task.getValue()) status.setText("Autor eliminado satisfactoriamente.");
                else status.setText("El autor no existe.");

                // Actualizar lista de resultados
                updateCombos();
                dT1LoadDocumentResults(true);
                aT1LoadAuthorResults(true);
            });

            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            status.setText("Por favor, selecciona un autor para poder eliminarlo.");
        }
    }

    // DONE  ############
    public void aT1Show(ActionEvent actionEvent) {
        if (aT1TableView.getSelectionModel().getSelectedIndex() != -1) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                public Boolean call() throws InterruptedException {
                    updateMessage("Abriendo autor...");

                    int itemIndex = aT1TableView.getSelectionModel().getSelectedIndex();
                    aT1CurrentAuthor = aT1ResultAuthors.get(itemIndex);

                    aT1AuthName.setText(aT1CurrentAuthor);

                    boolean success = true;
                    return success;
                }
            };

            task.setOnSucceeded(event -> {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();

                if (task.getValue()) status.setText("Autor abierto satisfactoriamente.");
                else status.setText("El autor no existe.");
            });

            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            status.setText("Por favor, selecciona un autor para poder verlo.");
        }
    }

    // DONE  ############
    public void aT1AuthClear(ActionEvent actionEvent) {
        aT1AuthName.clear();
    }

    // DONE  ############
    public void aT1Save(ActionEvent actionEvent) {
        if (!aT1AuthName.getText().isEmpty()) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                public Boolean call() throws InterruptedException {
                    updateMessage("Guardando el autor...");

                    if (!aT1AuthName.getText().equals(aT1CurrentAuthor)) {
                        mLibrary.modifyAuthor(aT1CurrentAuthor, aT1AuthName.getText());
                        aT1CurrentAuthor = aT1AuthName.getText();
                    }

                    return true;
                }
            };

            task.setOnSucceeded(event -> {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();

                if (task.getValue()) status.setText("Autor guardado satisfactoriamente.");
                else status.setText("El autor no se ha podido guardar.");

                // Actualizar lista de resultados
                updateCombos();
                dT1LoadDocumentResults(true);
                aT1LoadAuthorResults(true);
            });

            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            status.setText("Por favor, edita un autor para poder guardarlo.");
        }




    }

    public void aT2Add(ActionEvent actionEvent) {
        if (!aT2AuthName.getText().isEmpty()) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override public Boolean call() throws InterruptedException {
                    updateMessage("Añadiendo autor...");

                    boolean success = false;

                    String authName = aT2AuthName.getText();

                    if (!authName.equals("")) {
                        success = true;
                        mLibrary.addAuthor(authName);
                    }

                    // Clear form
                    aT2AuthName.clear();

                    return success;
                }
            };

            task.setOnSucceeded(event -> {
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                status.textProperty().unbind();

                Boolean success = task.getValue();
                if (success) status.setText("Autor añadido satisfactoriamente.");
                else status.setText("El autor no se ha podido añadir.");

                updateCombos();
                dT1LoadDocumentResults(true);
                aT1LoadAuthorResults(true);
            });

            status.textProperty().bind(task.messageProperty());
            progressBar.progressProperty().bind(task.progressProperty());

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            status.setText("Por favor, introduce los datos para poder añadir el autor.");
        }
    }
}
