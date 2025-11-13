package Controller;

import Storage.Storage;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {


    public static Forstilling createForstilling(String navn, LocalDate startDato, LocalDate slutDato) {

        Forstilling forstilling = new Forstilling(navn, startDato, slutDato);
        Storage.addForestilling(forstilling);
        return forstilling;
    }

    public static Kunde createKunde(String navn, String mobilnummer) {

        Kunde kunde = new Kunde(navn, mobilnummer);
        Storage.addKunde(kunde);
        return kunde;
    }

    public static Plads createPlads(int række, int nummer, int pris, PladsType pladsType) {

        Plads plads = new Plads(række, nummer, pris, pladsType);
        Storage.addPlads(plads);
        return plads;
    }

    public static Bestilling opretBestillingMedPladser(
            Forstilling forestilling, Kunde kunde,
            LocalDate dato, ArrayList<Plads> pladser) {

        if (forestilling == null || kunde == null || dato == null || pladser == null || pladser.size() == 0) {
            return null;
        }

        if (!(forestilling.getStartDato().isBefore(dato) || forestilling.getStartDato().isEqual(dato)) &&
                (forestilling.getSlutDato().isAfter(dato) || forestilling.getSlutDato().isEqual(dato))) {
            return null;
        }
        for (Plads plads : pladser) {
            if (!forestilling.erPladsenLedig(plads.getRække(), plads.getNummer(), dato)) {
                return null;
            }
            Bestilling bestilling = new Bestilling(dato, forestilling, kunde);
            for (Plads plads1 : pladser) {
                bestilling.addPlads(plads1);
            }
            forestilling.addBestilling(bestilling);
            kunde.addBestilling(bestilling);

            return bestilling;
        }
        return null;
    }
}

