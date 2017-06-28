package de.christian_heinisch.studenttodo.database;

/**
 * Created by chris on 14.06.2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ToDoDataSource {

    private static final String LOG_TAG = ToDoDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private StudentToDoDbHelper dbHelper;

    private String[] columns = {
            StudentToDoDbHelper.COLUMN_ID,
            StudentToDoDbHelper.COLUMN_TODO,
            StudentToDoDbHelper.COLUMN_TODO_DATE,
            StudentToDoDbHelper.COLUMN_TODO_CHECKED
    };


    public ToDoDataSource(Context context) {
        //Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new StudentToDoDbHelper(context);
    }

    public void open() {
        //Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        //Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        //Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    public ToDo createToDo(String todoname, String checked, long date) {
        ContentValues values = new ContentValues();
        values.put(StudentToDoDbHelper.COLUMN_TODO, todoname);
        values.put(StudentToDoDbHelper.COLUMN_TODO_DATE, date);
        values.put(StudentToDoDbHelper.COLUMN_TODO_CHECKED, checked);

        long insertId = database.insert(StudentToDoDbHelper.TABLE_TODO_LIST, null, values);

        Cursor cursor = database.query(StudentToDoDbHelper.TABLE_TODO_LIST,
                columns, StudentToDoDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        ToDo todo = cursorToToDo(cursor);
        cursor.close();

        return todo;
    }

    public void deleteToDo(long id) {
        database.delete(StudentToDoDbHelper.TABLE_TODO_LIST,
                StudentToDoDbHelper.COLUMN_ID + "=" + id,
                null);

        Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + id);
    }


    public ToDo updateToDo(long id, String todoname, String checked, long date) {


        ContentValues values = new ContentValues();
        values.put(StudentToDoDbHelper.COLUMN_TODO, todoname);
        values.put(StudentToDoDbHelper.COLUMN_TODO_DATE, date);
        values.put(StudentToDoDbHelper.COLUMN_TODO_CHECKED, checked);

        database.update(StudentToDoDbHelper.TABLE_TODO_LIST,
                values,
                StudentToDoDbHelper.COLUMN_ID + "=" + id,
                null);

        Cursor cursor = database.query(StudentToDoDbHelper.TABLE_TODO_LIST,
                columns, StudentToDoDbHelper.COLUMN_ID + "=" + id,
                null, null, null, null);

        cursor.moveToFirst();
        ToDo todo = cursorToToDo(cursor);
        cursor.close();

        return todo;
    }

    public long getDate(long id){

        long date = 0;

        Cursor cursor = database.query(StudentToDoDbHelper.TABLE_TODO_LIST,
                columns, StudentToDoDbHelper.COLUMN_ID + "=" + id ,null, null, null, null);

        ToDo todo;

        if (cursor!= null && cursor.moveToFirst());
        do {
            todo = cursorToToDo(cursor);
            date = todo.isDate();
        } while (cursor.moveToNext());

        return date;
    }

    public String getChecked(long id){

        String checked;

        Cursor cursor = database.query(StudentToDoDbHelper.TABLE_TODO_LIST,
                columns, StudentToDoDbHelper.COLUMN_ID + "=" + id ,null, null, null, null);

        ToDo todo;

        if (cursor!= null && cursor.moveToFirst());
        do {
            todo = cursorToToDo(cursor);
            checked = todo.isChecked();
        } while (cursor.moveToNext());

        return checked;
    }

    public void updateDate(long id, long date){

        Cursor cursor = database.query(StudentToDoDbHelper.TABLE_TODO_LIST,
                columns, StudentToDoDbHelper.COLUMN_ID + "=" + id ,null, null, null, null);

        ToDo todo;

        if (cursor!= null && cursor.moveToFirst());
        do {
            todo = cursorToToDo(cursor);

            updateToDo(id, todo.getToDo(), todo.isChecked(), date);

        } while (cursor.moveToNext());


    }

    public String getText(long id){

        String text = "";

        Cursor cursor = database.query(StudentToDoDbHelper.TABLE_TODO_LIST,
                columns, StudentToDoDbHelper.COLUMN_ID + "=" + id ,null, null, null, null);

        ToDo todo;

        if (cursor!= null && cursor.moveToFirst());
        do {
            todo = cursorToToDo(cursor);
            text = todo.getToDo();

        } while (cursor.moveToNext());

        return text;
    }

    public void updateText(long id, String text){

        Cursor cursor = database.query(StudentToDoDbHelper.TABLE_TODO_LIST,
                columns, StudentToDoDbHelper.COLUMN_ID + "=" + id ,null, null, null, null);

        ToDo todo;

        if (cursor!= null && cursor.moveToFirst());
        do {
            todo = cursorToToDo(cursor);

            updateToDo(id, text, todo.isChecked(), todo.isDate());

        } while (cursor.moveToNext());


    }

    private String CheckDate(long time) {



        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTimeInMillis(time);
        String datenew = DateFormat.format("dd.MM.yyyy", cal).toString();
        return datenew;
    }


    private ToDo cursorToToDo(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(StudentToDoDbHelper.COLUMN_ID);
        int idToDo = cursor.getColumnIndex(StudentToDoDbHelper.COLUMN_TODO);
        int idToDodate = cursor.getColumnIndex(StudentToDoDbHelper.COLUMN_TODO_DATE);
        int idToDoChecked = cursor.getColumnIndex(StudentToDoDbHelper.COLUMN_TODO_CHECKED);

        String todoname = cursor.getString(idToDo);
        long date = cursor.getLong(idToDodate);
        String checked = cursor.getString(idToDoChecked);
        long id = cursor.getLong(idIndex);

        ToDo todo = new ToDo(todoname, checked, date, id);

        return todo;
    }

    public ArrayList<ToDo> getToDoForList(String checked){
        ArrayList<ToDo> listitems = new ArrayList<ToDo>();

        Cursor cursor = database.query(StudentToDoDbHelper.TABLE_TODO_LIST,
                columns, "checked = ?",new String[]{checked}, null, null, null);

        String sqlQry = SQLiteQueryBuilder.buildQueryString(false, StudentToDoDbHelper.TABLE_TODO_LIST,
                columns, "checked = " + checked,null, null, null, null);

        Log.i(LOG_TAG, sqlQry);

        cursor.moveToFirst();
        ToDo todo;

        while (!cursor.isAfterLast()) {
            todo = cursorToToDo(cursor);
            listitems.add(new ToDo(todo.getToDo(), todo.isChecked(), todo.isDate(), todo.getId()));
            cursor.moveToNext();
        }

        cursor.close();

        return listitems;
    }
/*
    public List<ToDo> getAllToDoChecked(long cityid) {
        List<ToDo> todoList = new ArrayList<>();

        Cursor cursor = database.query(StudentToDoDbHelper.TABLE_TODO_LIST,
                columns, "cityid = " + cityid + " and buy = ?",new String[]{"false"}, null, null, null);

        cursor.moveToFirst();
        ToDo todo;

        while (!cursor.isAfterLast()) {
            todo = cursorToToDo(cursor);
            todoList.add(todo);
            cursor.moveToNext();
        }

        cursor.close();

        return todoList;
    }
    public List<ToDo> getAllToDoBuy(long cityid) {
        List<ToDo> todoList = new ArrayList<>();

        Cursor cursor = database.query(StudentToDoDbHelper.TABLE_TODO_LIST,
                columns, "cityid = " + cityid + " and buy = ?",new String[]{"true"}, null, null, null);

        cursor.moveToFirst();
        ToDo todo;

        while (!cursor.isAfterLast()) {
            todo = cursorToToDo(cursor);
            todoList.add(todo);
            Log.d(LOG_TAG, "ID: " + todo.getId() + ", Inhalt: " + todo.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return todoList;
    }*/

}
