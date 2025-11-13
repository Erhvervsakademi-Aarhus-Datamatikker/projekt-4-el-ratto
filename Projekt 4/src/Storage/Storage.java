package Storage;

import model.Forstilling;
import model.Kunde;
import model.Plads;

import java.util.ArrayList;

public class Storage {
    private static ArrayList<Forstilling> forestillinger = new ArrayList<>();
    private static ArrayList<Kunde> kunder = new ArrayList<>();
    private static ArrayList<Plads> pladser = new ArrayList<>();

    //functions to add and get items from the lists
    //Forstillinger
    public static void addForestilling(Forstilling forstilling) {
        if (!forestillinger.contains(forstilling)) {
            forestillinger.add(forstilling);
        }
    }

    public static ArrayList<Forstilling> getForestillinger() {
        return forestillinger;
    }

    //Kunder
    public static void addKunde(Kunde kunde) {
        if (!kunder.contains(kunde)) {
            kunder.add(kunde);
        }
    }

    public static ArrayList<Kunde> getKunder() {
        return kunder;
    }

    //Pladser
    public static void addPlads(Plads plads) {
        if (!pladser.contains(plads)) {
            pladser.add(plads);
        }
    }

    public static ArrayList<Plads> getPladser() {
        return pladser;
    }
}
