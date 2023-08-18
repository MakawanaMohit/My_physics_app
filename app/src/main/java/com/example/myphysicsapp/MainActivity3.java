package com.example.myphysicsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class MainActivity3 extends AppCompatActivity {
    public String getURLForResource (int resourceId) {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }

    // Set some constants
    private static String path = Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +R.drawable.img_3).toString();

    // Set some constants
    private static final Bitmap SOURCE_BITMAP = BitmapFactory.decodeFile(path);
    private static final int START_X = 10;
    private static final int START_Y = 15;
    private static final int WIDTH_PX = 100;
    private static final int HEIGHT_PX = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        // creating object of Loadingdialog class and passing MainActivity as argument
        final dialog loadingdialog = new dialog(MainActivity3.this);
        loadingdialog.startLoadingdialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // after 4 seconds
                loadingdialog.dismissdialog();

//                Intent intent = null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
//                    intent = new Intent(MainActivity.this, MainActivity2.class);
//                }
//                startActivity(intent);
            }
        }, 150); // 4 seconds



//
//// Crop bitmap
//        Bitmap newBitmap = Bitmap.createBitmap(SOURCE_BITMAP,START_X,START_Y,WIDTH_PX,HEIGHT_PX);
//
//// Assign new bitmap to ImageView
//        ImageView img = (ImageView) findViewById(R.id.imageView3);
//        img.setImageBitmap(newBitmap);
//        ImageView img = (ImageView) findViewById(R.id.imageView3);
//        img.setMaxWidth(30);
    }
}