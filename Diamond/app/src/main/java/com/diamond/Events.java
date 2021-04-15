package com.diamond;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Events extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;

    BufferedInputStream is;


    //private static final String apiurl="http://10.20.25.167:80/eventDiamond/event.php";
    private static final String apiurl="http://10.0.2.2:80/eventDiamond/event.php";
    ListView lv;
    private static String name[];
    private static String desig[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.event);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.gallery:
                        startActivity(new Intent(getApplicationContext(), Gallery.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.product:
                        startActivity(new Intent(getApplicationContext(), Product.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.event:
                        return true;
                    case R.id.aboutus:
                        startActivity(new Intent(getApplicationContext(), AboutUs.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });

        lv=(ListView)findViewById(R.id.listView);

        fetch_data_into_array(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = lv.getItemAtPosition(position).toString();

                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
                return true;
            case R.id.logout:
                sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.prefStatus),"logout");
                editor.apply();
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void fetch_data_into_array(View view)
    {

        class  dbManager extends AsyncTask<String,Void,String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;

                    name = new String[ja.length()];
                    desig = new String[ja.length()];

                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        name[i] = jo.getString("name");
                        desig[i] = jo.getString("des");

                    }

                    myadapter adptr = new myadapter(getApplicationContext(), name, desig);
                    lv.setAdapter(adptr);

                } catch (Exception ex) {
                    //Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... strings)
            {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuffer data = new StringBuffer();
                    String line;

                    while ((line = br.readLine()) != null) {
                        data.append(line + "\n");
                    }
                    br.close();

                    return data.toString();

                } catch (Exception ex) {
                    return ex.getMessage();
                }

            }

        }
        dbManager obj=new dbManager();
        obj.execute(apiurl);

    }

    class myadapter extends ArrayAdapter<String>
    {
        Context context;
        String ttl[];
        String dsc[];

        myadapter(Context c, String ttl[], String dsc[])
        {
            super(c,R.layout.list_row,R.id.tv1,ttl);
            context=c;
            this.ttl=ttl;
            this.dsc=dsc;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            LayoutInflater inflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.list_row,parent,false);


            TextView tv1=row.findViewById(R.id.tv1);
            TextView tv2=row.findViewById(R.id.tv2);

            tv1.setText(ttl[position]);
            tv2.setText(dsc[position]);

            return row;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Home.class));
    }
}