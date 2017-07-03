package de.christian_heinisch.studenttodo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.christian_heinisch.studenttodo.database.MoneyDataSource;
import de.christian_heinisch.studenttodo.database.ToDoDataSource;

import static de.christian_heinisch.studenttodo.R.id.radioButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogAddMoneyFragment extends DialogFragment {

    View rootview;
    private MoneyDataSource dataSource;
    double geld;
    String date;
    EditText tvName;
    EditText tvDate;
    RadioGroup radiogroup;
    RadioButton radioButton;


    public DialogAddMoneyFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final long id = this.getArguments().getLong("id");

        // Inflate the layout for this fragment

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.dialog_add_money, null);

        dataSource = new MoneyDataSource(getContext());

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
                        tvDate = (EditText) rootview.findViewById(R.id.editTextDate);
                        radiogroup = (RadioGroup) rootview.findViewById(R.id.rgMoneyAdd);

                        int radiobutton = radiogroup.getCheckedRadioButtonId();
                        int geldeingang = 0;
                        radioButton = (RadioButton) rootview.findViewById(radiobutton);

                        if(radioButton.getText().toString().equalsIgnoreCase("eingang"))
                        {
                            geldeingang = 1;
                        }

                        geld = Double.parseDouble(tvName.getText().toString());
                        date = tvDate.getText().toString();

                        dataSource.open();
                        dataSource.createMoney(geld,geldeingang,date);
                        dataSource.close();
                        ((StartActivity)getContext()).money();

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

}
