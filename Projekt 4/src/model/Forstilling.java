package model;

import Storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class Forstilling {
    private String navn;
    private LocalDate startDato;
    private LocalDate slutDato;
    private ArrayList<Bestilling> bestillinger = new ArrayList<>();

    public Forstilling(String navn, LocalDate startDato, LocalDate slutDato) {
        this.navn = navn;
        this.startDato = startDato;
        this.slutDato = slutDato;
    }

    public String getNavn() {
        return navn;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public ArrayList<Bestilling> createBestillinger(LocalDate date) {
        Bestilling bestilling = new Bestilling(date, this, new Kunde("testotron", "69420"));
        bestillinger.add(bestilling);
        return bestillinger;
    }

    public boolean erPladsenLedig(int række, int nummer, LocalDate dato) {
        for (Bestilling bestilling : bestillinger) {
            if (bestilling.getDate().equals(dato)) {
                for (Plads plads : bestilling.getPladser()) {
                    if (plads.getRække() == række && plads.getNummer() == nummer) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void addBestilling(Bestilling bestilling) {
        if (!bestillinger.contains(bestilling)) {
            bestillinger.add(bestilling);
        }
    }

    public int antalBestiltePladserPåDag(LocalDate date) {
        int antalBestillinger = 0;
        for (Bestilling bestilling : bestillinger) {
            if (bestilling.getDate().isEqual(date)) {
                antalBestillinger++;
            }
        }
        return antalBestillinger;
    }

    public LocalDate succesDato(){
        LocalDate bedsteDag = null;
        int bestCount = -1;
        for (Bestilling candidate : bestillinger) {
            LocalDate date = candidate.getDate();
            if (date == null){ continue;}

            int count = 0;
            for (Bestilling bestilling : bestillinger) {
                LocalDate bestillingDate = bestilling.getDate();
                if(bestillingDate != null && bestillingDate.isEqual(date)){
                    count += (bestilling.getPladser() == null ? 0 : bestilling.getPladser().size());
                }
            }
            if (count > bestCount){
                bestCount = count;
                bedsteDag = date;
            }
        }
        return bedsteDag;
    }

    @Override
    public String toString() {
        return navn + " (" + startDato + " - " + slutDato + ") ";
    }

}
