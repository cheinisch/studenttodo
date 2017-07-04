package de.christian_heinisch.studenttodo;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import de.christian_heinisch.studenttodo.adapters.RVMoneyAdapter;
import de.christian_heinisch.studenttodo.database.Money;
import de.christian_heinisch.studenttodo.database.MoneyDataSource;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyFragment extends Fragment {

    View rootview;
    RecyclerView mRecyclerView;
    private MoneyDataSource datasource;


    public MoneyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_money, container, false);

        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.my_recycler_view);

        FloatingActionButton fab = (FloatingActionButton) rootview.findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((StartActivity)getContext()).DialogAddMoney();
            }
        });

        datasource = new MoneyDataSource(getContext());


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

    private ArrayList<Money> getDataSet() {

        int startJahr = 2016;
        int endJahr = 2017;
        int count = 0;
        double gesamt = 0;

        ArrayList results = new ArrayList<Money>();
        datasource.open();
        ArrayList<Money> arrayOfmoney = null;

        for(int jahr = startJahr; jahr <= endJahr; jahr++){

            for(int monat =0; monat <= 11; monat++){

                double einnahmen = 0;
                double ausgaben = 0;

                String newmonat;
                int tempmonat = monat + 1;
                if(tempmonat<10){
                    newmonat = "0"+tempmonat;
                }else{
                    newmonat = ""+tempmonat;
                }

                String startMonat =jahr+"-"+newmonat+"-01";
                String endMonat =jahr+"-"+newmonat+"-"+31;


                arrayOfmoney = datasource.getMoneyforMonth(startMonat, endMonat);


                for(int i = 0; i < arrayOfmoney.size(); i++)
                {
                    if(arrayOfmoney.get(i).getTyp()== 1)
                    {
                        einnahmen = einnahmen + arrayOfmoney.get(i).getEuro();
                    }else{
                        ausgaben = ausgaben + arrayOfmoney.get(i).getEuro();
                    }
                }
                gesamt = gesamt + einnahmen;
                gesamt = gesamt - ausgaben;
                if(einnahmen != 0 || ausgaben != 0) {
                    Money obj = new Money(count, gesamt, einnahmen, ausgaben, jahr, monat, 0, 0);
                    //results.add(count, obj);
                    results.add(count, obj);
                }
            }

        }
        datasource.close();
        return results;
    }



}
