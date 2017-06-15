package de.christian_heinisch.studenttodo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.christian_heinisch.studenttodo.database.ToDoDataSource;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogEditTodoFragment extends DialogFragment {

    View rootview;
    private ToDoDataSource dataSource;

    String text;
    long date;
    String checked;
    long id;

    EditText tvName;
    EditText tvDate;


    public DialogEditTodoFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        id = this.getArguments().getLong("id");

        dataSource = new ToDoDataSource(getContext());

        // Inflate the layout for this fragment
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.dialog_edit_todo, null);

        tvName = (EditText) rootview.findViewById(R.id.editTextName);
        tvDate = (EditText) rootview.findViewById(R.id.editTextDate);

        getToDo();



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

                        text = tvName.getText().toString();
                        try {
                            date = convertTime(tvDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        updateToDo(id,text,checked,date);
                        ((StartActivity)getContext()).todo();

                    }
                })
                .create();

    }

    public void getToDo(){

        dataSource.open();
        date = dataSource.getDate(id);
        text = dataSource.getText(id);
        checked = dataSource.getChecked(id);
        dataSource.close();
        String newdate = getDate(date);
        tvDate.setText(newdate);
        tvName.setText(text);

    }

    public void updateToDo(long id, String todotext, String checked, long date){
        dataSource.open();
        dataSource.updateToDo(id, todotext, checked, date);
        dataSource.close();

    }

    private String getDate(long time) {



        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTimeInMillis(time);
        String datenew = DateFormat.format("dd.MM.yyyy", cal).toString();
        return datenew;
    }

    public long convertTime(String newdate) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = (Date)formatter.parse(newdate);

        long returnDate = date.getTime();

        getDate(returnDate);

        return returnDate;
    }

}
