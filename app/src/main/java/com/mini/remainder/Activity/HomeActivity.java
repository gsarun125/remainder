package com.mini.remainder.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mini.remainder.R;
import com.mini.remainder.model.GridAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends MainActivity {

    GridView gridView;
    List<String> a_title = new ArrayList();
    List<String> b_title=new ArrayList();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        gridView = findViewById(R.id.list);


        Cursor c1 = getDb().getData();
        if (c1.moveToFirst()) {
                do{
                    @SuppressLint("Range") String data = c1.getString(c1.getColumnIndex("title"));
                    @SuppressLint("Range") String data1 = c1.getString(c1.getColumnIndex("date"));
                    a_title.add(data);
                    b_title.add(data1);

                }while(c1.moveToNext());

        }
        GridAdapter gridAdapter=new GridAdapter(HomeActivity.this,a_title,b_title);
        gridView.setAdapter(gridAdapter);
        ImageView a=(ImageView)findViewById(R.id.image_empty) ;
        gridView.setEmptyView(a);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
