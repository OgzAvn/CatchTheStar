package com.oguz.catchthestar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timeTextView;
    TextView scoreTextView;
    int score ;
    ImageView imageView;
    LinearLayout linearLayout;
    Random random;

    Handler handler;
    Runnable randomImagerunnable;

    Runnable timerRunnable;

    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializing();


        randomImagerunnable = new Runnable() {
            @Override
            public void run() {
                randomImage();
                handler.postDelayed(randomImagerunnable,500);
            }

        };

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                new CountDownTimer(10000,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {
                        timeTextView.setText("Time :" + millisUntilFinished/1000);
                    }

                    @Override
                    public void onFinish() {
                        showAlertDialog();
                    }
                }.start();
            }

        };

        handler.post(randomImagerunnable);
        handler.post(timerRunnable);

    }

    public void initializing(){
        timeTextView = findViewById(R.id.timeTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        imageView = findViewById(R.id.imageView);
        linearLayout = findViewById(R.id.linearLayout);
        random = new Random();
        handler = new Handler();
        alertDialog = new AlertDialog.Builder(MainActivity.this);
        score = 0;
    }

    public void imageClicked(View view){
        score++;
        scoreTextView.setText("Score : " + score);
    }

    public void showAlertDialog(){

        alertDialog.setTitle("Restart");
        alertDialog.setMessage("Are you sure to restart game?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.removeCallbacks(randomImagerunnable);
                handler.removeCallbacks(timerRunnable);
                score = 0;
                scoreTextView.setText("Score:" + score);
                handler.post(timerRunnable);
                handler.post(randomImagerunnable);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.removeCallbacks(randomImagerunnable);
                handler.removeCallbacks(timerRunnable);
                imageView.setEnabled(false);
            }
        });

        alertDialog.show();

    }

    public void randomImage(){

        int emptySpaceX = linearLayout.getWidth();
        int emptySpaceY = linearLayout.getHeight();

        int maxX = emptySpaceX - imageView.getWidth();
        int maxY = emptySpaceY - imageView.getHeight();

        int randomX = random.nextInt(maxX);
        int randomY = random.nextInt(maxY);

        imageView.setX(randomX);
        imageView.setY(randomY);


    }
}