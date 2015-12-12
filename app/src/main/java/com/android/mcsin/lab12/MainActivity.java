package com.android.mcsin.lab12;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private MediaPlayer song1;

    private Button startButton;

    private Button loadGame;

    private Button TopScore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton=(Button)findViewById(R.id.bn);
        loadGame=(Button)findViewById(R.id.lg);
        TopScore=(Button)findViewById(R.id.ts);

        loadGame.setOnClickListener(btnOnClickListener);
        TopScore.setOnClickListener(btnOnClickListener);



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//                startActivityForResult(intent, 0);
                startActivity(intent);
            }
        });
    }

    Button.OnClickListener btnOnClickListener
            = new Button.OnClickListener(){


        @Override
        public void onClick(View v) {

            Fragment newFragment = null;

            if (v==loadGame){
                newFragment = new MyFragment1();
            }else if (v==TopScore){
                newFragment = new MyFragment2();

                ArrayList<String> stringList = new ArrayList<String>();
                stringList.add("Top Score:");
                stringList.add("No.1");
                stringList.add("No.2");


                Bundle bundle = new Bundle();
                bundle.putStringArrayList("theList", stringList);
                newFragment.setArguments(bundle);
            }

            // Create new transaction
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.myfragment, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();

        }
    };

    Button.OnClickListener loadGameOnClickListener
            = new Button.OnClickListener(){


        @Override
        public void onClick(View v) {

        }
    };

    public void allocateSong1()
    {
        if(song1 == null)
            song1 = MediaPlayer.create(this.getBaseContext(), R.raw.menu);
        song1.setOnPreparedListener(song1PListener);
        song1.setOnCompletionListener(song1CListener);
    }

    private MediaPlayer.OnPreparedListener song1PListener = new MediaPlayer.OnPreparedListener()
    {
        @Override
        public void onPrepared(MediaPlayer mp)
        {
            playSong1();
        }
    };

    private MediaPlayer.OnCompletionListener song1CListener = new MediaPlayer.OnCompletionListener()
    {
        @Override
        public void onCompletion(MediaPlayer mp)
        {
            playSong1();
        }
    };

    public void playSong1()
    {
        if (song1.isPlaying())
        {
            song1.pause();
        }
        if(song1.getCurrentPosition() != 1)
        {
            song1.seekTo(1);
        }
        song1.start();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        allocateSong1();
    }


    @Override
    protected void onPause()
    {
        deallocateSong1();
        super.onPause();
    }

    public void deallocateSong1()
    {

        if (song1.isPlaying())
        {
            song1.stop();
        }
        if (!(song1 == null))
        {
            song1.release();
            song1 = null;
        }
        song1CListener = null;
        song1PListener = null;
    }






}
