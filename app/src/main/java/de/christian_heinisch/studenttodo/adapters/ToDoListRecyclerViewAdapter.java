package de.christian_heinisch.studenttodo.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.christian_heinisch.studenttodo.R;
import de.christian_heinisch.studenttodo.StartActivity;
import de.christian_heinisch.studenttodo.ToDoFragment_RV;
import de.christian_heinisch.studenttodo.database.ToDo;
import de.christian_heinisch.studenttodo.database.ToDoDataSource;

/**
 * Created by chris on 16.06.2017.
 */

public class ToDoListRecyclerViewAdapter extends RecyclerView
        .Adapter<ToDoListRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "ToDoListRecyclerViewAdapter";
    private ArrayList<ToDo> mDataset;
    private static MyClickListener myClickListener;
    private int newposition;
    private Context mContext;
    StartActivity startActivity;

    DataObjectHolder holderdummy;
    ToDoDataSource dataSource = new ToDoDataSource(mContext);

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;
        CheckBox checked;


        public DataObjectHolder(View itemView) {
            super(itemView);

            label = (TextView) itemView.findViewById(R.id.textView_item_todolist_content);
            dateTime = (TextView) itemView.findViewById(R.id.textView_item_todolist_Date);
            checked = (CheckBox) itemView.findViewById(R.id.cbToDo);
            //itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;

    }

    public ToDoListRecyclerViewAdapter(Context context, ArrayList<ToDo> myDataset) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todolist, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    public void onChilddraw(){

    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getToDo());
        holder.dateTime.setText(getDate(mDataset.get(position).isDate()));
        if(mDataset.get(position).isChecked().equalsIgnoreCase("true")){
            holder.checked.toggle();
        }
        //in some cases, it will prevent unwanted situations
        holder.checked.setOnCheckedChangeListener(null);



        newposition = position;
        holderdummy = holder;


        // Set onClicklistener
        holder.label.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                edit(mDataset.get(newposition).getId());
                return false;
            }

        });

        holder.dateTime.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                edit(mDataset.get(newposition).getId());
                return false;
            }

        });

        holder.checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //set your object's last status
                dataSource = new ToDoDataSource(mContext);
                dataSource.open();
                if(holderdummy.checked.isChecked()){
                    dataSource.updateToDo(mDataset.get(newposition).getId(), mDataset.get(newposition).getToDo(), "true", mDataset.get(newposition).isDate());
                }else{
                    dataSource.updateToDo(mDataset.get(newposition).getId(), mDataset.get(newposition).getToDo(), "false", mDataset.get(newposition).isDate());
                }
                dataSource.close();

                // Läd das Fragment neu

                Fragment f = new ToDoFragment_RV();
                FragmentManager fragmentManager;
                fragmentManager =((Activity) mContext).getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_start, f);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }



    public void addItem(ToDo dataObj, int index) {
        mDataset.add(dataObj);
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


    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd.MM.yyyy", cal).toString();
        return date;
    }

    private void edit(long l){

        ((StartActivity)mContext).DialogEditToDO(l);

    }


}