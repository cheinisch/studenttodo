package de.christian_heinisch.studenttodo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.christian_heinisch.studenttodo.database.ToDoDataSource;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogAddTodoFragment extends DialogFragment {

    View rootview;
    private ToDoDataSource dataSource_todo;
    String todoname;
    long date;
    EditText tvName;
    EditText tvDate;


    public DialogAddTodoFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final long id = this.getArguments().getLong("id");

        // Inflate the layout for this fragment

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.dialog_add_todo, null);

        dataSource_todo = new ToDoDataSource(getContext());

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

                        todoname = tvName.getText().toString();
                        try {
                            date = convertTime(tvDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        dataSource_todo.open();
                        dataSource_todo.createToDo(todoname,"false",date);
                        dataSource_todo.close();
                        ((StartActivity)getContext()).todo();

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
