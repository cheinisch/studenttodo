package de.christian_heinisch.studenttodo.database;

/**
 * Created by chris on 29.06.2017.
 */

public class Money {

    private double euro;
    private long id;
    private String date;
    private String art;



    public Money(long id, double euro, String date, String art){

        this.id = id;
        this.euro = euro;
        this.date = date;
        this.art = art;

    }

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getEuro() {
        return euro;
    }

    public void setEuro(double euro) {
        this.euro = euro;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String monat) {
        this.date = monat;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

}
