package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Kunde {
    private String navn;
    private String mobilnummer;
    private ArrayList<Bestilling> bestillinger = new ArrayList<>();

    public Kunde(String navn, String mobilnummer) {
        this.navn = navn;
        this.mobilnummer = mobilnummer;
    }

    public String getNavn() {
        return navn;
    }

    public String getMobilnummer() {
        return mobilnummer;
    }

    public void addBestilling(Bestilling bestilling) {
        if(!bestillinger.contains(bestilling)){
            bestillinger.add(bestilling);
            bestilling.setKunde(this);
        }
    }
    public ArrayList<Plads> bestiltePladserTilForestillingPådag(Forstilling forstilling, LocalDate date){
        ArrayList<Plads> pladser = new ArrayList<>();
        for (Bestilling bestilling : bestillinger) {
            if(bestilling.getDate().equals(date) && bestilling.getForstilling() == forstilling){
                pladser.addAll(bestilling.getPladser());
            }
        }
        return pladser;
    }

    @Override
    public String toString() {
        return navn + " (" + mobilnummer + ") ";
    }
}
