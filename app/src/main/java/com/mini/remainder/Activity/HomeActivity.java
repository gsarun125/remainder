package com.mini.remainder.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mini.remainder.R;
import com.mini.remainder.model.NoteAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends MainActivity {

    RecyclerView recyclerView;
    List<String> a_title = new ArrayList();
    List<String> b_title=new ArrayList();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        recyclerView = findViewById(R.id.list);


        Cursor c1 = getDb().getData();
        if (c1.moveToFirst()) {
                do{
                    @SuppressLint("Range") String data = c1.getString(c1.getColumnIndex("title"));
                    @SuppressLint("Range") String data1 = c1.getString(c1.getColumnIndex("date"));
                    a_title.add(data);
                    b_title.add(data1);

                }while(c1.moveToNext());

        }


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        NoteAdapter noteAdapter=new NoteAdapter(HomeActivity.this,a_title,b_title);
        recyclerView.setAdapter(noteAdapter);
        /*
        ImageView a=(ImageView)findViewById(R.id.image_empty) ;
        gridView.setEmptyView(a);


 */

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

           /* ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, a);
            l.setAdapter(arrayAdapter);
            ImageView a=(ImageView)findViewById(R.id.image_empty) ;
            l.setEmptyView(a);

            */

            add.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            });
    }
}
