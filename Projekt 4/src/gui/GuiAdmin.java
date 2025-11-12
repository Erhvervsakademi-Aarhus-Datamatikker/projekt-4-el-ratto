package gui;

import Controller.Controller;
import Storage.Storage;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Bestilling;
import model.Forstilling;
import model.Kunde;
import model.Plads;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;


public class GuiAdmin extends Application {
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Teater Admin");
        GridPane pane = new GridPane();
        initContent(pane);
        Scene scene = new Scene(pane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    VBox forstillingBox = new VBox();
    VBox KundeBox = new VBox();
    VBox bestillingBox = new VBox();

    // forstilling ListView, TextFields and Button
    ListView<Forstilling> listViewForstillinger = new ListView<>();
    TextField textFieldForstillingNavn = new TextField();
    TextField textFieldForstillingStartDato = new TextField();
    TextField textFieldForstillingSlutDato = new TextField();
    Button buttonAddForstilling = new Button("Tilføj Forstilling");

    // kunde Listview, TextFields and Button
    ListView<Kunde> listViewKunder = new ListView<>();
    TextField textFieldKundeNavn = new TextField();
    TextField textFieldKundeMobilNummer = new TextField();
    Button buttonAddKunde = new Button("Tilføj Kunde");

    // pladser og dato ListView, TextFields and Button
    ListView<Plads> listViewPladser = new ListView<>();
    DatePicker datePicker = new DatePicker();
    TextField textFieldDato = new TextField();
    Button buttonOpretBestilling = new Button("Opret Bestilling");


    private void initContent(GridPane pane) {
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        // Forstilling Labels (local)
        Label labelForstillinger = new Label("Forestillinger:");
        Label labelForstillingNavn = new Label("Navn:");
        Label labelForstillingStartDato = new Label("Start Dato:");
        Label labelForstillingSlutDato = new Label("Slut Dato:");

        // kunde labels (local)
        Label labelKunder = new Label("Kunder");
        Label labelKundeNavn = new Label("Kunde navn:");
        Label labelKundeMobilNummer = new Label("Mobil Nummer:");

        // pladslabels
        Label labelplads = new Label("pladser");
        Label labelDato = new Label("dato");

        // Configure forstilling vbox
        forstillingBox.setSpacing(6);
        forstillingBox.setPadding(new Insets(5));
        forstillingBox.getChildren().clear();
        forstillingBox.getChildren().addAll(labelForstillinger,
                listViewForstillinger,
                labelForstillingNavn, textFieldForstillingNavn,
                labelForstillingStartDato, textFieldForstillingStartDato,
                labelForstillingSlutDato, textFieldForstillingSlutDato,
                buttonAddForstilling
        );
        forstillingBox.setPrefWidth(300);
        javafx.scene.layout.VBox.setVgrow(listViewForstillinger, Priority.ALWAYS);
        javafx.scene.layout.GridPane.setHgrow(forstillingBox, Priority.ALWAYS);
        javafx.scene.layout.GridPane.setVgrow(forstillingBox, Priority.ALWAYS);


        // Configure KundeBox (Middle)
        KundeBox.setSpacing(6);
        KundeBox.setPadding(new Insets(5));
        KundeBox.getChildren().clear();
        KundeBox.getChildren().addAll(
                labelKunder,
                listViewKunder,
                labelKundeNavn, textFieldKundeNavn,
                labelKundeMobilNummer, textFieldKundeMobilNummer,
                buttonAddKunde
        );
        KundeBox.setPrefWidth(200);
        javafx.scene.layout.VBox.setVgrow(listViewKunder, javafx.scene.layout.Priority.ALWAYS);
        javafx.scene.layout.GridPane.setHgrow(KundeBox, javafx.scene.layout.Priority.ALWAYS);
        javafx.scene.layout.GridPane.setVgrow(KundeBox, javafx.scene.layout.Priority.ALWAYS);

        // Configure BestillingBox (Right)
        bestillingBox.setSpacing(6);
        bestillingBox.setPadding(new Insets(5));
        bestillingBox.getChildren().clear();
        bestillingBox.getChildren().addAll(
                labelplads,
                listViewPladser,
                labelDato, datePicker,
                buttonOpretBestilling
        );
        bestillingBox.setPrefWidth(200);
        javafx.scene.layout.VBox.setVgrow(listViewPladser, javafx.scene.layout.Priority.ALWAYS);
        javafx.scene.layout.GridPane.setHgrow(bestillingBox, javafx.scene.layout.Priority.ALWAYS);
        javafx.scene.layout.GridPane.setVgrow(bestillingBox, javafx.scene.layout.Priority.ALWAYS);

        // configure selection modes: single for show/customer, multiple for seats
        listViewForstillinger.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewKunder.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewPladser.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Populate ListViews
        listViewForstillinger.getItems().setAll(Storage.getForestillinger());
        listViewKunder.getItems().setAll(Storage.getKunder());
        listViewPladser.getItems().setAll(Storage.getPladser());

        //
        pane.add(forstillingBox, 0, 0);
        pane.add(KundeBox, 1, 0);
        pane.add(bestillingBox, 2, 0);


        // Button Actions
        buttonAddForstilling.setOnAction(e -> {
            String ForstillingsNavn = textFieldForstillingNavn.getText().trim();
            LocalDate StartDato = textFieldForstillingStartDato.getText().isBlank() ? LocalDate.now() : LocalDate.parse(textFieldForstillingStartDato.getText().trim());
            LocalDate SlutDato = textFieldForstillingSlutDato.getText().isBlank() ? LocalDate.now() : LocalDate.parse(textFieldForstillingSlutDato.getText().trim());
            Controller.createForstilling(ForstillingsNavn, StartDato, SlutDato);
            listViewForstillinger.getItems().setAll(Storage.getForestillinger());
            textFieldForstillingNavn.clear();
            textFieldForstillingStartDato.clear();
            textFieldForstillingSlutDato.clear();
        });
        buttonAddKunde.setOnAction(e -> {
            String kundeNavn = textFieldKundeNavn.getText().trim();
            String kundeMobil = textFieldKundeMobilNummer.getText().trim();
            Controller.createKunde(kundeNavn, kundeMobil);
            listViewKunder.getItems().setAll(Storage.getKunder());
            textFieldKundeNavn.clear();
            textFieldKundeMobilNummer.clear();
        });
        buttonOpretBestilling.setOnAction(e -> {
            Forstilling selectedForstilling = listViewForstillinger.getSelectionModel().getSelectedItem();
            Kunde selectedKunde = listViewKunder.getSelectionModel().getSelectedItem();
            ObservableList<Plads> selectedPladser = listViewPladser.getSelectionModel().getSelectedItems();
            LocalDate chosenDate = datePicker.getValue();

            if (selectedForstilling == null || selectedKunde == null || selectedPladser == null || selectedPladser.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Manglende data", "vælg en forstilling, kunde, dato og mindst en plads.");
                return;
            }

            Bestilling result = Controller.opretBestillingMedPladser(
                    selectedForstilling,
                    selectedKunde,
                    chosenDate,
                    new ArrayList<>(selectedPladser)
            );

            if (result == null) {
                showAlert(Alert.AlertType.ERROR, "Fejl", "Kunnne ikke oprette din bestilling");
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Succes", "Dine Bestilling er blevet oprettet " + (chosenDate != null ? " for tid " + chosenDate : "") + ".");
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String manglendeData, String content) {
        Alert a = new Alert(alertType);
        a.setTitle(manglendeData);
        a.setHeaderText(null);
        a.setContentText(content);
        a.showAndWait();
    }
}
