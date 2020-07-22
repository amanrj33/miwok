package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if (i==AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }
            else if (i==AudioManager.AUDIOFOCUS_LOSS){
                mediaPlayer.release();
            }
        }
    };
    MediaPlayer.OnCompletionListener mOnCompletionListener= new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.release();
            mediaPlayer=null;
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        setContentView(R.layout.word_list);
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word(R.drawable.color_red, R.raw.color_red,"red", "weṭeṭṭi"));
        words.add(new Word(R.drawable.color_green, R.raw.color_green,"green", "chokokki"));
        words.add(new Word(R.drawable.color_brown, R.raw.color_brown,"brown", "ṭakaakki"));
        words.add(new Word(R.drawable.color_gray, R.raw.color_gray,"gray", "ṭopoppi"));
        words.add(new Word(R.drawable.color_black, R.raw.color_black,"black", "kululli"));
        words.add(new Word(R.drawable.color_white, R.raw.color_white,"white", "kelelli"));
        words.add(new Word(R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow,"dusty yellow", "ṭopiisә"));
        words.add(new Word(R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow,"mustard yellow", "chiwiiṭә"));
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word current = words.get(i);
                int result = audioManager.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, current.getPronunciationResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer!=null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        audioManager.abandonAudioFocus(audioFocusChangeListener);
    }
}









