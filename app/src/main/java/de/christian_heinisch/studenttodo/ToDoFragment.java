package de.christian_heinisch.studenttodo;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.christian_heinisch.studenttodo.database.ToDo;
import de.christian_heinisch.studenttodo.database.ToDoAdapter;
import de.christian_heinisch.studenttodo.database.ToDoDataSource;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoFragment extends Fragment {


    View rootview;
    private ToDoDataSource dataSource_todo;
    private ListView listView;

    public ToDoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_to_do, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootview.findViewById(R.id.fbToDoAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Test");
                ((StartActivity)getContext()).DialogAddToDO(1);
            }
        });



        getToDoList();

        return rootview;
    }

    public void getToDoList(){
        dataSource_todo = new ToDoDataSource(getContext());


        System.out.println("Die Datenquelle wird geöffnet.");
        dataSource_todo.open();

        //dataSource_todo.createToDo("Dummy", "true", 20170505);

        showAllToDo();
        showAllNotToDo();

        System.out.println("Die Datenquelle wird geschlossen.");
        dataSource_todo.close();
    }

    public void showAllToDo () {

        ArrayList<ToDo> arrayOfToDo = null;
        arrayOfToDo = dataSource_todo.getToDoForList("false");

        ToDoAdapter adapter = new ToDoAdapter(getActivity(), arrayOfToDo);

        listView = (ListView) rootview.findViewById(R.id.listViewToDo);
        listView.setAdapter(adapter);
        /*Fix für die Höhe*/
        setListViewHeightBasedOnChildren(listView);

        /*OnClick Listener*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Knobb gedrückt");



            }
        });
    }

    public void showAllNotToDo () {

        ArrayList<ToDo> arrayOfToDo = null;
        arrayOfToDo = dataSource_todo.getToDoForList("true");

        ToDoAdapter adapter = new ToDoAdapter(getActivity(), arrayOfToDo);

        listView = (ListView) rootview.findViewById(R.id.listViewNotToDo);
        listView.setAdapter(adapter);
        /*Fix für die Höhe*/
        setListViewHeightBasedOnChildren(listView);

        /*OnClick Listener*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Knobb gedrückt");



            }
        });
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        int x = listAdapter.getCount();
        View view = null;
        for (int i = 0; i < x; i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd.MM.yyyy", cal).toString();
        return date;
    }


}
