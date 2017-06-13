package de.christian_heinisch.studenttodo.database;

/**
 * Created by chris on 13.06.2017.
 */

public class ToDo {

    private String todo;
    private String checked;
    private int date;
    private long id;

    public ToDo(String todo, String checked, int date, long id){

        this.todo= todo;
        this.checked = checked;
        this.date = date;
        this.id = id;

    }

    public String getStuff() {
        return todo;
    }

    public void setStuff(String todo) {
        this.todo = todo;
    }

    public String isChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public int isDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
