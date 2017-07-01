package de.christian_heinisch.studenttodo.adapters;

/**
 * Created by chris on 28.06.2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.christian_heinisch.studenttodo.R;


public class MoneyObjectHolder extends RecyclerView.ViewHolder {

    public LinearLayout regularLayout;
    public TextView ItemGesamt;
    public TextView ItemEinnahmen;
    public TextView ItemAusgaben;
    public TextView Monat;
    public TextView Jahr;


    public MoneyObjectHolder(View view) {
        super(view);

        regularLayout = (LinearLayout) view.findViewById(R.id.regularLayout);
        ItemGesamt = (TextView) view.findViewById(R.id.textViewMoneyOverviewGesamt);
        ItemEinnahmen = (TextView) view.findViewById(R.id.textViewMoneyOverviewEinnahme);
        ItemAusgaben  = (TextView) view.findViewById(R.id.textViewMoneyOverviewAusgaben);
        Monat  = (TextView) view.findViewById(R.id.textViewMoneyOverviewMonat);
        Jahr  = (TextView) view.findViewById(R.id.textViewMoneyOverviewJahr);

    }
}