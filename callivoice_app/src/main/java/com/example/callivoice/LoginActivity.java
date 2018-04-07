package com.example.callivoice;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by 13 디콘 김덕영 on 2018-04-07.
 */

public class LoginActivity {

    EditText idText = (EditText) findViewById(R.id.idText);
    EditText passwordText = (EditText) findViewById(R.id.passwordText);
    EditText emailText = (EditText) findViewById(R.id.emailText);

    Button loginButton =(Button)findViewById(R.id.loginButton);
    TextView registerButton = (TextView) findViewByID(R.id.registerButton);

    registerButton.setOnClickListner(new View.OnClickListener()){
        public void onClick(View view){
            Intent registerIntent = new Intent(LoginActivity.this.registerActivity.class);
            LoginActivity.this.startActivity(registerIntent);
        }


        }

}
}
