package com.mini.remainder.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mini.remainder.R;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends MainActivity {

    ListView l;
    List<String> a = new ArrayList();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        l = findViewById(R.id.list);


        Cursor c1 = getDb().getData();
        if (c1.moveToFirst()) {
            do {
                StringBuilder sb = new StringBuilder();
                int columnsQty = c1.getColumnCount();
                for (int i = 0; i < columnsQty; ++i) {
                    sb.append(c1.getString(i));
                    if (i < columnsQty - 1)
                        sb.append("  ");
                }
                a.add(sb.toString());
            } while (c1.moveToNext());
        }


            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, a);
            l.setAdapter(arrayAdapter);
           ImageView a=(ImageView)findViewById(R.id.image_empty) ;
            l.setEmptyView(a);


            add.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            });


    }
}
