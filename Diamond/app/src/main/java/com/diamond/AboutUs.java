package com.diamond;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutUs extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.aboutus);

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
                        startActivity(new Intent(getApplicationContext(), Events.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.aboutus:
                        return true;
                }
                return false;
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