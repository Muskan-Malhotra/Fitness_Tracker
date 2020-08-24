package com.example.fitness_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText Tname;
    TextView Nmae;
    Button load;
    Spinner spinner;
    TextView textView;
    TextView textView2;
    EditText CC;
    EditText CB;
    //    Button button;
    Button savebutton;
    SharedPreferences shared;
    final String savetext = "Save_text";
    final String Savetext = "Save_Text";
    int count = 0;
    //SharedPreferences.Editor editor = shared.edit();

//to run the code open the firebase console first.
    //creating database reference to its object.

    DatabaseReference databasePerson;

    ListView listViewperson;

    //list to store all the persons
    List<Person> personlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        Nmae = (TextView) findViewById(R.id.Nmae);
        CC = (EditText) findViewById(R.id.CC);
        CC.setEnabled(false);
        CB = (EditText) findViewById(R.id.CB);
        CB.setEnabled(false);

//        button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(this);
        savebutton = (Button) findViewById(R.id.savebutton);
        savebutton.setOnClickListener((View.OnClickListener) this);
        //loadText();

        Tname = (EditText) findViewById(R.id.Tname);
        Tname.setEnabled(false);
        load = (Button) findViewById(R.id.load);
        spinner = (Spinner) findViewById(R.id.spinner);
        listViewperson = (ListView) findViewById(R.id.listViewperson);


        //now we will get the database reference
        //if you dont pass the path then we will get the root node of the json tree from firebase database.
        databasePerson = FirebaseDatabase.getInstance().getReference("Persons");
        personlist = new ArrayList<>();

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
    }


    private void load() {
//        super.onStart();

        databasePerson.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                personlist.clear();
                //dataSnapshot contains every person in the list each tym it is executed.
                //executed evrytime we change anythng in the database.
                //fetch all the values inside persons inside the specified reference person.
                for(DataSnapshot personSnapshot : dataSnapshot.getChildren()){
                    //iterate all the values of the databse
                    Person person = personSnapshot.getValue(Person.class);
                    personlist.add(person);

                }
                PersonList adapter = new PersonList(MainActivity.this,personlist);
                listViewperson.setAdapter(adapter);
                //then we have attached it to our ist view.//everytime data is added add(person) method is executed.
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //executed if thee is some error
            }
        });
    }


    private void AddPerson(){
// we will get the person name and age from here.
      String name = Tname.getText().toString().trim(); //trim func is used to trim the white spaces after the name is written.
//we have taken name as a string type variable to get the name of the person same is with the age of the person.
      String age = spinner.getSelectedItem().toString();
      String CalC = CC.getText().toString();
      String Calb = CB.getText().toString();

      // check if the name is filled or not
      if(!TextUtils.isEmpty(name)){
            //creating unique id for person
          String id = databasePerson.push().getKey();
          //this will create a unique string inside persons.
            //using getkey we can get the string.
          //we will store it inside a string.
           //now w will create  a new person
          Person person = new Person(id, name, age, CalC, Calb);
          //now we us the set value method to store the person into firebase.
          //can do it like this==>databasePerson.setValue(person); but we need to store person inside the id i.e uniquely generated.
          databasePerson.child(id).setValue(person);
          // if we again set the value to person the the value will overidde onthis everytime. so everytime it will generate a unique id and inside that id it will store person details.
          Toast.makeText(this,"Details Added",Toast.LENGTH_LONG).show();
          // everytime new node will created inside person node and inside it the person will be stored


      }
      else{
          // a msg will pop up.
          Toast.makeText(this," You are required to enter your name",Toast.LENGTH_LONG).show();
      }
    }


    @SuppressLint("SetTextI18n")
    public void onClick(View v) {

        if (count == 0) {
            //v.getId();
            CC.setEnabled(true);
            CB.setEnabled(true);
            Tname.setEnabled(true);
            //load.setEnabled(true);
            //loadText();
            //AddPerson();

            count++;
            savebutton.setText("Save");
        } else {
            // onDestroy() used to close the app
            saveText();
            CC.setEnabled(false);
            CB.setEnabled(false);
            Tname.setEnabled(false);
            savebutton.setText("Edit");
            count--;
            //case R.id.button:
//                loadText();
//                break;
        }
    }
    @SuppressLint("SetTextI18n")
    void saveText() {
        String name = Tname.getText().toString().trim();
        String age = spinner.getSelectedItem().toString();
        String CalC = CC.getText().toString();
        String Calb = CB.getText().toString();
        if(!TextUtils.isEmpty(name)){
            String id = databasePerson.push().getKey();

            Person person = new Person(id, name, age, CalC, Calb);
            databasePerson.child(id).setValue(person);

            Toast.makeText(this,"Details Added",Toast.LENGTH_LONG).show();
        }
        else{
            // a msg will pop up.
            Toast.makeText(this," You are required to enter your name",Toast.LENGTH_LONG).show();
        }

        Toast.makeText(this, "Data Saved", Toast.LENGTH_LONG).show();


    }
//    @SuppressLint("SetTextI18n")
//    void changeText(){
//        CC= new EditText(MainActivity.this);
//        CC.setText("Show Up");  // after restarting the app this show up msg is placed in cc
////        Toast.makeText(this,"Text Loaded",Toast.LENGTH_LONG).show();
//    }
//
//    void loadText(){
//
////        shared = getPreferences(Context.MODE_PRIVATE);
////        String savedText = shared.getString(savetext," ");
////        String SavedText = shared.getString(Savetext," ");
////        CC.setText(savedText);
////        CB.setText(SavedText);
////        //AddPerson();
//
////        Toast.makeText(this,"Text Loaded",Toast.LENGTH_LONG).show();
//    }
//    @SuppressLint("SetTextI18n")
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        savebutton.setText("Edit");
//        saveText();
}

// to store the value we will get a firebase database refer. also we need a model class to store all the attributes of the persons.

