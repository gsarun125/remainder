package com.mini.remainder.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mini.remainder.DataBase.DataBaseHandler;
import com.mini.remainder.R;

public class EditActivity extends AppCompatActivity {

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
            @Override
            public void onClick(View view) {

                String newTitle=editText.getText().toString();
                db.update(title,id,newTitle);
                Intent intent=new Intent(EditActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}