package com.diamond;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassword extends AppCompatActivity {

    EditText txtemail, txtpassword, txtconfirmpassword;
    Button btnreset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btnreset = findViewById(R.id.btnreset);
        txtemail = findViewById(R.id.txtemail);
        txtpassword = findViewById(R.id.txtpassword);
        txtconfirmpassword = findViewById(R.id.txtconfirmpassword);

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtemail.getText().toString().trim().length() == 0) {
                    txtemail.setError("Please enter email");
                }
                if (txtpassword.getText().toString().trim().length() == 0) {
                    txtpassword.setError("Please enter password");
                }
                if (txtconfirmpassword.getText().toString().trim().length() == 0) {
                    txtconfirmpassword.setError("Please enter confirm password");
                }
                if (!txtpassword.getText().toString().equals(txtconfirmpassword.getText().toString())) {
                    txtconfirmpassword.setError("Confirm password is not correct");
                }

                if (txtemail.getText().toString().trim().length() != 0 &&
                        txtpassword.getText().toString().trim().length() != 0 &&
                        txtconfirmpassword.getText().toString().trim().length() != 0 &&
                        (txtpassword.getText().toString().equals(txtconfirmpassword.getText().toString()))) {
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    i.putExtra("email", txtemail.getText().toString());
                    i.putExtra("password", txtpassword.getText().toString());
                    startActivity(i);
                }
            }
        });
    }
}