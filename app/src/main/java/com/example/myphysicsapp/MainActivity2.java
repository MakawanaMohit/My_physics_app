package com.example.myphysicsapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SharedMemory;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Magnifier;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

@RequiresApi(api = Build.VERSION_CODES.P)
public class MainActivity2 extends AppCompatActivity {
    ImageView drag, drag1;


    TextView txt ,setxbtn,msc;
    Magnifier mg,mgm;
    public float vscx,vscy,mscx,mscy;
    public boolean calibrate,setx,MSC;
    public static final String SHARED_PREF = "sharedpref";
    public static final String VSC_X = "vscx";
    public static final String VSC_Y = "vscy";
    public static final String MSC_X = "mscx";
    public static final String MSC_Y = "mscy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SharedPreferences shpref = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        vscx = shpref.getFloat(VSC_X,9);
        vscy = shpref.getFloat(VSC_Y,9);
        mscx = shpref.getFloat(MSC_X,9);
        mscy = shpref.getFloat(MSC_Y,9);

        drag = findViewById(R.id.imageView2);
        drag.setY(300);
        drag.setX(300);
        drag1 = findViewById(R.id.imageView);
        txt = findViewById(R.id.textView3);
        setxbtn = findViewById(R.id.textView);
        msc = findViewById(R.id.textView2);
        calibrate = false;
        MSC = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mgm = new Magnifier.Builder(drag).setSize(878, 458).build();
            mgm.setZoom((float) 1.73);
            mg = new Magnifier.Builder(drag).setSize(368, 658).build();
            mg.setZoom((float) 1.73);
        }
        // creating object of Loadingdialog class and passing MainActivity as argument
        final dialog loadingdialog = new dialog(MainActivity2.this);
        loadingdialog.startLoadingdialog();

        // using handler class to set time delay methods
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // after 4 seconds
                loadingdialog.dismissdialog();

                 drag.setX(vscx);
                 drag.setY(vscy);
                 drag1.setY(mscy);
                 drag1.setX(mscx);
//                Intent intent = null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
//                    intent = new Intent(MainActivity.this, MainActivity2.class);
//                }
//                startActivity(intent);
            }
        }, 150); // 4 seconds

        setx = true;
        setxbtn.setText("lock x cordinate");
        setxbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!setx){
                    setx = true;
                    setxbtn.setText("lock x cordinate");
                }else {
                    setx = false;
                    setxbtn.setText("re calibrate x");
                }
            }
        });
        msc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mgm.dismiss();
                if(MSC){
                    MSC  = false;
                    msc.setText("Re Calibrate the main scale ");
                    calibrate = true;
                    MSC = false;

                    txt.setText("Set Calibrations");
                    setxbtn.setVisibility(view.VISIBLE);
                    if(!setx){
                        setx = true;
                        setxbtn.setText("lock x cordinate");
                    }else {
                        setx = false;
                        setxbtn.setText("re calibrate x");
                    }
                }else{
                    MSC = true;
                    msc.setText("Calibrate and go to vernier scale calibration ");
                    setxbtn.setVisibility(View.INVISIBLE);
                    txt.setText("place the main scale as your need");

                }
            }
        });
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mg.dismiss();
                mgm.dismiss();
                if (!calibrate || MSC) {
                    if (!MSC) {
                        MSC = true;
                        calibrate = true;
                        msc.setVisibility(View.VISIBLE);
                        msc.setText("Calibrate and go to vernier scale calibration ");
                        txt.setText("place the main scale as your need");
                    }
                }else {
                    calibrate = false;
                    MSC = false;
                    setx = true;
                    txt.setText("Calibrate the Vernier Calliper");
                    setxbtn.setVisibility(View.INVISIBLE);
                    msc.setVisibility(View.INVISIBLE);
                    SharedPreferences shpref = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
                    SharedPreferences.Editor editor = shpref.edit();
                    editor.putFloat(VSC_X,drag.getX());
                    editor.putFloat(VSC_Y,vscy);
                    editor.putFloat(MSC_X,mscx);
                    editor.putFloat(MSC_Y,mscy);
                    editor.apply();
                }

            }
        });

    }

    float x, y, dx, dy;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (calibrate) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x = event.getX();
                y = event.getY();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !MSC) {
                    mgm.dismiss();
                }
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (MSC){

                    dx = event.getX() - x;

                    mscx = drag1.getX() + dx;
                    dy = event.getY() - y;
                    mscy = drag1.getY() + dy;
                    drag1.setX(mscx);
                    drag1.setY(mscy);

                    x = event.getX();
                    y = event.getY();
                }else {
                    if (setx) {
                        dx = event.getX() - x;
                        vscx = drag.getX() + dx/(float) 1.4;
                    }
                    dy = event.getY() - y;
                    vscy = drag.getY() +dy/(float) 2;
                    drag.setX(vscx);
                    drag.setY(vscy);

                    x = event.getX();
                    y = event.getY();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !MSC) {

                        mgm.show(-vscx-drag.getWidth()*(float)1.7, vscy+drag.getHeight()/2-800,-790-vscx,390-vscy);
                    }
                }
            }


        }
            else {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x = event.getX();
                    y = event.getY();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        mg.dismiss();
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    dx = event.getX() - x;
                    dy = event.getY() - y;
                        float pos = drag.getY() + dy/ (float) 1.3;
//                        setxbtn.setText("pos = "+pos+"vsc"+(vscy+drag1.getHeight()/2));
//                        setxbtn.setVisibility(View.VISIBLE);

                        if (pos < (vscy)) {
                            drag.setY(vscy-(float) 0.38);
                        } else if (pos>vscy+drag1.getHeight()/2) {
                            drag.setY(vscy+drag1.getHeight()/2);
                        }else {
                            drag.setY(pos);
                        }

                    x = event.getX();
                    y = event.getY();
                    drag.bringToFront();
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        mg.show(-1055, 293, -685, 100);
                    }
                }
            }



        return super.onTouchEvent(event);
    }
}




