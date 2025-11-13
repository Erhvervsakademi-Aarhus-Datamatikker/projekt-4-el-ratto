package model;

public class Plads {
    private int række;
    private int nummer;
    private int pris;
    private PladsType pladsType;


    public Plads(int række, int nummer, int pris, PladsType pladsType) {
        this.række = række;
        this.nummer = nummer;
        this.pris = pris;
        this.pladsType = pladsType;

    }

    public int getRække() {
        return række;
    }

    public int getNummer() {
        return nummer;
    }

    public int getPris() {
        return pris;
    }

    public PladsType getPladsType() {
        return pladsType;
    }


    @Override
    public String toString() {
        if(pladsType.equals(PladsType.EKSTRABEN) || pladsType.equals(PladsType.KØRESTOL)){
            return " " + pladsType + "Række: " + række + " Nr: " + nummer + " pris: " + pris;
        }
        return "Række: " + række + " Nr: " + nummer + " pris: " + pris;
    }
}
