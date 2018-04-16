package com.example.xander.fxconverter;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.xander.fxconverter.api.httpCalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity  {


    private static final String URL_RATES = "http://fx.xdreamz.net/fx.php";

        private ProgressDialog pDialog;
        private ListView lv;

        ArrayList<HashMap<String, String>> fxList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            lv = (ListView) findViewById(R.id.list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item);

            fxList = new ArrayList<>();

            new getFxRates().execute();
        }

        /**
         * Async task class to get json by making HTTP call
         */
        private class getFxRates extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Showing progress dialog
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Getting Updated Exchange Rates...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected Void doInBackground(Void... arg0) {
                httpCalls sh = new httpCalls();

                // Making a request to url and getting response
                String jsonStr = sh.makeServiceCall(URL_RATES);

                Log.e("NO RESPONSE: ", "Response from url: " + jsonStr);

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // Getting JSON Array node
                        JSONArray rates = jsonObj.getJSONArray("exRates");

                        // looping through Exchange Rates
                        for (int i = 0; i < rates.length(); i++) {
                            JSONObject c = rates.getJSONObject(i);

                            String cur = c.getString("Currency");
                            String amt = c.getString("Amount");

                            // tmp hash map for single contact
                            HashMap<String, String> fx = new HashMap<>();

                            // adding each child node to HashMap key => value
                            fx.put("Currency", cur);
                            fx.put("Amount", amt);

                            // adding contact to contact list
                            fxList.add(fx);
                        }
                    } catch (final JSONException e) {
                        Log.e("PARSING ERROR: ", "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });

                    }
                } else {
                    Intent intent = new Intent(MainActivity.this, NoItemInternetImage.class);
                    startActivity(intent);
                    Log.e("NO JSON: ", "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                // Dismiss the progress dialog
                if (pDialog.isShowing())
                    pDialog.dismiss();
                /**
                 * Updating parsed JSON data into ListView
                 * */
                ListAdapter adapter = new SimpleAdapter(
                        MainActivity.this, fxList,
                        R.layout.fx_list, new String[]{"Currency", "Amount"}, new int[]{R.id.curr, R.id.amount});
                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        //int listClickVal = lv.getSelectedItemPosition();

                        Log.d("SELECTED POSTION", String.valueOf(fxList.get(i)));

                        Intent intent = new Intent(MainActivity.this, ConvertActivity.class);
                        intent.putExtra("ItemClick", adapterView.getItemAtPosition(i).toString());
                        startActivity(intent);
                    }
                });
            }

        }

}
