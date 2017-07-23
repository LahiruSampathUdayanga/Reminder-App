package com.example.sampath.reminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button reg;
    private TextView tvLogin;
    private EditText tvEmail,tvPass;
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DbHelper(this);
        reg = (Button)findViewById(R.id.tvRegister);
        tvEmail = (EditText)findViewById(R.id.tvEmail);
        tvPass = (EditText)findViewById(R.id.tvPass);
        tvLogin = (TextView)findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(this);
        reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvRegister:
                register();
                break;
            case R.id.tvLogin:
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
                break;
            default:

        }
    }

    private void register(){
        String email = tvEmail.getText().toString();
        String pass = tvPass.getText().toString();
        if(email.isEmpty() && pass.isEmpty()){
            displayToast("Username/Password field empty");
        }else{
            db.addUser(email,pass);
            displayToast("User registered");
            finish();
        }
    }

    private void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
