package com.diamond;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText txtname, txtadd, txtemail, txtphn, txtpassword, txtconfirmpassword;
    Button btnregister;
    ImageView imggoogle,imgsnap,imgfacebook,imgtwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnregister = findViewById(R.id.btnregister);
        txtname = findViewById(R.id.txtname);
        txtadd = findViewById(R.id.txtadd);
        txtemail = findViewById(R.id.txtemail);
        txtphn = findViewById(R.id.txtphn);
        txtpassword = findViewById(R.id.txtpassword);
        txtconfirmpassword = findViewById(R.id.txtconfirmpassword);
        imggoogle = findViewById(R.id.imggoogle);
        imgfacebook = findViewById(R.id.imgfacebook);
        imgsnap = findViewById(R.id.imgsnap);
        imgtwitter = findViewById(R.id.imgtwitter);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String t_name = txtname.getText().toString();
                final String t_add = txtadd.getText().toString();
                final String t_email = txtemail.getText().toString();
                final String t_pass = txtpassword.getText().toString();
                final String t_conpass = txtconfirmpassword.getText().toString();
                final String t_phn = txtphn.getText().toString();
                if (t_name.trim().length() == 0) {
                    txtname.setError("Please enter name");
                }
                if (t_add.trim().length() == 0) {
                    txtadd.setError("Please enter address");
                }
                if (t_email.trim().length() == 0) {
                    txtemail.setError("Please enter email");
                }
                if (t_phn.trim().length() == 0) {
                    txtphn.setError("Please enter contact number");
                }
                if (t_pass.trim().length() == 0) {
                    txtpassword.setError("Please enter password");
                }
                if (t_conpass.trim().length() == 0) {
                    txtconfirmpassword.setError("Please enter confirm password");
                }
                if (!t_pass.equals(t_conpass)) {
                    txtconfirmpassword.setError("Confirm password is not correct");
                }
                if (t_name.trim().length() != 0 &&
                        t_add.trim().length() != 0 &&
                        t_email.trim().length() != 0 &&
                        t_phn.trim().length() != 0 &&
                        t_pass.trim().length() != 0 &&
                        t_conpass.trim().length() != 0 &&
                        (t_pass.equals(t_conpass))) {

                    register(t_name,t_add,t_email,t_pass);
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


    private void register(final String username, final String address, final String email, final String password){

        //String uRl = "http://10.20.25.167:80/loginregisterDiamond/register.php";
        String uRl = "http://10.0.2.2:80/loginregisterDiamond/register.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("You are registered successfully")){
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();
                }else {
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
                param.put("personName",username);
                param.put("address",address);
                param.put("email",email);
                param.put("password",password);

                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(request);

    }

}