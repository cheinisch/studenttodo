package de.christian_heinisch.studenttodo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import static de.christian_heinisch.studenttodo.database.StudentToDoDbHelper.TABLE_MONEY_LIST;

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


    public Money createMoney(double euro, int typ, String date) {

        System.out.println("TYP vor verarbeitung " + typ);

        ContentValues values = new ContentValues();
        values.put(StudentToDoDbHelper.COLUMN_MONEY, euro);
        values.put(StudentToDoDbHelper.COLUMN_MONEY_DATE, date);
        values.put(StudentToDoDbHelper.COLUMN_MONEY_TYPE, typ);

        System.out.println("TYP nach verarbeitung " + values.get(StudentToDoDbHelper.COLUMN_MONEY_TYPE));

        String sql =
                "INSERT INTO money_list (money, date, type) VALUES('"+euro+"','"+date+"','"+typ+"')" ;
        database.execSQL(sql);
        Money money = null;
        return money;
    }

    public void deleteMoney(long id) {
        database.delete(TABLE_MONEY_LIST,
                StudentToDoDbHelper.COLUMN_ID + "=" + id,
                null);

        Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + id);
    }


    private Money cursorToMoney(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(StudentToDoDbHelper.COLUMN_ID);
        int idMoney = cursor.getColumnIndex(StudentToDoDbHelper.COLUMN_MONEY);
        int idDate = cursor.getColumnIndex(StudentToDoDbHelper.COLUMN_MONEY_DATE);
        int idTYPE = cursor.getColumnIndex(StudentToDoDbHelper.COLUMN_MONEY_TYPE);


        double euro = cursor.getDouble(idMoney);
        String date = cursor.getString(idDate);
        int typ = cursor.getInt(idTYPE);


        String[] parts = date.split("-");
        int jahr = Integer.parseInt(parts[0]);
        int monat = Integer.parseInt(parts[1]);
        int tag = Integer.parseInt(parts[2]);

        long id = cursor.getLong(idIndex);

        Money money = new Money(id, euro, euro, euro, jahr ,monat, tag, typ);

        return money;
    }


    public ArrayList<Money> getMoneyforMonth(String startdate, String enddate) {
        ArrayList<Money> listitems = new ArrayList<Money>();


        Cursor cursor;
        String sqlQry;
            cursor = database.rawQuery("SELECT * FROM money_list WHERE date BETWEEN date('"+startdate+"') AND date('"+enddate+"')", null);


        cursor.moveToFirst();
        Money money;

        while (!cursor.isAfterLast()) {
            money = cursorToMoney(cursor);
            listitems.add(new Money(money.getId(), money.getEuro(), money.getEinnahme(), money.getAusgabe(), money.getJahr(), money.getMonat(), money.getTag(), money.getTyp()));

            cursor.moveToNext();
        }

        cursor.close();

        return listitems;
    }

}
