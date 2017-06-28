package de.christian_heinisch.studenttodo;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.christian_heinisch.studenttodo.adapters.OverviewToDoListRecyclerViewAdapter;
import de.christian_heinisch.studenttodo.database.ToDo;
import de.christian_heinisch.studenttodo.database.ToDoDataSource;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {

    View rootview;

    private ToDoDataSource dataSource_todo;
    private RecyclerView mRecyclerView;
    private RecyclerView new_mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager new_mLayoutManager;


    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_start, container, false);

        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.rvOverviewToDo);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OverviewToDoListRecyclerViewAdapter(getContext(), getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

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

}
