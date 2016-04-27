package com.example.maya.googlemap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import GPS.CustomGPS;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity  {

    private  final String LOG_LAG="TAG";

    private TextView textview;


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview=(TextView)findViewById(R.id.hello);

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                textview.setText(s);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) { }
        };
        CustomGPS customGPS=new CustomGPS(MainActivity.this,mySubscriber);

    }

}
