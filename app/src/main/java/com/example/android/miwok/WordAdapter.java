package com.example.android.miwok;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResourceId;

    //constructor
    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceId) {
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Word currentWord = getItem(position);
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        ImageView imageView = listItemView.findViewById(R.id.image);
        if (currentWord.hasImage())
            imageView.setImageResource(currentWord.getImageResourceId());
        else
            imageView.setVisibility(View.GONE);
        TextView miwok_textView = listItemView.findViewById(R.id.default_textView);
        miwok_textView.setText(currentWord.getDefaultTranslation());
        TextView default_textView = listItemView.findViewById(R.id.miwok_textView);
        default_textView.setText(currentWord.getMiwokTranslation());
        View textContainer = listItemView.findViewById(R.id.list_item_layout);
        int color = ContextCompat.getColor(getContext(),mColorResourceId);
        textContainer.setBackgroundColor(color);
        return listItemView;
    }
}
