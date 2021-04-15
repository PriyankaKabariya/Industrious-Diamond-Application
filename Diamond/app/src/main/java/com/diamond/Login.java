package com.diamond;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextView txtregister;
    TextView txtforgotpass;
    Button btnlogin;
    ImageView imgshare;
    EditText txtemail, txtpassword;
    CheckBox chkme;
    ImageView imggoogle,imgsnap,imgfacebook,imgtwitter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtregister = findViewById(R.id.txtregister);
        txtforgotpass = findViewById(R.id.txtforgotpass);
        btnlogin = findViewById(R.id.btnlogin);
        imgshare = findViewById(R.id.imgshare);
        txtemail = findViewById(R.id.txtemail);
        txtpassword = findViewById(R.id.txtpassword);
        chkme = findViewById(R.id.chkme);
        imggoogle = findViewById(R.id.imggoogle);
        imgfacebook = findViewById(R.id.imgfacebook);
        imgsnap = findViewById(R.id.imgsnap);
        imgtwitter = findViewById(R.id.imgtwitter);


        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.prefStatus),"");
        if (loginStatus.equals("loggedin")){
            startActivity(new Intent(getApplicationContext(),Home.class));
            finish();
        }




        txtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });

        txtforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
//                startActivity(i);
            }
        });

        imgshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tex_email = txtemail.getText().toString();
                String tex_password = txtpassword.getText().toString();
                if (tex_email.trim().length() == 0) {
                    txtemail.setError("Please enter email");
                }
                if (tex_password.trim().length() == 0) {
                    txtpassword.setError("Please enter password");
                }
                if (TextUtils.isEmpty(tex_email) || TextUtils.isEmpty(tex_password)){
                    Toast.makeText(getApplicationContext(), "All Fields Required", Toast.LENGTH_SHORT).show();
                }
                else{
                    login(tex_email,tex_password);
                }
            }
        });

        imggoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://accounts.google.com/ServiceLogin/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
        imgsnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://accounts.snapchat.com/accounts/login?continue=https%3A%2F%2Faccounts.snapchat.com%2Faccounts%2Fwelcome");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
        imgfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://www.facebook.com/?stype=lo&jlou=Afcj1HDPdROMhF2XNOnt9h9o2wF4I0dqoy2tfGO3dkUaM3YnOQ1vfKLlT5aKBfAkZzclLIah31SzD1xwn9hH0kE-vKtNemwMu49-HGQt3MsBFg&smuh=26133&lh=Ac9uuQ8MHwc55cxTRP0");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
        imgtwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://twitter.com/login?lang=en-gb");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
    }

    private void login(final String email, final String password){

        //String uRl = "http://10.20.25.167:80/loginregisterDiamond/login.php";
        String uRl = "http://10.0.2.2:80/loginregisterDiamond/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Login Success")){

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (chkme.isChecked()){
                        editor.putString(getResources().getString(R.string.prefStatus),"loggedin");
                    }
                    else{
                        editor.putString(getResources().getString(R.string.prefStatus),"loggedout");
                    }
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(),Home.class));

                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("email",email);
                param.put("password",password);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
