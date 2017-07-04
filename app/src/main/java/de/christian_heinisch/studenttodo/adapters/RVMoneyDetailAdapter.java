package de.christian_heinisch.studenttodo.adapters;

/**
 * Created by chris on 28.06.2017.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import de.christian_heinisch.studenttodo.MoneyDetail;
import de.christian_heinisch.studenttodo.MoneyFragment;
import de.christian_heinisch.studenttodo.R;
import de.christian_heinisch.studenttodo.StartActivity;
import de.christian_heinisch.studenttodo.database.Money;
import de.christian_heinisch.studenttodo.database.MoneyDataSource;


public class RVMoneyDetailAdapter extends RecyclerView.Adapter<MoneyDetailObjectHolder> {

    private ArrayList<String> itemsPendingRemoval;
    private ArrayList<Money> mDataset;
    private Context mContext;

    MoneyDataSource dataSource = new MoneyDataSource(mContext);

    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<String, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be


    public RVMoneyDetailAdapter(Context context, ArrayList<Money> myDataset) {
        mDataset = myDataset;
        mContext = context;
        itemsPendingRemoval = new ArrayList<>();
    }

    @Override
    public MoneyDetailObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.money_detail_item, parent, false);
        return new MoneyDetailObjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MoneyDetailObjectHolder holder, final int position) {

        final int typ = mDataset.get(position).getTyp();
        final String date = mDataset.get(position).getTag() + "." + mDataset.get(position).getMonat() + "." + mDataset.get(position).getJahr();
        final String gesamt;
        if(typ == 1){
            gesamt = mDataset.get(position).getEinnahme() + " €";
            holder.TextDetail.setText("Einnahme");
            holder.TextDetailAusgabe.setVisibility(View.GONE);
        }else {
            gesamt = "-"+mDataset.get(position).getAusgabe() + " €";
            holder.TextDetail.setVisibility(View.GONE);
            holder.TextDetailAusgabe.setText("Ausgabe");
        }
        holder.ItemGesamt.setText(gesamt);
        holder.Datum.setText(date);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void reload(){

        Fragment f = new MoneyDetail();
        FragmentManager fragmentManager;
        fragmentManager =((Activity) mContext).getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_start, f);
        ft.addToBackStack(null);
        ft.commit();

    }

    private void edit(long l){

        ((StartActivity)mContext).DialogEditToDO(l);

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