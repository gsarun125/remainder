package com.mini.remainder.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mini.remainder.model.NoteClickListenter;
import com.mini.remainder.R;
import com.mini.remainder.model.NoteAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeActivity extends MainActivity implements NoteClickListenter {

   private RecyclerView recyclerView;
   private List<String> mTitle = new ArrayList();
   private List<String> mDate = new ArrayList();
   private List<String> mId = new ArrayList();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        recyclerView = findViewById(R.id.list);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Refresh_Feed();
        Check_Notification_Settings();

        add.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {


                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in,R.anim.anim_scale_out);
                HomeActivity.super.finish();
            }
        });
    }


    public void Refresh_Feed() {

        DateFormat obj = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");

        Cursor c1 = getDb().getData();
        if (c1.moveToFirst()) {
            do {
                @SuppressLint("Range") String data2 = c1.getString(c1.getColumnIndex("id"));
                @SuppressLint("Range") String data = c1.getString(c1.getColumnIndex("title"));
                @SuppressLint("Range") String data1 = c1.getString(c1.getColumnIndex("date"));
                long l = Long.parseLong(data1);
                Date res = new Date(l);
                mId.add(data2);
                mTitle.add(data);
                mDate.add(obj.format(res).toString());

            } while (c1.moveToNext());

        }
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        NoteAdapter noteAdapter = new NoteAdapter(this, HomeActivity.this, mTitle, mDate, mId);
        recyclerView.setAdapter(noteAdapter);

    }


    @Override
    public void onClick(String Title, String date, String id) {
         Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", Title);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.anim_scale_out);
        HomeActivity.super.finish();
    }

    @SuppressLint("Range")
    @Override
    public void OnLongClick(String position) {
        Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show();
        Cursor c1 = getDb().GetRequestID(position);
        String requestid = null;
        if (c1.moveToFirst()) {
            do {

                requestid = c1.getString(c1.getColumnIndex("requestid"));

            } while (c1.moveToNext());
        }

        cancel(Integer.parseInt(requestid));
        getDb().delete(position);
        Refresh_Feed();

    }

    @Override
    public void onBackPressed() {
        HomeActivity.super.finish();
    }
}
