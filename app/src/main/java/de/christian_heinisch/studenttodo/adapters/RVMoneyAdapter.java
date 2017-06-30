package de.christian_heinisch.studenttodo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.christian_heinisch.studenttodo.R;
import de.christian_heinisch.studenttodo.database.Money;
import de.christian_heinisch.studenttodo.database.MoneyOverview;

/**
 * Created by chris on 29.06.2017.
 */

import android.util.Log;

import java.util.ArrayList;

public class RVMoneyAdapter extends RecyclerView
        .Adapter<RVMoneyAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<MoneyOverview> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView gesamt;
        TextView einnahmen;
        TextView ausgaben;
        TextView dateTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            gesamt = (TextView) itemView.findViewById(R.id.textViewMoneyOverviewGesamt);
            einnahmen = (TextView) itemView.findViewById(R.id.textViewMoneyOverviewEinnahme);
            ausgaben = (TextView) itemView.findViewById(R.id.textViewMoneyOverviewAusgaben);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public RVMoneyAdapter(ArrayList<MoneyOverview> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.gesamt.setText(mDataset.get(position).getgEuro() + " €");
        holder.einnahmen.setText(mDataset.get(position).geteEuro() + " €");
        holder.ausgaben.setText(mDataset.get(position).getaEuro() + " €");
    }

    public void addItem(MoneyOverview dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}