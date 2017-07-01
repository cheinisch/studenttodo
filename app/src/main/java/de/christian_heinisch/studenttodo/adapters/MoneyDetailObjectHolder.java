package de.christian_heinisch.studenttodo.adapters;

/**
 * Created by chris on 28.06.2017.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.christian_heinisch.studenttodo.R;


public class MoneyDetailObjectHolder extends RecyclerView.ViewHolder {

    public LinearLayout regularLayout;
    public TextView ItemGesamt;
    public TextView Monat;
    public TextView Jahr;
    public TextView TextDetail;
    public TextView TextDetailAusgabe;


    public MoneyDetailObjectHolder(View view) {
        super(view);

        regularLayout = (LinearLayout) view.findViewById(R.id.regularLayout);
        ItemGesamt = (TextView) view.findViewById(R.id.moneyDetailEuro);
        Monat  = (TextView) view.findViewById(R.id.textViewMoneyOverviewMonat);
        Jahr  = (TextView) view.findViewById(R.id.textViewMoneyOverviewJahr);
        TextDetail = (TextView) view.findViewById(R.id.textViewDetailText);
        TextDetailAusgabe = (TextView) view.findViewById(R.id.textViewDetailTextAusgabe);

    }
}