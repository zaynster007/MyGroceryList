package com.example.mygrocery.Activity.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mygrocery.Activity.Data.DatabaseHandler;
import com.example.mygrocery.Activity.Model.Grocery;
import com.example.mygrocery.Activity.UI.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.mygrocery.R;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    List<Grocery> grocerylist;
    List<Grocery> listitems;
    private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
              //          .setAction("Action", null).show();

                startActivity(new Intent(ListActivity.this,MainActivity.class));

            }
        });
        db=new DatabaseHandler(this);
        recyclerView=  findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        grocerylist=new ArrayList<>();
        listitems=new ArrayList<>();
        grocerylist=db.getAllGrocery();

        for(Grocery c:grocerylist)
        {
            Grocery grocery=new Grocery();
            grocery.setName(c.getName());
            grocery.setQty("QTY : "+c.getQty());
            grocery.setDate("Added on:" +c.getDate());
            listitems.add(grocery);

        }

        recyclerViewAdapter=new RecyclerViewAdapter(this,listitems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();




    }



}
