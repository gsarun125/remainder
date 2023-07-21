package com.mini.remainder.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
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
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.grid_title.setText(title.get(position));
        holder.grid_title.setSelected(true);

        holder.grid_date.setText(date.get(position));
        holder.grid_date.setSelected(true);

        int color_code=getRandomColour();

        System.out.println("color_code"+color_code);
        holder.card.setCardBackgroundColor(holder.itemView.getResources().getColor(color.get(color_code),null));

        setAnimation(holder.itemView, position);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i= id.get(position);
                listenter.onClick(title.get(position),date.get(position),i);
            }
        });
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String i= id.get(position);
                title.clear();
                id.clear();
                date.clear();
                listenter. OnLongClick(i);
                return true;
            }
        });


    }
    private int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(1000);
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
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


    public  class NoteViewHolder extends RecyclerView.ViewHolder  {


        MaterialCardView card;
        TextView grid_title;
        TextView grid_date;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            grid_title=itemView.findViewById(R.id.grid_title);
            grid_date=itemView.findViewById(R.id.Date);
            card=itemView.findViewById(R.id.card);


        }
    }
    private NoteClickListenter listenter;
    public NoteAdapter(NoteClickListenter listenter, Context context, List<String> title, List<String> date,List<String> id) {
        this.listenter=listenter;
        this.context = context;
        this.title = title;
        this.date = date;
        this.id=id;

    }

    Context context;
    List<String> title = new ArrayList();
    List<String> date = new ArrayList();
    List<String> id = new ArrayList();



}
