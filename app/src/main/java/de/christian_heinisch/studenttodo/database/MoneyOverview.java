package de.christian_heinisch.studenttodo.database;

/**
 * Created by chris on 29.06.2017.
 */

public class MoneyOverview {

    private double aeuro;
    private double geuro;
    private double eeuro;
    private long id;
    private String date;



    public MoneyOverview(long id, double geuro, double aeuro, double eeuro, String date){

        this.id = id;
        this.geuro = geuro;
        this.aeuro = aeuro;
        this.eeuro = eeuro;
        this.date = date;

    }

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getaEuro() {
        return aeuro;
    }

    public void setaEuro(double aeuro) {
        this.aeuro = aeuro;
    }

    public double geteEuro() {
        return eeuro;
    }

    public void seteEuro(double eeuro) {
        this.eeuro = eeuro;
    }

    public double getgEuro() {
        return geuro;
    }

    public void setgEuro(double geuro) {
        this.geuro = geuro;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
