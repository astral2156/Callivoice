package com.example.callivoice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    final idText = (EditText) findViewById(R.id.idText);
    final EditText passwordText = (EditText) findViewById(R.id.passwordText);
    final EditText emailText = (EditText) findViewById(R.id.emailText);

    Button registerButton =(Button)findViewById(R.id.registerButton);

    @Override
    public void onClick(View view){
        String idText=idText.getText().toString();
        String passwordText=passwordText.getText().toString();
        String emailText=emailText.getText().toString();

        Response.Listner<String>reponseListner = new Response.Listner<String>(){
            @Override
            public void onResponse(String response){
                try{

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("회원 등록에 성공하였습니다")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.startActivity(intent);
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("회원 등록 실패!")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };
        RegisterRequest registerRequest = new RegisterRequest(idText,passwordText,emailText,responseListner);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerRequest);
    }
}
