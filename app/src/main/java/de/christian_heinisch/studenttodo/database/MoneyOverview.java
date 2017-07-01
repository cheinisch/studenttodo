package de.christian_heinisch.studenttodo.database;

/**
 * Created by chris on 29.06.2017.
 */

public class MoneyOverview {

    private double ausgabeEuro;
    private double gesamtEuro;
    private double einnahmeEuro;
    private long id;
    private String date;



    public MoneyOverview(long id, double gesamtEuro, double ausgabeEuro, double einnahmeEuro, String date){

        this.id = id;
        this.gesamtEuro = gesamtEuro;
        this.ausgabeEuro = ausgabeEuro;
        this.einnahmeEuro = einnahmeEuro;
        this.date = date;

    }

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getaEuro() {
        return ausgabeEuro;
    }

    public void setaEuro(double aeuro) {
        this.ausgabeEuro = aeuro;
    }

    public double geteEuro() {
        return einnahmeEuro;
    }

    public void seteEuro(double eeuro) {
        this.einnahmeEuro = eeuro;
    }

    public double getgEuro() {
        return gesamtEuro;
    }

    public void setgEuro(double geuro) {
        this.gesamtEuro = geuro;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
