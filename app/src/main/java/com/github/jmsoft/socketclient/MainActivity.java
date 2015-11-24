package com.github.jmsoft.socketclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.jmsoft.socketclient.error.ErrorConstants;
import com.github.jmsoft.socketclient.interfaces.ActivityGenericsInterface;

import socketclient.lg.com.socketclient.R;

/**
 * Main Activity
 */
public class MainActivity extends Activity implements ActivityGenericsInterface {

    private static final int REQUEST_CODE = 0;
    private EditText etIdentification;
    private EditText etAddress;
    private EditText etPort;
    private Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUIComponents();
    }

    /**
     * Get UI components references
     */
    public void initializeUIComponents() {
        etIdentification = (EditText) findViewById(R.id.etIdentification);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etPort = (EditText) findViewById(R.id.etPort);
        btnConnect = (Button) findViewById(R.id.btnConnect);
    }

    @Override
    public void getIntentValues() {}
}
