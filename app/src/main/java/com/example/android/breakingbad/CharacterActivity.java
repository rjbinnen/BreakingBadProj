package com.example.android.breakingbad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class CharacterActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private TextView mNameTextView;
    private TextView mNicknameTextView;
    private ImageView mImageView;
    private TextView mStatusTextView;
    private TextView mBirthdayTextView;
    private TextView mOccupationsTextView;
    private TextView mAppearancesTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        setContentView(R.layout.activity_character_detail);

        mNameTextView = findViewById(R.id.tv_detail_item_name);
        mNicknameTextView = findViewById(R.id.tv_detail_item_nickname);
        mImageView = findViewById(R.id.iv_detail_item_image);
        mStatusTextView = findViewById(R.id.tv_detail_item_status);
        mBirthdayTextView = findViewById(R.id.tv_detail_item_birthday);
        mOccupationsTextView = findViewById(R.id.tv_detail_item_occupations);
        mAppearancesTextView = findViewById(R.id.tv_detail_item_appearances);


        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_INDEX)) {

            Serializable serializable = intentThatStartedThisActivity.getSerializableExtra(Intent.EXTRA_INDEX);
            Log.d(TAG, "onCreate: " + serializable);
            Character c = (Character) serializable;
            Log.d(TAG, "onCreate: " +c.getName());

            mNameTextView.setText(c.getName());
            mNicknameTextView.setText(c.getNickName());
            Picasso.get().load(c.getImage()).into(mImageView);
            mStatusTextView.setText(c.getStatus());
            mBirthdayTextView.setText(c.getBirthday());
            for (int i = 0; i < c.getOccupations().length; i++) {
                mOccupationsTextView.append(c.getOccupations()[i]);
            }
            for (int i = 0; i < c.getAppearance().length; i++) {
                mAppearancesTextView.append(String.valueOf(c.getAppearance()[i]));
            }
        }

    }
}
