package com.example.mygrocery.Activity.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.mygrocery.Activity.Data.DatabaseHandler;
import com.example.mygrocery.Activity.Model.Grocery;
import com.example.mygrocery.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText groceryName;
    private EditText quantity;
    private Button saveButton;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         db=new DatabaseHandler(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //after clickinng on add item


                       creatPopupDialouge();



            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void creatPopupDialouge(){
        dialogBuilder=new AlertDialog.Builder(this);
        View v=getLayoutInflater().inflate(R.layout.popup,null);
        groceryName=(EditText) v.findViewById(R.id.grocery_name);
        quantity=(EditText) v.findViewById(R.id.qty);
        saveButton=(Button)v.findViewById(R.id.saveButton);
        dialogBuilder.setView(v);
        dialog=dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to work with database
                if(!groceryName.getText().toString().isEmpty() && !quantity.getText().toString().isEmpty())
                         saveTodb(view);
            }
        });
    }

    private void saveTodb(View view)
    {
        Grocery grocery=new Grocery();
        String newgrocery=groceryName.getText().toString();
        String newQty=quantity.getText().toString();
        grocery.setName(newgrocery);
        grocery.setQty(newQty);

        db.addGrocery(grocery);
        Log.d("item added",String.valueOf(db.getGroceryCount()));
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               dialog.dismiss();
               startActivity(new Intent(MainActivity.this,ListActivity.class));

           }
       },1200);

    }


}
