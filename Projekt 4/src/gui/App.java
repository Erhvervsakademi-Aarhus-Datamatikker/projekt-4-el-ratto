package gui;

import Controller.Controller;
import Storage.Storage;
import javafx.application.Application;
import model.Forstilling;
import model.PladsType;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.initStorage();
        Application.launch(GuiAdmin.class);

    }

    final int YELLOWPRICE = 500;
    final int GREENPRICE = 450;
    final int BLUEPRICE = 400;

    private void initStorage() {
        Controller controller = new Controller();
        controller.createForstilling("Lykke Per", LocalDate.of(2025, 10, 21), LocalDate.of(2026, 1, 10));
        controller.createForstilling("Evita", LocalDate.of(2025, 11, 5), LocalDate.of(2026, 2, 15));
        controller.createForstilling("Hamlet", LocalDate.of(2025, 12, 1), LocalDate.of(2026, 3, 20));

        controller.createKunde("Peter Bastian", "12345678");
        controller.createKunde("Anna Hansen", "87654321");
        controller.createKunde("Noah Rex Frederiksen", "25722180");

        // creating seats
        for (int row = 1; row <= 20; row++) {
            for (int seat = 1; seat <= 15; seat++) {
                if (row <= 5 && seat <= 2 || seat >= 19) {
                    controller.createPlads(row, seat, GREENPRICE, PladsType.STANDARD);
                } else if (row <= 5 && 2 < seat && seat < 19) {
                    controller.createPlads(row, seat, YELLOWPRICE, PladsType.STANDARD);
                } else if (row > 5 && row <= 10 && (seat <= 2 || seat >= 19)) {
                    controller.createPlads(row, seat, BLUEPRICE, PladsType.STANDARD);
                } else if (row > 5 && row <= 10 && 2 < seat && seat < 19) {
                    if (row == 10 && (seat == 7 || seat == 8 || seat == 9 || seat == 10 || seat == 11 || seat == 12)) {
                        controller.createPlads(row, seat, GREENPRICE, PladsType.KØRESTOL);
                    } else {
                        controller.createPlads(row, seat, BLUEPRICE, PladsType.STANDARD);
                    }
                } else {
                    if (seat == 7 || seat == 8 || seat == 9 || seat == 10 || seat == 11 || seat == 12) {
                        controller.createPlads(row, seat, BLUEPRICE, PladsType.EKSTRABEN);
                    } else {
                        controller.createPlads(row, seat, BLUEPRICE, PladsType.STANDARD);
                    }

                }
            }
        }

    }
}
