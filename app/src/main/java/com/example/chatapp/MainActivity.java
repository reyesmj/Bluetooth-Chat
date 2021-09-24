package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView BluetoothOn, BluetoothOff, PressButton;
    BluetoothAdapter myBluetoothAdapter;
    Intent btEnable;
    int requetCodeForEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PressButton = (ImageView) findViewById(R.id.PressButton);
        PressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Logic.class);
                startActivity(intent);
            }
        });
        BluetoothOn = (ImageView) findViewById(R.id.BluetoothOn);
        BluetoothOff = (ImageView) findViewById(R.id.BluetoothOff);
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        requetCodeForEnable = 1;
        bluetoothOnMethod();
        bluetoothOffMethod();
    }

    private void bluetoothOffMethod() {
        BluetoothOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myBluetoothAdapter.isEnabled()) {
                    myBluetoothAdapter.disable();
                    Toast.makeText(getApplicationContext(), "Bluetooth turned off!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth already turned off!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requetCodeForEnable) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Bluetooth is Enabled", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Bluetooth  Enabled Cancelled", Toast.LENGTH_LONG).show();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void bluetoothOnMethod() {
        BluetoothOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(), "Bluetooth does not support on this device", Toast.LENGTH_LONG).show();
                } else {
                    if (!myBluetoothAdapter.isEnabled()) {
                        startActivityForResult(btEnable, requetCodeForEnable);
                    }
                }
            }
        });
    }

}