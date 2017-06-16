package de.christian_heinisch.studenttodo;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.christian_heinisch.studenttodo.adapters.MyRecyclerViewAdapter;
import de.christian_heinisch.studenttodo.database.ToDo;
import de.christian_heinisch.studenttodo.database.ToDoAdapter;
import de.christian_heinisch.studenttodo.database.ToDoDataSource;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoFragment_RV extends Fragment {


    View rootview;

    private ToDoDataSource dataSource_todo;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView_checked;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapter_checked;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";


    public ToDoFragment_RV() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_to_do_rv, container, false);

        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.rv);
        mRecyclerView_checked = (RecyclerView) rootview.findViewById(R.id.rv_checked);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView_checked.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        //mLayoutManager_checked = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView_checked.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mAdapter_checked = new MyRecyclerViewAdapter(getDataSet_checked());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView_checked.setAdapter(mAdapter_checked);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView_checked.addItemDecoration(itemDecoration);


  //      setHasOptionsMenu(true);

        FloatingActionButton fab = (FloatingActionButton) rootview.findViewById(R.id.fbToDoAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((StartActivity)getContext()).DialogAddToDO();
            }
        });


        return rootview;
    }

    private ArrayList<ToDo> getDataSet() {
        dataSource_todo = new ToDoDataSource(getContext());
        dataSource_todo.open();

        ArrayList<ToDo> arrayOfToDo = null;
        arrayOfToDo = dataSource_todo.getToDoForList("false");
        dataSource_todo.close();

        return arrayOfToDo;
    }

    private ArrayList<ToDo> getDataSet_checked() {
        dataSource_todo = new ToDoDataSource(getContext());
        dataSource_todo.open();

        ArrayList<ToDo> arrayOfToDo = null;
        arrayOfToDo = dataSource_todo.getToDoForList("true");
        dataSource_todo.close();

        return arrayOfToDo;
    }


    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd.MM.yyyy", cal).toString();
        return date;
    }


}
