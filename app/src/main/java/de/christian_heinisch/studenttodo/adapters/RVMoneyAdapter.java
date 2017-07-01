package de.christian_heinisch.studenttodo.adapters;

/**
 * Created by chris on 28.06.2017.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import de.christian_heinisch.studenttodo.MoneyFragment;
import de.christian_heinisch.studenttodo.R;
import de.christian_heinisch.studenttodo.StartActivity;
import de.christian_heinisch.studenttodo.database.Money;
import de.christian_heinisch.studenttodo.database.MoneyOverview;
import de.christian_heinisch.studenttodo.database.ToDoDataSource;

;

public class RVMoneyAdapter extends RecyclerView.Adapter<MoneyObjectHolder> {

    private ArrayList<String> itemsPendingRemoval;
    private ArrayList<MoneyOverview> mDataset;
    private Context mContext;

    ToDoDataSource dataSource = new ToDoDataSource(mContext);

    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<String, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be


    public RVMoneyAdapter(Context context, ArrayList<MoneyOverview> myDataset) {
        mDataset = myDataset;
        mContext = context;
        itemsPendingRemoval = new ArrayList<>();
    }

    @Override
    public MoneyObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new MoneyObjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MoneyObjectHolder holder, final int position) {

        final String gesamt = mDataset.get(position).getgEuro() + " €";
        final String einnahme = mDataset.get(position).geteEuro() + " €";
        final String ausgabe = mDataset.get(position).getaEuro() + " €";
        final String monat;
        final String jahr;

        String[] splitResult = mDataset.get(position).getDate().split("-");
        monat = getMonthForInt(Integer.parseInt(splitResult[1]));
        jahr = splitResult[2];

        holder.ItemEinnahmen.setText(einnahme);
        holder.ItemGesamt.setText(gesamt);
        holder.ItemAusgaben.setText(ausgabe);
        holder.Monat.setText(monat);
        holder.Jahr.setText(jahr);



        /*if (itemsPendingRemoval.contains(data)) {
            // {show swipe layout} and {hide regular layout}
            holder.regularLayout.setVisibility(View.GONE);
            holder.swipeLayout.setVisibility(View.VISIBLE);
            holder.undo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    undoOpt(data);
                }
            });
        } else {
            // {show regular layout} and {hide swipe layout}
            holder.regularLayout.setVisibility(View.VISIBLE);
            holder.swipeLayout.setVisibility(View.GONE);
            holder.listItem.setText(data);
            holder.listItemDate.setText(date);
            if(checkbox.equalsIgnoreCase("true")){
                holder.checkbox.toggle();
            }
        }*/

        /*holder.listItem.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                edit(mDataset.get(position).getId());
                return false;
            }

        });*/
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void reload(){

        Fragment f = new MoneyFragment();
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