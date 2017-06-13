package de.christian_heinisch.studenttodo;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoFragment extends Fragment {


    View rootview;

    public ToDoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_to_do, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootview.findViewById(R.id.fbToDoAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Test");
                ((StartActivity)getContext()).DialogAddToDO(1);
            }
        });

        return rootview;
    }

}
