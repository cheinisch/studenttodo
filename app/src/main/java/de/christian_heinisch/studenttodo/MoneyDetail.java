package de.christian_heinisch.studenttodo;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.christian_heinisch.studenttodo.adapters.RVMoneyDetailAdapter;
import de.christian_heinisch.studenttodo.database.Money;
import de.christian_heinisch.studenttodo.database.MoneyDataSource;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyDetail extends Fragment {

    int jahr;
    int monat;
    View rootview;
    TextView gesamtText;
    TextView ausgabe;
    TextView einnahme;
    private RecyclerView mRecyclerView;
    private MoneyDataSource datasource;


    public MoneyDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        jahr = this.getArguments().getInt("jahr");
        monat = this.getArguments().getInt("monat");

        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_money_detail, container, false);

        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.rvMoneyDetail);

        datasource = new MoneyDataSource(getContext());

        gesamtText = (TextView) rootview.findViewById(R.id.textView_Gesamt);
        einnahme = (TextView) rootview.findViewById(R.id.textView_Einnahme);
        ausgabe = (TextView) rootview.findViewById(R.id.textView_Ausgabe);



        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Recyclerview für noch zu erledigende Listenelemente
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        RVMoneyDetailAdapter rvAdapter = new RVMoneyDetailAdapter(getContext(), getDataSet());
        mRecyclerView.setAdapter(rvAdapter);

    }

    private ArrayList<Money> getDataSet() {

        int count = 0;
        ArrayList results = new ArrayList<Money>();
        datasource.open();
        ArrayList<Money> arrayOfmoney = null;
                double einnahmen = 0;
                double ausgaben = 0;
                double newgesamt;

                String newmonat;
                int tempmonat = monat + 1;
                if(tempmonat<10){
                    newmonat = "0"+tempmonat;
                }else{
                    newmonat = ""+tempmonat;
                }

                String startMonat =jahr+"-"+newmonat+"-01";
                String endMonat =jahr+"-"+newmonat+"-"+31;

                System.out.println("Monat" + startMonat);


                arrayOfmoney = datasource.getMoneyforMonth(startMonat, endMonat);
                Money obj = null;

                for(int j = 0; j < arrayOfmoney.size(); j++)
                {


                    if(arrayOfmoney.get(j).getTyp() == 1) {
                        einnahmen = einnahmen + arrayOfmoney.get(j).getEinnahme();
                    }else {
                        ausgaben = ausgaben + arrayOfmoney.get(j).getAusgabe();
                    }
                    obj = new Money(count, 0, arrayOfmoney.get(j).getEinnahme(), arrayOfmoney.get(j).getAusgabe(), jahr, monat, 0, arrayOfmoney.get(j).getTyp());

                    results.add(count, obj);
                    count = count + 1;
                }
        datasource.close();

        newgesamt = einnahmen - ausgaben;

        einnahme.setText(einnahmen + " €");
        ausgabe.setText(ausgaben + " €");
        gesamtText.setText(newgesamt + " €");

        return results;
    }

}
