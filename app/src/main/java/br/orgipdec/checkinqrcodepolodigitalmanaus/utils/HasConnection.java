package br.orgipdec.checkinqrcodepolodigitalmanaus.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HasConnection extends AsyncTask<Void, Integer, Boolean> {

        private Context context;

        public HasConnection(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

            assert cm != null;
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnected();


            if (isConnected) {
                try {
                    HttpURLConnection urlc = (HttpURLConnection)
                            (new URL("http://info.cern.ch/hypertext/WWW/TheProject.html")
                                    .openConnection());
                    urlc.setRequestProperty("User-Agent", "Android");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 204 &&
                            urlc.getContentLength() == 0)
                        return true;

                } catch (IOException e) {
                    Log.e("ResultadoJFS", "Error checking internet connection_HS1");
                    return false;
                }
            } else {
                Log.d("ResultadoJFS", "No network available!_HS1");
                return false;
            }


            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if(result == null || result == true){
                Log.d("ResultadoJFS", "COM INTERNET " + result);
            }
            else if(result == false){
                Log.d("ResultadoJFS", "No network available!_HS1 >> " + result);

            }
        }
}
