package com.example.android.miwok;

public class Word {
    //defining the variables
    private String mMiwokTranslation;
    private String mDefaultTranslation;
    private int mPronunciationResourceId;
    private final int IMAGE_RESOURCE_ID = -1;
    private int mImageResourceId = IMAGE_RESOURCE_ID;

    //constructor
    public Word(int imageResourceId, int pronunciationResourceId, String defaultTranslation, String miwokTranslation) {
        mImageResourceId = imageResourceId;
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mPronunciationResourceId = pronunciationResourceId;
    }

    public Word(int pronunciationResourceId, String defaultTranslation, String miwokTranslation) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mPronunciationResourceId = pronunciationResourceId;
    }

    //methods that can be applied
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getPronunciationResourceId() {
        return mPronunciationResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != IMAGE_RESOURCE_ID;
    }
}
