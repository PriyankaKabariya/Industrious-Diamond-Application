package com.diamond;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Product extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;

    int[] images = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
            R.drawable.f,
            R.drawable.g,
            R.drawable.h,
            R.drawable.i,
            R.drawable.j,
            R.drawable.k
           };

    String[] version = {
            "Good",
            "Good",
            "Good",
            "Good",
            "Good",
            "Good",
            "Good",
            "Good",
            "Good",
            "Good",
            "Good"
            };

    String[] price = {
            "$500",
            "$758",
            "$675",
            "$678",
            "$760",
            "$387",
            "$740",
            "$654",
            "$680",
            "$670",
            "$580"
    };

    String[] carat = {
            "1.0",
            "1.1",
            "1.5",
            "1.6",
            "2.0",
            "2.2",
            "2.3",
            "3.0",
            "4.0",
            "4.1",
            "4.4"
           };

    String[] cut = {
            "good",
            "Very good",
            "Ideal",
            "Very good",
            "Good",
            "Very ideal",
            "Very good",
            "Good",
            "Good",
            "Very Ideal",
            "Very good"
    };

    String[] clarity = {
            "VS2",
            "VS1",
            "SI1",
            "VVS2",
            "VS2",
            "VVS1",
            "SI2",
            "VS2",
            "VVS1",
            "SI2",
            "VS2"
    };

    String[] measurement = {
            "3.94 x 3.92 x 2.48 mm",
            "3.93 x 3.76 x 2.36 mm",
            "3.44 x 3.26 x 2.46 mm",
            "3.74 x 3.94 x 2.86 mm",
            "3.90 x 3.92 x 2.44 mm",
            "3.34 x 3.66 x 2.96 mm",
            "3.44 x 3.98 x 2.36 mm",
            "3.91 x 3.56 x 2.56 mm",
            "3.34 x 3.93 x 2.42 mm",
            "3.74 x 3.26 x 2.66 mm",
            "3.99 x 3.97 x 2.49 mm"
    };


    ListView lView;

    ListAdapter lAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.product);

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
                        return true;
                    case R.id.event:
                        startActivity(new Intent(getApplicationContext(), Events.class));
                        overridePendingTransition(0, 0);
                        finish();
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

        lView = (ListView) findViewById(R.id.androidList);

        lAdapter = new ListAdapter(getApplicationContext(), version, price,carat,cut,clarity,measurement, images);

        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplicationContext(), version[i]+" "+price[i], Toast.LENGTH_SHORT).show();

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Home.class));
    }
}