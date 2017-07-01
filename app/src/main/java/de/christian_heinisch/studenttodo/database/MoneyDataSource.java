package de.christian_heinisch.studenttodo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by chris on 01.07.2017.
 */

public class MoneyDataSource {

    private static final String LOG_TAG = MoneyDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private StudentToDoDbHelper dbHelper;

    private String[] columns = {
            StudentToDoDbHelper.COLUMN_ID,
            StudentToDoDbHelper.COLUMN_MONEY,
            StudentToDoDbHelper.COLUMN_MONEY_DATE,
            StudentToDoDbHelper.COLUMN_MONEY_TYPE
    };

    public MoneyDataSource(Context context) {
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

    private Money cursorToMoney(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(StudentToDoDbHelper.COLUMN_ID);
        int idMoney = cursor.getColumnIndex(StudentToDoDbHelper.COLUMN_MONEY);
        int idDate = cursor.getColumnIndex(StudentToDoDbHelper.COLUMN_MONEY_DATE);
        int idTYPE = cursor.getColumnIndex(StudentToDoDbHelper.COLUMN_MONEY_TYPE);

        double euro = cursor.getDouble(idMoney);
        String date = cursor.getString(idDate);

        String[] parts = date.split("-");
        int jahr = Integer.parseInt(parts[0]);
        int monat = Integer.parseInt(parts[1]);
        int tag = Integer.parseInt(parts[2]);

        long id = cursor.getLong(idIndex);

        Money money = new Money(id, euro, jahr ,monat, tag);

        return money;
    }


    public ArrayList<Money> getMoneyforMonth(String date) {
        ArrayList<Money> listitems = new ArrayList<Money>();

        Cursor cursor;
        String sqlQry;
            cursor = database.query(StudentToDoDbHelper.TABLE_TODO_LIST,
                    columns, null, null, null, null, null);

            sqlQry = SQLiteQueryBuilder.buildQueryString(false, StudentToDoDbHelper.TABLE_TODO_LIST,
                    columns, null, null, null, null, null);


        Log.i(LOG_TAG, sqlQry);

        cursor.moveToFirst();
        Money money;

        while (!cursor.isAfterLast()) {
            money = cursorToMoney(cursor);
            listitems.add(new Money(money.getId(), money.getEuro(), money.getJahr(), money.getMonat(), money.getTag()));
            cursor.moveToNext();
        }

        cursor.close();

        return listitems;
    }

}
