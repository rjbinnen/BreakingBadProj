package com.example.android.breakingbad;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CharacterAPITask extends AsyncTask<String, Void, List<Character>> {
    private final String TAG = CharacterAPITask.class.getSimpleName();
    private final String JSON_CHARACTER_NAME = "name";
    private final String JSON_CHARACTER_NICKNAME = "nickname";
    private final String JSON_CHARACTER_IMG = "img";
    private final String JSON_CHARACTER_STATUS = "status";
    private final String JSON_CHARACTER_BIRTHDAY = "birthday";
    private final String JSON_CHARACTER_OCCUPATION = "occupation";
    private final String JSON_CHARACTER_APPEARANCE = "appearance";

    private CharacterListener listener = null;
    private Context context;

    public CharacterAPITask(CharacterListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected List<Character> doInBackground(String... params) {
        Log.d(TAG, "doInBackground() called with: params = [" + params + "]");
        if (params.length == 0) {
            return null;
        }
        String characterUrl = params[0];
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(characterUrl);

            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String response = scanner.next();

                ArrayList<Character> resultList = convertJsonToArrayList(response);
                Log.d(TAG, "doInBackground: result" + response);
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

    private ArrayList<Character> convertJsonToArrayList(String response) {
        Log.d(TAG, "convertJsonToArrayList() called with: response = [" + response + "]");
        ArrayList<Character> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject j = (JSONObject) jsonArray.get(i);
                JSONArray appearances = j.getJSONArray(JSON_CHARACTER_APPEARANCE);
                int[] appearanceList = new int[appearances.length()];
                for (int k = 0; k < appearances.length(); k++) {
                    appearanceList[k] = (int) appearances.get(k);
                }
                JSONArray occupations = j.getJSONArray(JSON_CHARACTER_OCCUPATION);
                String[] occupationList = new String[occupations.length()];
                for (int k = 0; k < occupations.length(); k++) {
                    occupationList[k] = (String) occupations.get(k);
                }
                list.add(new Character(j.getString(JSON_CHARACTER_NAME), j.getString(JSON_CHARACTER_NICKNAME), j.getString(JSON_CHARACTER_IMG), j.getString(JSON_CHARACTER_STATUS), j.getString(JSON_CHARACTER_BIRTHDAY), occupationList, appearanceList));
            }
        } catch (JSONException e) {
            Log.e(TAG, "convertJsonToArrayList: ", e);
            e.printStackTrace();
        }
        Log.d(TAG, "convertJsonToArrayList: returning " + list.size() + " items");
        return list;
    }

    @Override
    protected void onPostExecute(List<Character> characters) {
        Log.d(TAG, "onPostExecute() called with: characters = [" + characters + "]");
        super.onPostExecute(characters);
        Log.d(TAG, "onPostExecute: list size " + characters.size());
        Toast.makeText(this.context, "Loaded items: " + characters.size(), Toast.LENGTH_LONG).show();
        listener.onCharactersAvailable(characters);
    }


    public interface CharacterListener {
        void onCharactersAvailable(List<Character> characters);
    }
}
