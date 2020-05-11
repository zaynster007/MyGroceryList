package com.example.mygrocery.Activity.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygrocery.Activity.Data.DatabaseHandler;
import com.example.mygrocery.Activity.Model.Grocery;
import com.example.mygrocery.R;

import java.util.List;
import java.util.zip.Inflater;

//import static com.example.mygrocery.R.*;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private Grocery grocery;
    private List<Grocery> groceryItems;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private AlertDialog.Builder alertDialogbuilder;

    public RecyclerViewAdapter(Context context, List<Grocery> groceryItems) {
        this.context = context;
        this.groceryItems = groceryItems;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);


        return new RecyclerViewAdapter.ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Grocery grocery=groceryItems.get(position);
        holder.groceryname.setText(grocery.getName());
        holder.qty.setText(grocery.getQty());
        holder.date.setText(grocery.getDate());

    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView groceryname;
        TextView qty;
        TextView date;
        Button edit;
        Button delete;

        int id;
        public ViewHolder(@NonNull View v,Context cxt) {
            super(v);
            context =cxt;
            groceryname=v.findViewById(R.id.grocery_item);
            qty=v.findViewById(R.id.quantity);
            date=v.findViewById(R.id.date);
            edit=v.findViewById(R.id.edit);
            delete=v.findViewById(R.id.delete);
            edit.setOnClickListener(this);
            delete.setOnClickListener(this);
          v.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  //next window
              }
          });



        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            { case R.id.edit:
                int position=getAdapterPosition();
                Grocery groceri=groceryItems.get(position);
                editItem(groceri);


                break;
                case R.id.delete:
                    int pos=getAdapterPosition();
                    Grocery grocery=groceryItems.get(pos);
                    deleteItem(grocery.getId());

                    break;




            }
        }

        void deleteItem(final int id){
            alertDialogbuilder=new AlertDialog.Builder(context);
            inflater=LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.confirmation_dial,null);
            Button yesButton= view.findViewById(R.id.yes);
            Button noButton=view.findViewById(R.id.no);
            alertDialogbuilder.setView(view);
            dialog=alertDialogbuilder.create();
            dialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHandler db=new DatabaseHandler(context);
                    db.deleteGrocery(id);
                    groceryItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();

                }
            });

        }

        void editItem(final Grocery grocery){

            alertDialogbuilder=new AlertDialog.Builder(context);
            inflater=LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.popup,null);
            final EditText groceryItem=view.findViewById(R.id.grocery_name);
            final EditText qunt=view.findViewById(R.id.qty);
            final TextView Title=view.findViewById(R.id.title);
            Title.setText("Edit grocery");
            final Button save=view.findViewById(R.id.saveButton);

            alertDialogbuilder.setView(view);
            dialog=alertDialogbuilder.create();
            dialog.show();

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHandler db=new DatabaseHandler(context);
                    grocery.setName(groceryItem.getText().toString());
                    grocery.setQty(qunt.getText().toString());

                  //  if(!groceryItem.getText().toString().isEmpty()&& !qunt.getText().toString().isEmpty())
                        db.updateGrocery(grocery);
                    notifyItemChanged(getAdapterPosition(),grocery);

                    dialog.dismiss();



                }
            });


        }



    }





}
