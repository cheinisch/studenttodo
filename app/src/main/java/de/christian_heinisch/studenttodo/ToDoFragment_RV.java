package de.christian_heinisch.studenttodo;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
    private RecyclerView new_mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager new_mLayoutManager;
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
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getContext(), getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        new_mRecyclerView = (RecyclerView) rootview.findViewById(R.id.rv_checked);
        new_mRecyclerView.setHasFixedSize(true);
        new_mLayoutManager = new LinearLayoutManager(getContext());
        new_mRecyclerView.setLayoutManager(new_mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getContext(), getDataSet_checked());
        new_mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration newitemDecoration =
                new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        new_mRecyclerView.addItemDecoration(newitemDecoration);

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


}
