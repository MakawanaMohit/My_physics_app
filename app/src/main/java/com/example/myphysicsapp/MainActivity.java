package com.example.myphysicsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView l;
    String tutorials[]
            = {
            "Practical- 1 VaniyarCaliper",
            "Practical- 2 Micrometer Screw Gauge",
            "Practical- 3 ",
            "Practical- 4 ",
            "Practical- 5 ",
            "Practical- 6 ",
            "Practical- 7 ",
            "Practical- 8 ",
            "Practical- 9 ",
            "Practical- 10 ",
            "Practical- 11 ",
            "Practical- 12 ",
            "Practical- 13 ",
            "Practical- 14 ",
            "Practical- 15 ",
        };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l = findViewById(R.id.practicallist);
        ArrayAdapter<String> arr;
        arr
                = new ArrayAdapter<String>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                tutorials);
        l.setAdapter(arr);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    Intent intent = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        intent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent);
                    }

                } else if (i==1) {
                    Intent intenti1 = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        intenti1 = new Intent(MainActivity.this, MainActivity3.class);
                        startActivity(intenti1);
                    }
                }
            }
        });
    }
}
