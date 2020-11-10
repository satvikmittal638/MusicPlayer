package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    SeekBar seekVol,seekProg;
    AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player=MediaPlayer.create(this,R.raw.test);
        seekVol=findViewById(R.id.seekVol);
        seekProg=findViewById(R.id.seekProg);
        final Button play=findViewById(R.id.play),
                pause=findViewById(R.id.pause),
                stop=findViewById(R.id.stop);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
            }
        });


        audioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);//declaring audiomanager object
        int maxVol=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//to get the max volume of music in player
        int curVol=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//to get current volume of device/music
        seekVol.setMax(maxVol);//to set max volume
        seekVol.setProgress(curVol);//to set current device volume
        seekVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(audioManager.STREAM_MUSIC,progress,0);//to adjust the volume
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekProg.setMax(player.getDuration());
        new Timer().scheduleAtFixedRate(new TimerTask() {//to change the position of seekbar automatically
            @Override
            public void run() {
                seekProg.setProgress(player.getCurrentPosition());//set the progress of seekbar to current time of the music in player
            }
        },0,100);//'period' means after which time it will update the seekbar
        //adjusting the music
        seekProg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                player.seekTo(progress);//to adjust the music
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}