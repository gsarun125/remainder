package com.mini.remainder.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.mini.remainder.NoteClickListenter;
import com.mini.remainder.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {


    List<Integer> color=new ArrayList<>();


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grid,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {

        holder.grid_title.setText(title.get(position));
        holder.grid_title.setSelected(true);

        holder.grid_date.setText(date.get(position));
        holder.grid_date.setSelected(true);

        int color_code=getRandomColour();

        System.out.println("color_code"+color_code);
        holder.card.setCardBackgroundColor(holder.itemView.getResources().getColor(color.get(color_code),null));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenter.onClick(title.get(position),date.get(position));
            }
        });
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listenter. OnLongClick(date.get(position));

                return true;
            }
        });


    }

    private int getRandomColour(){

        color.add(R.color.color1);
        color.add(R.color.color2);
        color.add(R.color.color3);
        color.add(R.color.color4);
        color.add(R.color.color5);
        color.add(R.color.color6);
        color.add(R.color.color7);
        System.out.println("ffrrrr"+color.get(1));
        Random random= new Random();
        int random_color=random.nextInt(color.size());

        return random_color;
    }
    @Override
    public int getItemCount() {
        return date.toArray().length;
    }


    public  class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        MaterialCardView card;
        TextView grid_title;
        TextView grid_date;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            grid_title=itemView.findViewById(R.id.grid_title);
            grid_date=itemView.findViewById(R.id.Date);
            card=itemView.findViewById(R.id.card);


        }

        @Override
        public void onClick(View view) {
            Log.d("ClickFromViewHolder", "Clicked");
        }
    }
    private NoteClickListenter listenter;
    public NoteAdapter(NoteClickListenter listenter, Context context, List<String> title, List<String> date) {
        this.listenter=listenter;
        this.context = context;
        this.title = title;
        this.date = date;
    }

    Context context;
    List<String> title = new ArrayList();
    List<String> date = new ArrayList();


}
