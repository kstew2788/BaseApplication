package com.triviumtechnology.baseapplication.NetworkUtilities;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.triviumtechnology.baseapplication.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Internet {

    private final static String BASE_URL = "https://api.github.com/search/repositories";
    private final static String PARAM_QUERY = "q";
    private final static String PARAM_SORT = "sort";
    private final static String SORTBY = "stars";

   void Internet(){
       Log.i("Internet.java","Internet.java Constructor");
   }
    private Uri BuildUri(String query){

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY,query)
                .appendQueryParameter(PARAM_SORT,SORTBY)
                .build();
        return builtUri;
    }

    public URL BuildURL(String query){
        URL url = null;

        try{
            url = new URL(BuildUri(query).toString());
        }catch(MalformedURLException e){
            Log.v("internet.java","BuildURL: Shite URL");
            e.printStackTrace();
        }

        return url;
    }

    private static String getResponseFromHttpURL(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }
    public static class HttpQueryTask extends AsyncTask<URL,Void,String>{
        @Override
        protected String doInBackground(URL... urls){
           URL searchUrl = urls[0];
           String SearchResults = null;
           try {
               SearchResults = getResponseFromHttpURL(searchUrl);
           }catch (Exception e){
               Log.d("Internet.java","HttpQueryTask: something wrong with getResponseFromHttpURL function");
               e.printStackTrace();
           }
           return SearchResults;
       }
       @Override
       protected void onPostExecute(String s){
           if(s != null && !s.equals("")){
            MainActivity.mSearchResults.setText(s);
           }
       }
    }//End of Async Task
}
