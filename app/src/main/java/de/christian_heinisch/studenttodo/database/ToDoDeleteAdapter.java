package de.christian_heinisch.studenttodo.database;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.christian_heinisch.studenttodo.R;
import de.christian_heinisch.studenttodo.StartActivity;


/**
 * Created by chris on 09.01.2017.
 */

public class ToDoDeleteAdapter extends ArrayAdapter<ToDo> {

    private ToDoDataSource dataSource;

    public ToDoDeleteAdapter(Context context, ArrayList<ToDo> zeugs) {
        super(context, 0, zeugs);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ToDo todo = getItem(position);
        dataSource = new ToDoDataSource(getContext());

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todolist, parent, false);
        }



        TextView tvToDo = (TextView) convertView.findViewById(R.id.textView_item_todolist_content);
        TextView tvDate = (TextView) convertView.findViewById(R.id.textView_item_todolist_Date);
        CheckBox cbChecked = (CheckBox) convertView.findViewById(R.id.cbToDo);


        String Date = getDate(todo.isDate());
        final String checked = String.valueOf(todo.isChecked());


        tvToDo.setText(todo.getToDo().toString());
        tvDate.setText(Date);
        if(checked.equalsIgnoreCase("true")){
            cbChecked.toggle();
        }

        tvDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }

        });

        tvDate.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                ((StartActivity)getContext()).DialogEditToDO(todo.getId());
                return true;
            }

        });

        tvToDo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }

        });

        tvToDo.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                ((StartActivity)getContext()).DialogEditToDO(todo.getId());
                return true;
            }

        });

        cbChecked.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                dataSource.open();
                if(checked.equalsIgnoreCase("true")){
                    dataSource.updateToDo(todo.getId(), todo.getToDo(), "false", todo.isDate());
                }else{
                    dataSource.updateToDo(todo.getId(), todo.getToDo(), "true", todo.isDate());
                }
                dataSource.close();

                ((StartActivity)getContext()).todo();
            }
        });



        return convertView;

    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd.MM.yyyy", cal).toString();
        return date;
    }
}

