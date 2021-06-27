package com.example.android.breakingbad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static android.widget.GridLayout.HORIZONTAL;

public class MainActivity extends AppCompatActivity implements CharacterAPITask.CharacterListener, CharacterOnClickHandler {
    private final String TAG = MainActivity.class.getSimpleName();

    private static final String CHARACTERS_SAVE_INSTANCE_KEY = "Characters";

    private RecyclerView mCharacterListRecyclerView;
    private CharacterAdapter characterAdapter;
    private ArrayList<Character> characters = new ArrayList<>();
    private Instant dateBeforePause = Instant.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCharacterListRecyclerView = findViewById(R.id.rv_characterList);

        if (savedInstanceState != null) {
            Log.d(TAG, "onCreate: if statement called");
            characters.clear();
            this.characters = (ArrayList<Character>)savedInstanceState.getSerializable(CHARACTERS_SAVE_INSTANCE_KEY);
            characterAdapter = new CharacterAdapter(characters, this);
            characterAdapter.notifyDataSetChanged();
            mCharacterListRecyclerView.setAdapter(characterAdapter);
            GridLayoutManager layoutManager = new GridLayoutManager(this, getResources().getConfiguration().orientation);
            mCharacterListRecyclerView.setLayoutManager(layoutManager);
        } else {
            Log.d(TAG, "onCreate: else statement called");
            characters = new ArrayList<>();

            GridLayoutManager layoutManager = new GridLayoutManager(this, getResources().getConfiguration().orientation);
            mCharacterListRecyclerView.setLayoutManager(layoutManager);
            characterAdapter = new CharacterAdapter(characters, this);
            mCharacterListRecyclerView.setAdapter(characterAdapter);

            CharacterAPITask apiTask = new CharacterAPITask(this);
            String[] params = {"https://www.breakingbadapi.com/api/characters/"};
            apiTask.execute(params);
        }
    }

    @Override
    public void onCharactersAvailable(List<Character> characters) {
        Log.d(TAG, "onCharactersAvailable: characters size: " + characters.size());
        this.characters.clear();
        this.characters.addAll(characters);
        this.characterAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCharacterClick(View view, int itemIndex) {
        Log.d(TAG, "onCharacterClick() called with: view = [" + view + "], itemIndex = [" + itemIndex + "]");
        Context context = this;

        if (view.getId() == R.id.imageView || view.getId() == R.id.tv_character_item_name || view.getId() == R.id.tv_character_item_nickname) {
            Class destinationActivity = ImageActivity.class;
            Intent startDestinationActivityIntent = new Intent(context, destinationActivity);

            startDestinationActivityIntent.putExtra(Intent.EXTRA_INDEX, characters.get(itemIndex));
            startActivity(startDestinationActivityIntent);
        } else {
            Class destinationActivity = CharacterActivity.class;
            Intent startDestinationActivityIntent = new Intent(context, destinationActivity);

            startDestinationActivityIntent.putExtra(Intent.EXTRA_INDEX, characters.get(itemIndex));

            startActivity(startDestinationActivityIntent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dateBeforePause = Instant.now();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Duration diff = Duration.between(dateBeforePause, Instant.now());
        Toast.makeText(this, "Screen paused for " + diff.getSeconds() + " seconds", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CHARACTERS_SAVE_INSTANCE_KEY, characters);
    }
}