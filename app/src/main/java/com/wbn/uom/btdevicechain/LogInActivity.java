package com.wbn.uom.btdevicechain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogInActivity extends AppCompatActivity {

    private Button buttonMaster;
    private Button buttonSlave;
    private EditText device_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        buttonMaster = (Button)findViewById(R.id.btnMaster);
        buttonSlave = (Button)findViewById(R.id.btnSlave);
        device_name = (EditText)findViewById(R.id.device_name);

        buttonMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity("MASTER");
            }
        });

        buttonSlave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity("SLAVE");
            }
        });
    }

    private void startMainActivity(String value){
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        Bundle extras = new Bundle();
        extras.putString("STATE",value);
        extras.putString("NAME", String.valueOf(device_name.getText()));
        intent.putExtras(extras);
        LogInActivity.this.startActivity(intent);
    }
}
