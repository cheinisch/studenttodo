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
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import de.christian_heinisch.studenttodo.R;
import de.christian_heinisch.studenttodo.StartActivity;
import de.christian_heinisch.studenttodo.ToDoFragment;;
import de.christian_heinisch.studenttodo.database.ToDo;
import de.christian_heinisch.studenttodo.database.ToDoDataSource;

public class RVAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private ArrayList<String> itemsPendingRemoval;
    private ArrayList<ToDo> mDataset;
    private Context mContext;

    ToDoDataSource dataSource = new ToDoDataSource(mContext);

    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<String, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be


    public RVAdapter(Context context, ArrayList<ToDo> myDataset) {
        mDataset = myDataset;
        mContext = context;
        itemsPendingRemoval = new ArrayList<>();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        final String data = mDataset.get(position).getToDo();
        final String date = getDate(mDataset.get(position).isDate());
        final String checkbox = mDataset.get(position).isChecked();

        if (itemsPendingRemoval.contains(data)) {
            /** {show swipe layout} and {hide regular layout} */
            holder.regularLayout.setVisibility(View.GONE);
            holder.swipeLayout.setVisibility(View.VISIBLE);
            holder.undo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    undoOpt(data);
                }
            });
        } else {
            /** {show regular layout} and {hide swipe layout} */
            holder.regularLayout.setVisibility(View.VISIBLE);
            holder.swipeLayout.setVisibility(View.GONE);
            holder.listItem.setText(data);
            holder.listItemDate.setText(date);
            if(checkbox.equalsIgnoreCase("true")){
                holder.checkbox.toggle();
            }
        }

        holder.listItem.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                edit(mDataset.get(position).getId());
                return false;
            }

        });

        holder.checkbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dataSource = new ToDoDataSource(mContext);
                dataSource.open();
                // TODO Auto-generated method stub
                if(holder.checkbox.isChecked()){
                    System.out.println("Checked");
                    dataSource.updateToDo(mDataset.get(position).getId(), mDataset.get(position).getToDo(), "true", mDataset.get(position).isDate());
                }else{
                    System.out.println("un-Checked");
                    dataSource.updateToDo(mDataset.get(position).getId(), mDataset.get(position).getToDo(), "false", mDataset.get(position).isDate());
                }
                dataSource.close();

                // Läd das Fragment neu

                reload();
            }
        });
    }

    private void undoOpt(String customer) {
        Runnable pendingRemovalRunnable = pendingRunnables.get(customer);
        pendingRunnables.remove(customer);
        if (pendingRemovalRunnable != null)
            handler.removeCallbacks(pendingRemovalRunnable);
        itemsPendingRemoval.remove(customer);
        // this will rebind the row in "normal" state
        notifyItemChanged(mDataset.indexOf(customer));
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void pendingRemoval(final int position) {


        final String data = mDataset.get(position).getToDo();
        if (!itemsPendingRemoval.contains(data)) {
            itemsPendingRemoval.add(data);
            // this will redraw row in "undo" state
            notifyItemChanged(position);
            // let's create, store and post a runnable to remove the data
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    //remove(mDataset.indexOf(data));

                    dataSource = new ToDoDataSource(mContext);
                    dataSource.open();
                    dataSource.deleteToDo(mDataset.get(position).getId());
                    dataSource.close();

                    reload();
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(data, pendingRemovalRunnable);
        }
    }

    public void remove(int position) {
        String data = mDataset.get(position).getToDo();
        if (itemsPendingRemoval.contains(data)) {
            itemsPendingRemoval.remove(data);
        }
        if (mDataset.contains(data)) {
            mDataset.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean isPendingRemoval(int position) {
        String data = mDataset.get(position).getToDo();
        return itemsPendingRemoval.contains(data);
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd.MM.yyyy", cal).toString();
        return date;
    }

    private void reload(){

        Fragment f = new ToDoFragment();
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

}