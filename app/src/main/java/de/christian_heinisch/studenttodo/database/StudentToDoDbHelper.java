package de.christian_heinisch.studenttodo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by chris on 13.06.2017.
 */

public class StudentToDoDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = StudentToDoDbHelper.class.getSimpleName();


    public static final String DB_NAME = "studenttodo.db";
    public static final int DB_VERSION = 2;

    /*Tabelle To Do Liste */
    public static final String TABLE_TODO_LIST = "todo_list";
    /*Spalten To Do Liste*/
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TODO = "todo";
    public static final String COLUMN_TODO_DATE = "date";
    public static final String COLUMN_TODO_CHECKED = "checked";

    /*Tabelle Geld*/
    public static final String TABLE_MONEY_LIST = "money_list";
    /*Spalten Geld*/
    public static final String COLUMN_MONEY = "money";
    public static final String COLUMN_MONEY_YEAR = "year";
    public static final String COLUMN_MONEY_MONTH = "month";
    public static final String COLUMN_MONEY_DAY = "day";
    public static final String COLUMN_MONEY_DATE = "date";
    public static final String COLUMN_MONEY_TYPE = "type";

    public static final String SQL_CREATE_TODO =
            "CREATE TABLE " + TABLE_TODO_LIST +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TODO + " TEXT NOT NULL, " +
                    COLUMN_TODO_DATE + " INTEGER NOT NULL, " +
                    COLUMN_TODO_CHECKED + " TEXT NOT NULL);";

    public static final String SQL_CREATE_MONEY =
            "CREATE TABLE " + TABLE_MONEY_LIST +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MONEY + " REAL NOT NULL, " +
                    COLUMN_MONEY_DATE + " TEXT NOT NULL, " +
                    COLUMN_MONEY_TYPE + " TEXT NOT NULL);";


    public StudentToDoDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        //Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Die Tabelle Zeug wird mit SQL-Befehl: " + SQL_CREATE_TODO + " angelegt.");
            db.execSQL(SQL_CREATE_TODO);

            Log.d(LOG_TAG, "Die Tabelle Zeug wird mit SQL-Befehl: " + SQL_CREATE_MONEY + " angelegt.");
            db.execSQL(SQL_CREATE_MONEY);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}