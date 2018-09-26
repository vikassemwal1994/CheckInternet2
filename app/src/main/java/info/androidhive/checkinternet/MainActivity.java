package info.androidhive.checkinternet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener {

    private FloatingActionButton fab;
    private Button btnCheck, btn_startTimer;
    CustomDialogClass customDialogClass;
    TextView tv_timer;
    long starttime = 0;
    CountDownTimer ct;

    final Handler h = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            long millis = System.currentTimeMillis() - starttime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds     = seconds % 60;

            tv_timer.setText(String.format("%d:%02d", minutes, seconds));
            return false;
        }
    });
    //runs without timer be reposting self
    Handler h2 = new Handler();
    Runnable run = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - starttime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds     = seconds % 60;

            tv_timer.setText(String.format("%d:%02d", minutes, seconds));

            h2.postDelayed(this, 500);
        }
    };

    //tells handler to send a message
    class firstTask extends TimerTask {

        @Override
        public void run() {
            h.sendEmptyMessage(0);
        }
    };

    //tells activity to run on ui thread
    class secondTask extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    long millis = System.currentTimeMillis() - starttime;
                    int seconds = (int) (millis / 1000);
                    int minutes = seconds / 60;
                    seconds     = seconds % 60;

                    tv_timer.setText(String.format("%d:%02d", minutes, seconds));
                }
            });
        }
    };

    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        customDialogClass = new CustomDialogClass(MainActivity.this,"");
        customDialogClass.setCanceledOnTouchOutside(false);
        customDialogClass.setCancelable(false);
//        customDialogClass.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        customDialogClass.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customDialogClass.requestWindowFeature(Window.FEATURE_NO_TITLE);

        tv_timer = (TextView) findViewById(R.id.tv_timer);
        btnCheck = (Button) findViewById(R.id.btn_check);
        btn_startTimer = (Button) findViewById(R.id.btn_startTimer);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Manually checking internet connection
        checkConnection();


        btn_startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_startTimer.getText().equals("stop")){
                    ct.cancel();

                }else{

                    ct = new CountDownTimer(180000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            long millis = millisUntilFinished;
                            int seconds = (int) (millis / 1000);
                            int minutes = seconds / 60;
                            seconds     = seconds % 60;
                            tv_timer.setText(String.format("%d:%02d", minutes, seconds));
                            //here you can have your logic to set text to edittext
                        }

                        public void onFinish() {
                            tv_timer.setText("done!");
                        }

                    }.start();

                }
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        super.onPause();
        timer.cancel();
        timer.purge();
        h2.removeCallbacks(run);
        btn_startTimer.setText("start");
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {

        if (isConnected) {
            customDialogClass.cancel();
        } else {
            customDialogClass.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
