package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bestilling {
    private LocalDate date;
    private ArrayList<Plads> pladser = new ArrayList<>();
    private Forstilling forstilling;
    private Kunde kunde;

    public Bestilling(LocalDate date, Forstilling forstilling, Kunde kunde) {
        this.date = date;
        this.forstilling = forstilling;
        this.kunde = kunde;
    }

    public LocalDate getDate() {
        return date;
    }
    public void addPlads(Plads plads) {
        if(!pladser.contains(plads)){
        pladser.add(plads);
        }
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public Forstilling getForstilling() {
        return forstilling;
    }

    public ArrayList<Plads> getPladser() {
        return pladser;
    }
    public int samletPris(){
        int pris = 0;
        for (Plads plads : pladser) {
            pris += plads.getPris();
        }

        return pris;
    }
}

