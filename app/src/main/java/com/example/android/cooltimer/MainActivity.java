package com.example.android.cooltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView timeTextView;
    private Button buttonTime;
    private SeekBar seekBar;
    private boolean isTimerOn;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeTextView = findViewById(R.id.textView);
        buttonTime = findViewById(R.id.button);
        seekBar = findViewById(R.id.seekBar);

        isTimerOn = true;

        seekBar.setMax(60);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                long progress = i * 1000;
                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    public void startTime(View view) {

        if (isTimerOn) {
            buttonTime.setText("Stop");
            seekBar.setEnabled(false);
            isTimerOn = false;

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 100) {
                @Override
                public void onTick(long l) {
                    updateTimer(l);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell_sound);
                    mediaPlayer.start();
                    resetTimer();
                }
            };
            countDownTimer.start();

        } else {
            resetTimer();
        }


//            /* Создаем таймер обратного отсчета */
//
//            CountDownTimer myTimer = new CountDownTimer(60000, 1000) {
//
//                // первый - параметр сколько времени будет исполнятся код / второй - интервал с какой чистотой таймер будет работать
//
//                @Override
//                public void onTick(long l) {
//                    timeTextView.setText("00:" + String.valueOf(l / 1000));
//                    // делим на 1000 чтобы получить количество секунд
//                }
//
//                @Override
//                public void onFinish() {
//                }
//            };

//            myTimer.start();

        /* Второй вариант таймера */

//      final Handler handler = new Handler(); // таймер
//
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                Log.d("time", "time end");
//                handler.postDelayed(this, 2000);
//            }
//        };
//
//        handler.post(runnable);

    }

    private void updateTimer(long l) {

        int minutes = (int) l / 1000 / 60;
        int seconds = (int) l / 1000 - (minutes * 60);

        String minutesString = "";
        String secondsString = "";

        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = String.valueOf(minutes);
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = String.valueOf(seconds);
        }

        timeTextView.setText(minutesString + ":" + secondsString);
    }

    private void resetTimer() {
        countDownTimer.cancel();
        buttonTime.setText("Start");
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
        timeTextView.setText("00:30");
        isTimerOn = true;
    }
}
