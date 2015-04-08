package com.example.sankalp.snapdealassignment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.TextView;


public class SingleItemView extends Activity{

    // Declare Variables
    String name;
    String placeReference;
    ImageLoader imageLoader = new ImageLoader(this);
    ScaleGestureDetector sgd;
    Matrix matrix;
    ImageView placeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemview);
        Intent i = getIntent();
      //  name = i.getStringExtra("name");
        placeReference = i.getStringExtra("photo_reference");

       // TextView txtName = (TextView) findViewById(R.id.placeName);
        placeImage = (ImageView) findViewById(R.id.placeImage);

        // Set results to the TextViews
    //    txtName.setText(name);
        imageLoader.DisplayImage(placeReference, placeImage);

        matrix =new Matrix();
         sgd = new ScaleGestureDetector(this,new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        sgd.onTouchEvent(event);
        return true;
    }

    private  class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{


        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            float sf = detector.getScaleFactor();
            sf =Math.max(0.5f,Math.min(sf,5.0f));
            matrix.setScale(sf,sf);
            placeImage.setImageMatrix(matrix);

            return true;
        }
    }



}
