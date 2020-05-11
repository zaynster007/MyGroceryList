package com.example.mygrocery.Activity.Data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mygrocery.Activity.Model.Grocery;
import com.example.mygrocery.Activity.Util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context ctx;
    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME,null,Constants.DB_VERSION);
        this.ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE "+Constants.TABLE_NAME+" ("+Constants.KEY_ID +" INTEGER PRIMARY KEY ,"+
                Constants.KEY_GROCERY_ITEM +" TEXT ,"+Constants.KEY_QTY +" TEXT ,"+Constants.KEY_DATE+" LONG);";
                db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +Constants.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    //Operations add,delete,update,count etc.

   public void addGrocery(Grocery grocery){
        SQLiteDatabase db=this.getWritableDatabase();
       ContentValues values=new ContentValues();
       values.put(Constants.KEY_GROCERY_ITEM,grocery.getName());
       values.put(Constants.KEY_QTY,grocery.getQty());
       values.put(Constants.KEY_DATE,java.lang.System.currentTimeMillis());
       db.insert(Constants.TABLE_NAME,null,values);
       Log.d("saved","Saved in db");


   }

   public Grocery getAGrocery(int id){


        SQLiteDatabase db=this.getWritableDatabase();
      Cursor cursor=db.query(Constants.TABLE_NAME,new String[] {Constants.KEY_ID,Constants.KEY_GROCERY_ITEM,Constants.KEY_QTY
                 ,Constants.KEY_DATE },Constants.KEY_ID+"=?",new String[] {String.valueOf(id)},null,null,null);

       Grocery grocery=new Grocery();
      if(cursor!=null){
          cursor.moveToFirst();


              //Grocery grocery=new Grocery();
              grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
              grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
              grocery.setQty(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY)));
             //date formatting
              DateFormat dateFormat= DateFormat.getDateInstance();
              String fromatedDate= dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE))).getTime());


             grocery.setDate(fromatedDate);


      }


       return grocery;


   }

   public List<Grocery> getAllGrocery(){
       SQLiteDatabase db=this.getReadableDatabase();
       List<Grocery> groceriesList=new ArrayList<>();

       Cursor cursor=db.query(Constants.TABLE_NAME,new String[] {Constants.KEY_ID,Constants.KEY_GROCERY_ITEM,Constants.KEY_QTY,Constants.KEY_DATE},
               null,null,null,null,Constants.KEY_DATE+" DESC");
       if(cursor.moveToFirst())
       {
          do
          {
              Grocery grocery=new Grocery();
              grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
              grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
              grocery.setQty(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY)));
              //date formatting
              DateFormat dateFormat= DateFormat.getDateInstance();
              String fromatedDate= dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE))).getTime());


              grocery.setDate(fromatedDate);

              groceriesList.add(grocery);

              cursor.moveToNext();
          }while (cursor.moveToNext() );
       }
       return groceriesList;
   }



   public int  updateGrocery(Grocery grocery){

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
       values.put(Constants.KEY_GROCERY_ITEM,grocery.getName());
       values.put(Constants.KEY_QTY,grocery.getQty());
       values.put(Constants.KEY_DATE,java.lang.System.currentTimeMillis());




        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID+" =?",new String[] {String.valueOf(grocery.getId())});
   }

   public void deleteGrocery(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();


   }
   public int  getGroceryCount(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+Constants.TABLE_NAME,null);

        return cursor.getCount();

   }








}
