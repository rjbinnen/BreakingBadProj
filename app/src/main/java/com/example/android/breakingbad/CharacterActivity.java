package com.example.android.breakingbad;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CharacterActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private final String CHARACTER_QUOTE_INSTANCE_KEY = "Character quotes";

    private TextView mNameTextView;
    private TextView mNicknameTextView;
    private ImageView mImageView;
    private TextView mStatusTextView;
    private TextView mBirthdayTextView;
    private TextView mOccupationsTextView;
    private TextView mAppearancesTextView;
    private TextView mQuotesTextView;

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
        mQuotesTextView = findViewById(R.id.tv_detail_item_quotes);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_INDEX)) {

            Serializable serializable = intentThatStartedThisActivity.getSerializableExtra(Intent.EXTRA_INDEX);
            Log.d(TAG, "onCreate: " + serializable);
            Character c = (Character) serializable;
            Log.d(TAG, "onCreate: " + c.getName());

            mNameTextView.append(" " + c.getName());
            mNicknameTextView.append(" " + c.getNickName());
            Picasso.get().load(c.getImage()).into(mImageView);
            mStatusTextView.append(" " + c.getStatus());
            mBirthdayTextView.append(" " + c.getBirthday());
            mOccupationsTextView.append(" ");
            for (int i = 0; i < c.getOccupations().length - 1; i++) {
                mOccupationsTextView.append(c.getOccupations()[i] + "\n");
            }
            mOccupationsTextView.append(c.getOccupations()[c.getOccupations().length - 1]);
            mAppearancesTextView.append(" ");
            for (int i = 0; i < c.getAppearance().length; i++) {
                mAppearancesTextView.append("Season " + (c.getAppearance()[i]) + "\n");
            }

            if (savedInstanceState != null) {
                mQuotesTextView.setText(savedInstanceState.getString(CHARACTER_QUOTE_INSTANCE_KEY));
            } else {

                QuoteAPITask apiTask = new QuoteAPITask();
                String[] params = {"https://www.breakingbadapi.com/api/quote?author="};
                String st = c.getName();
                String newName = st.replaceAll("\\s", "+");
                params[0] += newName;
                Log.d(TAG, "onCreate: Parameter string is" + params[0]);
                apiTask.execute(params);
            }
        }
    }

    public class QuoteAPITask extends AsyncTask<String, Void, List<String>> {
        private final String TAG = this.getClass().getSimpleName();

        private final String JSON_CHARACTER_QUOTES = "quote";

        @Override
        protected List<String> doInBackground(String... params) {
            Log.d(TAG, "doInBackground() called with: strings = [" + params + "]");
            if (params.length == 0) {
                return null;
            }
            String quoteURL = params[0];
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(quoteURL);

                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    String response = scanner.next();

                    ArrayList<String> resultList = convertJsonToArrayList(response);
                    Log.d(TAG, "doInBackground: result" + response);
                    Log.d(TAG, "doInBackground: result list is" + resultList);
                    return resultList;
                } else {
                    return null;
                }
            } catch (MalformedURLException e) {
                Log.e(TAG, "doInBackground: ", e);
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: ", e);
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    Log.i(TAG, "doInBackground: disconnect");
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            Log.d(TAG, "onPostExecute() called with: strings = [" + strings + "]");
            for (int i = 0; i < strings.size(); i++) {
                mQuotesTextView.append(strings.get(i) + "\n");
            }
        }

        private ArrayList<String> convertJsonToArrayList(String response) {
            Log.d(TAG, "convertJsonToArrayList() called with: response = [" + response + "]");
            ArrayList<String> list = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject j = (JSONObject) jsonArray.get(i);
                    list.add(j.getString(JSON_CHARACTER_QUOTES));
                }
            } catch (JSONException e) {
                Log.e(TAG, "convertJsonToArrayList: ", e);
                e.printStackTrace();
            }
            Log.d(TAG, "convertJsonToArrayList: returning " + list.size() + " items");
            return list;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CHARACTER_QUOTE_INSTANCE_KEY, mQuotesTextView.getText().toString());
    }
}
