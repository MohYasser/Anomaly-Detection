package com.example.batteryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // creating variables for our list view.
    ListView NumbersLV;

    // creating a new array list.
    ArrayList<Integer> NumbersArrayList;

    // creating a variable for database reference.
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing variables for listviews.
        NumbersLV = findViewById(R.id.idLVNumbers);


        // initializing our array list
        NumbersArrayList = new ArrayList<>();

        // calling a method to get data from
        // Firebase and set data to list view
        initializeListView();

        //This method will listen for the 2 number selection and start new
        //activity where the numbers are operated on and the result is shown
    }

    private void initializeListView() {
        // creating a new array adapter for our list view.
        final ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, NumbersArrayList);

        // below line is used for getting reference
        // of our Firebase Database.
        reference = FirebaseDatabase.getInstance().getReference();

        // in below line we are calling method for add child event
        // listener to get the child of our database.
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when new child is added to
                // our data base and after adding new child
                // we are adding that item inside our array list and
                // notifying our adapter that the data in adapter is changed.
                NumbersArrayList.add(snapshot.getValue(Integer.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when the new child is added.
                // when the new child is added to our list we will be
                // notifying our adapter that data has changed.
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // below method is called when we remove a child from our database.
                // inside this method we are removing the child from our array list
                // by comparing with it's value.
                // after removing the data we are notifying our adapter that the
                // data has been changed.
                NumbersArrayList.remove(snapshot.getValue(Integer.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when we move our
                // child in our database.
                // in our code we are note moving any child.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // this method is called when we get any
                // error from Firebase with error.
            }
        });
        // below line is used for setting an adapter to our list view.
        NumbersLV.setAdapter(adapter);

        //Setting up the OnClickListener and transitioning to the next activity
        ArrayList<String> ListViewClickedValue = new ArrayList<>();
        Intent intent = new Intent(MainActivity.this, OperationScreen.class);
        final int[] i = {0};
        NumbersLV.setOnItemClickListener((parent, view, position, id) -> {
            i[0]++;
            ListViewClickedValue.add(NumbersArrayList.get(position).toString());
            if(i[0]==1) {
                intent.putExtra("FirstNumber", (String) ListViewClickedValue.get(0));
            }else if(i[0]==2) {
                intent.putExtra("SecondNumber", (String) ListViewClickedValue.get(1));
                startActivity(intent);
            }
        });

    }

}
