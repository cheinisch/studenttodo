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


public class ItemViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout regularLayout;
    public LinearLayout swipeLayout;
    public TextView listItem;
    public TextView listItemDate;
    public CheckBox checkbox;
    public TextView undo;

    public ItemViewHolder(View view) {
        super(view);

        regularLayout = (LinearLayout) view.findViewById(R.id.regularLayout);
        listItem = (TextView) view.findViewById(R.id.textView_item_todolist_content);
        listItemDate  = (TextView) view.findViewById(R.id.textView_item_todolist_Date);
        checkbox = (CheckBox) view.findViewById(R.id.cbToDo);
        swipeLayout = (LinearLayout) view.findViewById(R.id.swipeLayout);
        undo = (TextView) view.findViewById(R.id.undo);

    }
}