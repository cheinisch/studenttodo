package de.christian_heinisch.studenttodo;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.christian_heinisch.studenttodo.adapters.RVAdapter;
import de.christian_heinisch.studenttodo.adapters.SwipeUtil;
import de.christian_heinisch.studenttodo.database.ToDo;
import de.christian_heinisch.studenttodo.database.ToDoDataSource;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewErledigt;
    private ToDoDataSource dataSource_todo;

    public ToDoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_to_do, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
        mRecyclerViewErledigt = (RecyclerView) mView.findViewById(R.id.recyclerViewErledigt);

        FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fbToDoAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((StartActivity)getContext()).DialogAddToDO();
            }
        });

        return mView;
    }


    @Override
    public void onResume() {
        super.onResume();
        // Recyclerview für noch zu erledigende Listenelemente
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        RVAdapter rvAdapter = new RVAdapter(getContext(), getData("false"));
        mRecyclerView.setAdapter(rvAdapter);

        setSwipeForRecyclerView();


        // Recyclerview für erledigte Listenelemente
        LinearLayoutManager linearLayoutManagerErledigt = new LinearLayoutManager(getActivity());
        linearLayoutManagerErledigt.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewErledigt.setLayoutManager(linearLayoutManagerErledigt);

        RVAdapter rvAdapterErledigt = new RVAdapter(getContext(), getData("true"));
        mRecyclerViewErledigt.setAdapter(rvAdapterErledigt);

        setSwipeForRecyclerViewErledigt();


    }

    private ArrayList<ToDo> getData(String checked) {
        dataSource_todo = new ToDoDataSource(getContext());
        dataSource_todo.open();

        ArrayList<ToDo> arrayOfToDo = null;
        arrayOfToDo = dataSource_todo.getToDoForList(checked);
        dataSource_todo.close();

        return arrayOfToDo;
    }

    private void setSwipeForRecyclerView() {

        SwipeUtil swipeHelper = new SwipeUtil(0, ItemTouchHelper.LEFT, getActivity()) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPosition = viewHolder.getAdapterPosition();
                RVAdapter adapter = (RVAdapter) mRecyclerView.getAdapter();
                adapter.pendingRemoval(swipedPosition);
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                RVAdapter adapter = (RVAdapter) mRecyclerView.getAdapter();
                if (adapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }
        };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(swipeHelper);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        //set swipe label
        swipeHelper.setLeftSwipeLable("Löschen");
        //set swipe background-Color
        swipeHelper.setLeftcolorCode(ContextCompat.getColor(getActivity(), R.color.swipebg));

    }

    private void setSwipeForRecyclerViewErledigt() {

        SwipeUtil swipeHelper = new SwipeUtil(0, ItemTouchHelper.LEFT, getActivity()) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPosition = viewHolder.getAdapterPosition();
                RVAdapter adapter = (RVAdapter) mRecyclerViewErledigt.getAdapter();
                adapter.pendingRemoval(swipedPosition);
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                RVAdapter adapter = (RVAdapter) mRecyclerViewErledigt.getAdapter();
                if (adapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }
        };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(swipeHelper);
        mItemTouchHelper.attachToRecyclerView(mRecyclerViewErledigt);

        //set swipe label
        swipeHelper.setLeftSwipeLable("Löschen");
        //set swipe background-Color
        swipeHelper.setLeftcolorCode(ContextCompat.getColor(getActivity(), R.color.swipebg));

    }

}

