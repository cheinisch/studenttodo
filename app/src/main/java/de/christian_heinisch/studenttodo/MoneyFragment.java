package de.christian_heinisch.studenttodo;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import de.christian_heinisch.studenttodo.adapters.RVMoneyAdapter;
import de.christian_heinisch.studenttodo.database.MoneyOverview;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyFragment extends Fragment {

    View rootview;
    RecyclerView mRecyclerView;


    public MoneyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_money, container, false);

        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.my_recycler_view);


        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Recyclerview für noch zu erledigende Listenelemente
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        RVMoneyAdapter rvAdapter = new RVMoneyAdapter(getContext(), getDataSet());
        mRecyclerView.setAdapter(rvAdapter);

    }

    private ArrayList<MoneyOverview> getDataSet() {

        int startJahr = 2016;
        int endJahr = 2017;
        int count = 0;
        ArrayList results = new ArrayList<MoneyOverview>();
        for(int jahr = startJahr; jahr <= endJahr; jahr++){

            for(int monat =0; monat <= 11; monat++){

                String newMonat = "01-"+monat+"-"+jahr;

                MoneyOverview obj = new MoneyOverview(1, 453.25,25.30,450.00, newMonat);
                results.add(count, obj);
                count = count+1;
            }

        }

        /*ArrayList results = new ArrayList<MoneyOverview>();
        for (int index = 0; index < 20; index++) {
            MoneyOverview obj = new MoneyOverview(1, 453.25,25.30,450.00, "2017-06-30");
            results.add(index, obj);
        }*/
        return results;
    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.GERMAN);
        String[] months = dfs.getShortMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

}
