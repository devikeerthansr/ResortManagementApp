package com.example.resortmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resortmanagement.constant.SQLCommand;
import com.example.resortmanagement.util.DBOperator;
import com.example.resortmanagement.util.UserData;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    EditText userName,password;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        userName = findViewById(R.id.inputname);
        password = findViewById(R.id.inputpassword);
        loginBtn = findViewById(R.id.login);
        error = findViewById(R.id.textinput_error);
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(userName.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Login and password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] args = getArgs();
                Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_LOGIN,args);
                if((cursor != null) && (cursor.getCount() > 0)) {
                    error.setVisibility(View.GONE);
                    while (cursor.moveToNext())
                    {
                        String id = cursor.getString(0);
                        int custId = Integer.parseInt(id);
                        String firstName = cursor.getString(1);
                        String lastName = cursor.getString(2);
                        UserData.setCustId(custId);
                        UserData.setFirstName(firstName);
                        UserData.setLastName(lastName);
                    }

                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(i);
                } else {
                    error.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private String[] getArgs(){
        String[] args = new String[2];
        args[0] = userName.getText().toString();
        args[1] = password.getText().toString();
        return args;
    }

}
