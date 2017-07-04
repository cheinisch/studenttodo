package de.christian_heinisch.studenttodo;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.christian_heinisch.studenttodo.database.MoneyDataSource;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogAddMoneyDetailFragment extends DialogFragment {

    View rootview;
    private MoneyDataSource dataSource;
    double geld;
    String date;
    EditText tvName;
    EditText tvDate;
    RadioGroup radiogroup;
    RadioButton radioButton;
    DatePickerDialog datePickerDialog;


    public DialogAddMoneyDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.dialog_add_money, null);

        dataSource = new MoneyDataSource(getContext());

        tvDate = (EditText) rootview.findViewById(R.id.editTextDate);
        // perform click event on edit text
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Verstecke die tastatur

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(rootview.getWindowToken(), 0);

                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                tvDate.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        return new AlertDialog.Builder(getActivity())
                .setView(rootview)
                .setTitle(R.string.todo_add_title)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing (will close dialog)
                    }
                })
                .setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do something
                        tvName = (EditText) rootview.findViewById(R.id.editTextName);
                        radiogroup = (RadioGroup) rootview.findViewById(R.id.rgMoneyAdd);

                        int radiobutton = radiogroup.getCheckedRadioButtonId();
                        int geldeingang = 0;
                        radioButton = (RadioButton) rootview.findViewById(radiobutton);

                        if(radioButton.getText().toString().equalsIgnoreCase("eingang"))
                        {
                            geldeingang = 1;
                        }

                        geld = Double.parseDouble(tvName.getText().toString());
                        date = getDateforDB(tvDate.getText().toString());

                        dataSource.open();
                        dataSource.createMoney(geld,geldeingang,date);
                        dataSource.close();
                            ((StartActivity) getContext()).money();
                    }
                })
                .create();
    }

    public long convertTime(String newdate) throws ParseException {



        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = (Date)formatter.parse(newdate);

        long returnDate = date.getTime();

        System.out.println("NEUES DATUM: " + returnDate);

        return returnDate;
    }

    private String getDateforDB(String oldDate){

        String newDate;

        String[] tempString = oldDate.split("-");

        int tempTag;
        int tempMonat;

        tempMonat = Integer.parseInt(tempString[1]);

        if(tempMonat < 10){
            tempString[1] = "0" + tempString[1];
        }

        tempTag = Integer.parseInt(tempString[2]);


        if(tempTag < 10){
            tempString[2] = "0" + tempString[2];
        }

        newDate = tempString[0] + "-" + tempString[1] + "-" + tempString[2];

        return newDate;
    }

}
