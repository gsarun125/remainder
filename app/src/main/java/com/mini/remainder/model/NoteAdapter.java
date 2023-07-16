package com.mini.remainder.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mini.remainder.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grid,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {

        holder.grid_title.setText(title.get(position));
        holder.grid_date.setText(date.get(position));
    }

    @Override
    public int getItemCount() {
        return date.toArray().length;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView grid_title;
        TextView grid_date;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            grid_title=itemView.findViewById(R.id.grid_title);
            grid_date=itemView.findViewById(R.id.Date);



        }
    }

    public NoteAdapter(Context context, List<String> title, List<String> date) {
        this.context = context;
        this.title = title;
        this.date = date;
    }

    Context context;
    List<String> title = new ArrayList();
    List<String> date = new ArrayList();

}
