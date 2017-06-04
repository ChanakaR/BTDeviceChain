package com.wbn.uom.btdevicechain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogInActivity extends AppCompatActivity {

    private Button buttonMaster;
    private Button buttonSlave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        buttonMaster = (Button)findViewById(R.id.btnMaster);
        buttonSlave = (Button)findViewById(R.id.btnSlave);

        buttonMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity("MASTER");
            }
        });

        buttonSlave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity("VALUE");
            }
        });
    }

    private void startMainActivity(String value){
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        intent.putExtra("STATE", value); //Optional parameters
        LogInActivity.this.startActivity(intent);
    }
}
