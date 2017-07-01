package de.christian_heinisch.studenttodo.database;

/**
 * Created by chris on 29.06.2017.
 */

public class Money {

    private double Euro;
    private double Einnahme;
    private double Ausgabe;
    private long id;
    private String date;
    private int monat;
    private int jahr;
    private int tag;
    private int typ;


    public Money(long id, double Euro, double einnahme, double ausgabe, int jahr, int monat, int tag, int typ){

        this.id = id;
        this.Euro = Euro;
        this.Einnahme = einnahme;
        this.Ausgabe = ausgabe;
        this.monat = monat;
        this.tag = tag;
        this.jahr = jahr;
        this.typ = typ;

    }

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getEuro() {
        return Euro;
    }

    public void setEuro(double euro) {
        this.Euro = euro;
    }

    public double getEinnahme() {
        return Einnahme;
    }

    public void setEinnahme(double einnahme) {
        Einnahme = einnahme;
    }

    public void setAusgabe(double ausgabe) {
        Ausgabe = ausgabe;
    }

    public double getAusgabe() {
        return Ausgabe;
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    public int getMonat(){
        return  monat;
    }

    public void setMonat(int monat) {
        this.monat = monat;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }
}
