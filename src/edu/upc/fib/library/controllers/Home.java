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

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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
    }

    public void saveStatus() {
        mLibrary.saveStatus();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Llenar los comboboxes
        ObservableList<String> dT4AlgorithmComboItems = FXCollections.observableArrayList();
        dT4Algorithm.setItems(dT4AlgorithmComboItems);
        dT4AlgorithmComboItems.add("A");
        dT4AlgorithmComboItems.add("B");
        dT4Algorithm.setValue("A");

        // Llenar las listas de resultados iniciales
        dT1LoadDocumentResults(false);
        aT1LoadAuthorResults(true);

        updateCombos();
    }

    // Actualizar las opciones de los combo boxes
    public void updateCombos() {
        dT1UpdateAuthorCombo();
        dT1UpdateTitleCombo();
        dT4UpdateAuthorCombo();
        dT4UpdateTitleCombo();
        aT1UpdateAuthorCombo();
    }


    // dT1

    public void dT1AuthorClear(ActionEvent actionEvent) {
        dT1Author.setValue("");
    }

    public void dT1UpdateAuthorCombo() {
        String current = (String) dT1Author.getValue();
        ObservableList<String> authorComboItems = FXCollections.observableArrayList();
        dT1Author.setItems(authorComboItems);
        authorComboItems.addAll(mLibrary.getAuthorNames());
        dT1Author.setValue(current);
    }

    public void dT1UpdateAuthorCombo(ActionEvent actionEvent) {
        dT1UpdateTitleCombo();
        dT1Title.setValue("");
    }

    @FXML
    public void dT1TitleClear(ActionEvent actionEvent) {
        dT1Title.setValue("");
    }

    @FXML
    private void dT1UpdateTitleCombo() {
        String current = (String) dT1Title.getValue();
        ObservableList<String> titleComboItems = FXCollections.observableArrayList();
        dT1Title.setItems(titleComboItems);
//        if (dT1Author.getValue() != null && mLibrary.existsAuthor(dT1Author.getValue().toString())) {
//            titleComboItems.addAll(mLibrary.getAuthorDocumentTitles(dT4Author.getValue().toString()));
//        } else {
//            titleComboItems.addAll(mLibrary.getDocumentTitles());
//        }
        titleComboItems.addAll(mLibrary.getDocumentTitles());
        dT1Title.setValue(current);
    }

    public void dT1Search(ActionEvent actionEvent) {
        dT1LoadDocumentResults(false);
    }

    public void dT1LoadDocumentResults(boolean isReload) {
        if (!isReload) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);
        }

        Task<Void> task = new Task<Void>() {
            @Override public Void call() throws InterruptedException {
                if (!isReload) updateMessage("Realizando búsqueda de documentos...");

                dT1TableView.getItems().clear();

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
                    if (!isReload) updateMessage("Búsqueda finalizada.");
                }
                // Si no esta puesto ninguno => Mostrar todos los documentos
                else if (dT1Title.getValue() == null && dT1Author.getValue() == null) {
                    dT1ResultDocuments = mLibrary.getDocumentsByPrefixes(null, null);
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
                    if (!isReload) updateMessage("Se muestra la lista completa de documentos.");
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

    public void dT1DocClear() {
        dT1DocAuthor.clear();
        dT1DocTitle.clear();
        dT1DocContent.clear();
    }

    public void dT1DocClear(ActionEvent actionEvent) {
        dT1DocClear();
    }

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

    public void dT2ExpressionClear(ActionEvent actionEvent) {
        dT2Expression.clear();
    }

    public void dT2Search(ActionEvent actionEvent) {
        dT2LoadDocumentResults(false);
    }

    public void dT2LoadDocumentResults(boolean isReload) {
        if (!isReload) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);
        }

        Task<Void> task = new Task<Void>() {
            @Override public Void call() throws InterruptedException {
                if (!isReload) updateMessage("Realizando búsqueda de documentos...");

                dT2TableView.getItems().clear();

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

    public void dT2DocClear() {
        dT2DocAuthor.clear();
        dT2DocTitle.clear();
        dT2DocContent.clear();
    }
    public void dT2DocClear(ActionEvent actionEvent) {
        dT2DocClear();
    }

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

    public void dT3AddFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
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
    }

    public void dT3AddFolder(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Stage stage = new Stage();
        List<File> files = fileChooser.showOpenMultipleDialog(stage);

        if (files != null) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                public Boolean call() throws InterruptedException {
                    updateMessage("Cargando documentos desde ficheros...");

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
    }

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

    public void dT3DragExited(DragEvent e) {
        dT3Drag.setStyle("-fx-border-color: #C6C6C6;");
    }

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

    public void dT4UpdateAuthorCombo() {
        String current = (String) dT4Author.getValue();
        ObservableList<String> authorComboItems = FXCollections.observableArrayList();
        dT4Author.setItems(authorComboItems);
        authorComboItems.addAll(mLibrary.getAuthorNames());
        dT4Author.setValue(current);
    }

    public void dT4UpdateAuthorCombo(ActionEvent actionEvent) {
        dT4UpdateTitleCombo();
        dT4Title.setValue("");
    }

    public void dT4UpdateTitleCombo() {
        String current = (String) dT4Title.getValue();
        ObservableList<String> titleComboItems = FXCollections.observableArrayList();
        dT4Title.setItems(titleComboItems);
        titleComboItems.clear();
        Set<String> list;
        if (dT4Author.getValue() != null) {
            list = mLibrary.getAuthorDocumentTitles(dT4Author.getValue().toString());
            if (list != null) {
                titleComboItems.addAll(list);
            } else {
                titleComboItems.addAll(mLibrary.getDocumentTitles());
            }
        } else {
            titleComboItems.addAll(mLibrary.getDocumentTitles());
        }
        dT4Title.setValue(current);
    }

    public void dT4Search(ActionEvent actionEvent) {
        dT4LoadDocumentResults(false);
    }

    public void dT4LoadDocumentResults(boolean isReload) {
        if (!isReload) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);
        }

        Task<Void> task = new Task<Void>() {
            @Override public Void call() throws InterruptedException {
                if (!isReload) updateMessage("Realizando búsqueda de documentos parecidos...");

                dT4TableView.getItems().clear();

                if (dT4Author.getValue() != null && dT4Title.getValue() != null && !dT4NResults.getText().equals("")) {
                    String author = dT4Author.getValue().toString();
                    String title = dT4Title.getValue().toString();
                    String algorithm = dT4Algorithm.getValue().toString();
                    int algorithmInt = 1;
                    if (algorithm.equals("A")) algorithmInt = 1;
                    else if (algorithm.equals("B")) algorithmInt = 2;
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

    public void dT4DocClear() {
        dT4DocAuthor.clear();
        dT4DocTitle.clear();
        dT4DocContent.clear();
    }

    public void dT4DocClear(ActionEvent actionEvent) {
        dT4DocClear();
    }

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

    public void aT1AuthorClear(ActionEvent actionEvent) {
        aT1Author.setValue("");
    }

    public void aT1UpdateAuthorCombo() {
        String current = (String) aT1Author.getValue();
        ObservableList<String> authorComboItems = FXCollections.observableArrayList();
        aT1Author.setItems(authorComboItems);
        authorComboItems.addAll(mLibrary.getAuthorNames());
        aT1Author.setValue(current);
    }

    public void aT1Search(ActionEvent actionEvent) {
        aT1LoadAuthorResults(false);
    }

    public void aT1LoadAuthorResults(boolean isReload) {
        if (!isReload) {
            progressBar.setProgress(0);
            progressBar.setVisible(true);
        }

        Task<Void> task = new Task<Void>() {
            @Override public Void call() throws InterruptedException {
                if (!isReload) updateMessage("Realizando búsqueda de autores...");

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

    public void aT1AuthClear() {
        aT1AuthName.clear();
    }

    public void aT1AuthClear(ActionEvent actionEvent) {
        aT1AuthClear();
    }

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

    public void ajTReset(ActionEvent actionEvent) {
        mLibrary.restartStatus();

        dT1Author.setValue("");
        dT1Title.setValue("");
        dT1DocClear();
        dT1LoadDocumentResults(true);

        dT2Expression.clear();
        dT2DocClear();
        dT2LoadDocumentResults(true);

        dT3DocAuthor.clear();
        dT3DocTitle.clear();
        dT3DocContent.clear();

        dT4DocClear();
        dT4LoadDocumentResults(true);

        dT4Author.setValue("");
        dT4Title.setValue("");

        aT1Author.setValue("");
        aT1AuthClear();
        aT1LoadAuthorResults(true);
        updateCombos();
    }

}
