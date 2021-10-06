package com.example.filedownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button startButton,button2;
    TextView totext;
    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        button2=findViewById(R.id.button2);
        totext=findViewById(R.id.totext);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload(v);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopDownload(v);
            }
        });
    }

    public void mockFileDownloader(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Downloading...");
            }
        });

        for(int downloadProgress = 0; downloadProgress <= 100; downloadProgress=downloadProgress+10){
            if(stopThread){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText("start");
                        totext.setText("");
                    }
                });
                return;
            }
            int finalDownloadProgress = downloadProgress;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    totext.setText("Download Progress: "+ finalDownloadProgress +"%");
                }
            });
            Log.d(TAG,"Download Progress: "+downloadProgress+"%");

            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("start");
                totext.setText("");
            }
        });
    }

    class ExampleRunnable implements Runnable{
        @Override
        public void run(){
            mockFileDownloader();
        }
    }

    public void startDownload(View view){
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }
    public void stopDownload(View view){
        stopThread = true;
    }
}