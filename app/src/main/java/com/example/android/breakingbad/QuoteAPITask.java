package com.example.android.breakingbad;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

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

//public class QuoteAPITask extends AsyncTask<String, Void, List<String>> {
//    private final String TAG = this.getClass().getSimpleName();
//
//    private final String JSON_CHARACTER_QUOTES = "quote: ";
//
//
//    private TextView quoteTextView;
//
//    @Override
//    protected List<String> doInBackground(String... params) {
//        Log.d(TAG, "doInBackground() called with: strings = [" + params + "]");
//        if (params.length == 0) {
//            return null;
//        }
//        String quoteURL = params[0];
//        URL url = null;
//        HttpURLConnection urlConnection = null;
//        try {
//            url = new URL(quoteURL);
//
//            urlConnection = (HttpURLConnection) url.openConnection();
//            InputStream in = urlConnection.getInputStream();
//
//            Scanner scanner = new Scanner(in);
//            scanner.useDelimiter("\\A");
//
//            boolean hasInput = scanner.hasNext();
//            if (hasInput) {
//                String response = scanner.next();
//
//                ArrayList<String> resultList = convertJsonToArrayList(response);
//                Log.d(TAG, "doInBackground: result" + response);
//                return resultList;
//            } else {
//                return null;
//            }
//        } catch (MalformedURLException e) {
//            Log.e(TAG, "doInBackground: ", e);
//            e.printStackTrace();
//        } catch (IOException e) {
//            Log.e(TAG, "doInBackground: ", e);
//            e.printStackTrace();
//        } finally {
//            if (urlConnection != null) {
//                Log.i(TAG, "doInBackground: disconnect");
//                urlConnection.disconnect();
//            }
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(List<String> strings) {
//        super.onPostExecute(strings);
//        Log.d(TAG, "onPostExecute() called with: strings = [" + strings + "]");
//        quoteTextView = fin
//
//    }
//
//    private ArrayList<String> convertJsonToArrayList(String response) {
//        Log.d(TAG, "convertJsonToArrayList() called with: response = [" + response + "]");
//        ArrayList<String> list = new ArrayList<>();
//        try {
//            JSONArray jsonArray = new JSONArray(response);
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject j = (JSONObject) jsonArray.get(i);
//                list.add(j.getString(JSON_CHARACTER_QUOTES));
//            }
//        } catch (JSONException e) {
//            Log.e(TAG, "convertJsonToArrayList: ", e);
//            e.printStackTrace();
//        }
//        Log.d(TAG, "convertJsonToArrayList: returning " + list.size() + " items");
//        return list;
//    }


//    public URL buildURL(String query) {
////        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
////                .appendQueryParameter(PARAM_QUERY, githubSearchQuery)
////                .appendQueryParameter(PARAM_SORT, sortBy)
////                .build();
////
////        URL url = null;
////        try {
////            url = new URL(builtUri.toString());
////        } catch (MalformedURLException e) {
////            e.printStackTrace();
////        }
////
////        return url;
//        return null;
//    }
//}
