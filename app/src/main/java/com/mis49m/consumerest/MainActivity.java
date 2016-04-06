package com.mis49m.consumerest;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnCalculate;
    ListView listVenues;
    EditText etLatLong;

    VenueAdapter venueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final ArrayList<Venue> alVenues = new ArrayList<Venue>();

        etLatLong = (EditText) findViewById(R.id.et_lat_long);
        etLatLong.setText("41.087318,29.050287");
        listVenues = (ListView) findViewById(R.id.list_venues);
        btnCalculate = (Button) findViewById(R.id.btn_calculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try {

                String result = getVenues(etLatLong.getText().toString());
                JSONObject jsonObject = new JSONObject(result);
                JSONObject response = jsonObject.getJSONObject("response");
                JSONArray venues = response.getJSONArray("venues");
                for (int i = 0; i < venues.length(); i++) {
                    JSONObject venue = venues.getJSONObject(i);

                    Venue ven = new Venue();
                    ven.setName(venue.getString("name"));

                    alVenues.add(ven);
                }

                Venue[] vens = new Venue[alVenues.size()];
                alVenues.toArray(vens);
                venueAdapter = new VenueAdapter(MainActivity.this, vens);
                listVenues.setAdapter(venueAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        });
    }

    public String getVenues(String latLong) {
        String urlStr="https://api.foursquare.com/v2/venues/search?ll=";
        urlStr +=latLong;
        urlStr +="&client_id=YE23BTJMFDDZGCHGV5J3MPA4EHVCV20KQDRPYTGQFNBLV3HO&client_secret=PUDKZGGIMWE2G3YWWYLOFMXBB4L1M52MKXF1JGBWM5YXWSEB&v=20131029";
        urlStr += "&limit=10";

        URL url;
        HttpURLConnection conn=null;
        InputStream inputStream = null;
        InputStreamReader reader = null;
        StringBuilder response = new StringBuilder();

        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000); //maximum time to wait for an input stream read
            conn.setConnectTimeout(15000); //maximum time to wait while connecting
            conn.setRequestMethod("GET");
            conn.setDoInput(true); //whether this URLConnection allows receiving data
            conn.connect();

            inputStream = conn.getInputStream();
            reader = new InputStreamReader(inputStream);

            int data = reader.read();
            while(data!=-1){
                response.append( (char)data );
                data = reader.read();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{

            try {
                if(reader!=null) reader.close();
                if(inputStream!=null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(conn!=null) conn.disconnect();

        }

        return response.toString();
    }

    public void parseList1Json(){
        try {

            JSONObject jsonObject = new JSONObject(Util.readFile(this, R.raw.list1));
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parseList2Json(){
        try {

            JSONObject jsonObject = new JSONObject(Util.readFile(this, R.raw.list2));
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");

            JSONObject jsonLocation = jsonObject.getJSONObject("location");
            double lat = jsonLocation.getDouble("lat");
            double lng = jsonLocation.getDouble("lng");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseList3Json(){
        try {

            JSONArray jsonArray = new JSONArray(Util.readFile(this, R.raw.list3));
            for(int i=0;i <jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");

                JSONObject jsonLocation = jsonObject.getJSONObject("location");
                double lat = jsonLocation.getDouble("lat");
                double lng = jsonLocation.getDouble("lng");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
