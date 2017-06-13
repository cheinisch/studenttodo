package de.christian_heinisch.studenttodo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogAddTodoFragment extends DialogFragment {

    View rootview;


    public DialogAddTodoFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final long id = this.getArguments().getLong("id");

        // Inflate the layout for this fragment

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.dialog_add_todo, null);


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
                        //((MainActivity)getContext()).stuffListFragment();

                    }
                })
                .create();
    }

}
