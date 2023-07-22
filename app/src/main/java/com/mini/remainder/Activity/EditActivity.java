package com.mini.remainder.Activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mini.remainder.DataBase.DataBaseHandler;
import com.mini.remainder.R;

public class EditActivity extends MainActivity {

    DataBaseHandler db=new DataBaseHandler(this);
    TextInputEditText editText;
    FloatingActionButton update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent=getIntent();
        String id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");

        editText=(TextInputEditText)findViewById(R.id.editText);
        update=(FloatingActionButton)findViewById(R.id.update);

        editText.setText(title);

        System.out.println(id);
        System.out.println(title);

        update.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {

                String newTitle=editText.getText().toString();
                db.update(title,id,newTitle);

                System.out.println(id);

                Cursor c1 = db.GetRequestID(id);
                String requestid = null;
                Long time = null;

                if (c1.moveToFirst()) {
                    do {

                        requestid = c1.getString(c1.getColumnIndex("requestid"));
                        time=c1.getLong(c1.getColumnIndex("date"));
                        System.out.println(requestid);
                        System.out.println(time);
                    } while (c1.moveToNext());
                }
                int Request_ID=Integer.parseInt(requestid);

                set(getNotification(newTitle,Request_ID),time,Request_ID);

                Intent intent=new Intent(EditActivity.this,HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.anim_scale_out);
               finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(this,HomeActivity.class);
        startActivity(i);
        finish();
    }

}