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

public class NumbersActivity extends AppCompatActivity {
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
        words.add(new Word(R.drawable.number_one, R.raw.number_one,"one", "lutti"));
        words.add(new Word(R.drawable.number_two, R.raw.number_two,"two", "otiiko"));
        words.add(new Word(R.drawable.number_three, R.raw.number_three,"three", "tolookosu"));
        words.add(new Word(R.drawable.number_four, R.raw.number_four,"four", "oyyisa"));
        words.add(new Word(R.drawable.number_five, R.raw.number_five,"five", "massokka"));
        words.add(new Word(R.drawable.number_six, R.raw.number_six,"six", "temmokka"));
        words.add(new Word(R.drawable.number_seven, R.raw.number_seven,"seven", "kenekaku"));
        words.add(new Word(R.drawable.number_eight, R.raw.number_eight,"eight", "kawinta"));
        words.add(new Word(R.drawable.number_nine, R.raw.number_nine,"nine", "wo'e"));
        words.add(new Word(R.drawable.number_ten, R.raw.number_ten,"ten", "na'aacha"));
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);
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
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, current.getPronunciationResourceId());
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
