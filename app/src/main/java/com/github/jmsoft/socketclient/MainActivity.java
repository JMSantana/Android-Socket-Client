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

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String identification = etIdentification.getText().toString();
                String address = etAddress.getText().toString();
                Integer port = Integer.parseInt(etPort.getText().toString());
                launchChatActivity(identification,address,port);
            }
        });

    }

    private void launchChatActivity(String identification, String address, Integer port) {
        Intent connectIntent = new Intent(this, ChatActivity.class);
        connectIntent.putExtra("identification", identification);
        connectIntent.putExtra("address", address);
        connectIntent.putExtra("port", port);
        startActivityForResult(connectIntent, REQUEST_CODE);
    }

    /**
     * Get UI components references
     */
    @Override
    public void initializeUIComponents() {
        etIdentification = (EditText) findViewById(R.id.etIdentification);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etPort = (EditText) findViewById(R.id.etPort);
        btnConnect = (Button) findViewById(R.id.btnConnect);
    }

    @Override
    public void getIntentValues() {}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE == requestCode) {
            if (ErrorConstants.getConnectionError() == resultCode) {
                Toast.makeText(this, getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            }
        }
    }
}
