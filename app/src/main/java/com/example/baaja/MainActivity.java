package com.example.baaja;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteController;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mPlayer=null;
    AudioManager audioManager;
    ImageButton play;
    ImageButton pause;
    ImageButton replay;
    View loop;
    TextView text;
    AudioManager.OnAudioFocusChangeListener listener= new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_GAIN){
                mPlayer.start();
            }
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
                    //audioManager.unregisterMediaButtonEventReceiver(RemoteControlReceiver);
                    //audioManager.abandonAudioFocus(listener)
                mPlayer.pause();
            }
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
                mPlayer.pause();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=(TextView)findViewById(R.id.textView);
        audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result=audioManager.requestAudioFocus(listener,audioManager.STREAM_MUSIC,audioManager.AUDIOFOCUS_GAIN);
        if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
            //audioManager.registerMediaButtonEventReceiver(audioManager.RemoteControlReceiver);
            mPlayer=MediaPlayer.create(MainActivity.this,R.raw.dil_tod_ke);
        }
        //mPlayer=MediaPlayer.create(MainActivity.this,R.raw.dil_tod_ke);
        text.setText((new Song("Dil Tode ke","B Praak","Mrjatt.com")).toString());
        play=findViewById(R.id.play_btn);
        pause=findViewById(R.id.pause_btn);
        loop=findViewById(R.id.looping_btn);
        pause.setEnabled(false);
        replay=findViewById(R.id.replay_btn);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mPlayer.isLooping()==false)
                releasePlayer();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.start();
                pause.setEnabled(true);
            }
        });
        mPlayer.setLooping(false);
        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlayer.isLooping()==true)
                {
                    mPlayer.setLooping(false);
                    Toast.makeText(MainActivity.this,"Loop OFF",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mPlayer.setLooping(true);
                    Toast.makeText(MainActivity.this,"Loop ON",Toast.LENGTH_SHORT).show();
                }

            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.pause();
                pause.setEnabled(false);
            }
        });
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlayer.isPlaying()==false)
                {
                    mPlayer.stop();
                    try {
                        mPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    pause.setEnabled(true);
                    mPlayer.start();
                }
                else
                    mPlayer.seekTo(0);
            }
        });
    }
    public void releasePlayer(){
        if(mPlayer!=null) {
            mPlayer.release();
            audioManager.abandonAudioFocus(listener);
        }

    }

    /*@Override
    protected void onStop() {
        super.onStop();
        mPlayer.pause();
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        if(mPlayer.isPlaying()==true)
            pause.setEnabled(true);
        else
            pause.setEnabled(false);
    }
    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }*/
}