package com.example.android.breakingbad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class ImageActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mImageView = findViewById(R.id.iv_full_image);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_INDEX)) {

            Serializable serializable = intentThatStartedThisActivity.getSerializableExtra(Intent.EXTRA_INDEX);
            Log.d(TAG, "onCreate: " + serializable);
            Character c = (Character) serializable;

            Picasso.get().load(c.getImage()).into(mImageView);
        }
    }
}
