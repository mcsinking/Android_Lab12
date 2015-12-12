package com.android.mcsin.lab12;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Main2Activity extends Activity implements View.OnClickListener {

    private GameView gv;
    private Button shootButton;
    private MediaPlayer song2;

    private MediaPlayer coo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //gets rid of the title at the top of the app


        gv = new GameView(this);

        Button musicOnButton = new Button(this);
        musicOnButton.setBackgroundResource(R.drawable.volumeon);
        musicOnButton.setPadding(0, 0, 0, 10);
        musicOnButton.setOnClickListener(musicOnButtonListener);

        Button musicOffButton = new Button(this);
        musicOffButton.setBackgroundResource(R.drawable.volumeoff);
        musicOffButton.setPadding(0, 0, 0, 10);
        musicOffButton.setOnClickListener(musicOffButtonListener);

        LinearLayout musicVolumeButtonLayout = new LinearLayout(this);
        musicVolumeButtonLayout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        musicVolumeButtonLayout.addView(musicOnButton);
        musicVolumeButtonLayout.addView(musicOffButton);




        shootButton = new Button(this);
        shootButton.setWidth(350);
        shootButton.setHeight(100);
        shootButton.setBackgroundColor(Color.LTGRAY);
        shootButton.setTextColor(Color.RED);
        shootButton.setTextSize(20);
        shootButton.setBackgroundResource(R.drawable.aircraft);
        shootButton.setText("Shoot it!");



        DisplayMetrics dm=getResources().getDisplayMetrics();
        final int screenWidth=dm.widthPixels;
        final int screenHeight=dm.heightPixels-50;

        shootButton.setOnTouchListener(new View.OnTouchListener() {

            int lastX, lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int ea = event.getAction();
                Log.i("TAG", "Touch:" + ea);

                switch (ea) {
                    case MotionEvent.ACTION_DOWN:

                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;

                        int l = v.getLeft() + dx;
                        int b = v.getBottom() + dy;
                        int r = v.getRight() + dx;
                        int t = v.getTop() + dy;


                        if (l < 0) {
                            l = 0;
                            r = l + v.getWidth();
                        }

                        if (t < 0) {
                            t = 0;
                            b = t + v.getHeight();
                        }

                        if (r > screenWidth) {
                            r = screenWidth;
                            l = r - v.getWidth();
                        }

                        if (b > screenHeight) {
                            b = screenHeight;
                            t = b - v.getHeight();
                        }
                        v.layout(l, t, r, b);

                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        Toast.makeText(Main2Activity.this,
                                "Currently Position：" + l + "," + t + "," + r + "," + b,
                                Toast.LENGTH_SHORT).show();
                        v.postInvalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }


                return false;
            }
        });



        shootButton.setOnClickListener(this);
        shootButton.setGravity(Gravity.CENTER);

        FrameLayout GameLayout = new FrameLayout(this);
        LinearLayout ButtonLayout = new LinearLayout(this);
        ButtonLayout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

        ButtonLayout.addView(shootButton);

        GameLayout.addView(gv);
        GameLayout.addView(ButtonLayout);
        GameLayout.addView(musicVolumeButtonLayout);

        setContentView(GameLayout);


    }

    public View.OnClickListener musicOnButtonListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            if(!song2.isPlaying()) song2.start();
        }
    };

    public View.OnClickListener musicOffButtonListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            if(song2.isPlaying()) song2.pause();
        }
    };


    //backgroundsounds
    public void allocateSounds()
    {
        if(song2 == null)
            song2 = MediaPlayer.create(this.getBaseContext(), R.raw.airbattle_world3);
        song2.setVolume(0.2f, 0.2f);
        song2.setOnPreparedListener(song2PListener);
        song2.setOnCompletionListener(song2CListener);

        if(coo == null) coo = MediaPlayer.create(this.getBaseContext(), R.raw.short_lazer);
    }

    private MediaPlayer.OnPreparedListener song2PListener = new MediaPlayer.OnPreparedListener()
    {
        @Override
        public void onPrepared(MediaPlayer mp)
        {
            playSong2();
        }
    };

    private MediaPlayer.OnCompletionListener song2CListener = new MediaPlayer.OnCompletionListener()
    {
        @Override
        public void onCompletion(MediaPlayer mp)
        {
            playSong2();
        }
    };

    public void playSong2()
    {
        if (song2.isPlaying())
        {
            song2.pause();
        }

        if(song2.getCurrentPosition() != 1)
        {
            song2.seekTo(1);
        }
        song2.start();
    }

    public void playCoo()
    {
        if (!coo.isPlaying())
        {
            if(coo.getCurrentPosition() != 1)
            {
                coo.seekTo(1);
            }
            coo.start();
        }
    }

    public void deallocateSounds()
    {

        if (song2.isPlaying())
        {
            song2.stop();
        }

        if (coo.isPlaying())
        {
            coo.stop();
        }


        if (!(song2 == null))
        {
            song2.release();
            song2 = null;
        }

        if (!(coo == null))
        {
            coo.release();
            coo = null;
        }


        song2CListener = null;
        song2PListener = null;
    }












    @Override
    protected void onPause()
    {
        SharedPreferences prefs = getSharedPreferences("AirBattleData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String tempscore=Integer.toString(gv.getScore());
        String tempmovespeed=String.valueOf(gv.getMoveSpeed());
        String templv=Integer.toString(gv.getLvNo());
        editor.putString("AirBattleScore", tempscore);
        editor.putString("AirBattleMoveSpeed", tempmovespeed);
        editor.putString("AirBattlelv", templv);

        editor.commit();

        deallocateSounds();
        super.onPause();
        gv.killThread(); //Notice this reaches into the GameView object and runs the killThread mehtod.
    }

    @Override
    protected void onResume()
    {

        SharedPreferences prefs = getSharedPreferences("AirBattleData",MODE_PRIVATE);
        String retrievedHighScore = prefs.getString("AirBattleScore", "0");
        String retrievedMoveSpeed = prefs.getString("AirBattleMoveSpeed", "3.0f");
        String retrievedlv = prefs.getString("AirBattlelv", "0");

        gv.setScore(Integer.valueOf(retrievedHighScore));
        gv.setMoveSpeed(Float.parseFloat(retrievedMoveSpeed));
        gv.setLvNo(Integer.valueOf(retrievedlv));

        super.onResume();

        allocateSounds();
    }



    @Override
    protected void onDestroy() {

//        SharedPreferences prefs = getSharedPreferences("AirBattleData", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//
//        String tempscore=Integer.toString(0);
//        String tempmovespeed=String.valueOf(0);
//        String templv=Integer.toString(0);
//        editor.putString("AirBattleScore", tempscore);
//        editor.putString("AirBattleMoveSpeed", tempmovespeed);
//        editor.putString("AirBattlelv", templv);
//
//        editor.commit();

        super.onDestroy();
        gv.onDestroy();
    }

    @Override
    public void onClick(View v) {
        playCoo();

        //if (gv.isBulletYActive()==false){
        if ((System.currentTimeMillis()>(gv.getFireTimer()+650))||gv.getFireTimer()==0)
        gv.fire(shootButton.getX(), shootButton.getY(), shootButton.getWidth(), shootButton.getHeight());
        //}
    }



    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        playCoo();

        float touchedX = e.getX();
        float touchedY = e.getY();
        if (touchedX>shootButton.getX()+50.0f)
        shootButton.setX(shootButton.getX()+5.0f);
        if (touchedX<shootButton.getX()+50.0f)
            shootButton.setX(shootButton.getX() - 5.0f);

        if (touchedY>shootButton.getY()+25.0f)
            shootButton.setY(shootButton.getY() + 5.0f);
        if (touchedY<shootButton.getY()+25.0f)
            shootButton.setY(shootButton.getY() - 5.0f);



      // if (gv.isBulletYActive()==false){
            if ((System.currentTimeMillis()>(gv.getFireTimer()+650))||gv.getFireTimer()==0)

            gv.fire(shootButton.getX(), shootButton.getY(), shootButton.getWidth(), shootButton.getHeight());
      //  }

        return true;
    }
}
