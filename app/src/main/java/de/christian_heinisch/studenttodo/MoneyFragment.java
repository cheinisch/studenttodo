package de.christian_heinisch.studenttodo;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.christian_heinisch.studenttodo.adapters.DataObject;
import de.christian_heinisch.studenttodo.adapters.RVMoneyAdapter;
import de.christian_heinisch.studenttodo.database.Money;
import de.christian_heinisch.studenttodo.database.MoneyOverview;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyFragment extends Fragment {

    View rootview;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;


    public MoneyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_money, container, false);

        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RVMoneyAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);


        return rootview;
    }

    private ArrayList<MoneyOverview> getDataSet() {
        ArrayList results = new ArrayList<MoneyOverview>();
        for (int index = 0; index < 20; index++) {
            MoneyOverview obj = new MoneyOverview(1, 1.1,1.2,1.9, "2017-06-30");
            results.add(index, obj);
        }
        return results;
    }

}
