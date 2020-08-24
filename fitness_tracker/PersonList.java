package com.example.fitness_tracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import static com.example.fitness_tracker.R.layout.activity_main;
import static com.example.fitness_tracker.R.layout.list_layout;

public class PersonList extends ArrayAdapter<Person> {
    private Activity context;
    private List<Person> personlist;

    public PersonList(Activity context, List<Person> personlist){
        super(context, R.layout.list_layout, personlist);
        this.context = context;
        this.personlist = personlist;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null, true);
        TextView textV1 = (TextView) listViewItem.findViewById(R.id.textV1);
        TextView textV2 = (TextView) listViewItem.findViewById(R.id.textV2);
        TextView textV3 = (TextView) listViewItem.findViewById(R.id.textV3);
        TextView textV4 = (TextView) listViewItem.findViewById(R.id.textV4);

        Person person = personlist.get(position);

        textV1.setText(person.getpname());
        textV2.setText(person.getpage());
        textV3.setText(person.getcalCon());
        textV4.setText(person.getcalB());

        return listViewItem;

    }






}
