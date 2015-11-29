package com.github.jmsoft.socketclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
public class MainActivity extends AppCompatActivity implements ActivityGenericsInterface {

    //UI components
    private EditText etIdentification;
    private EditText etAddress;
    private EditText etPort;
    private Button btnConnect;

    //Request code
    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the reference to the UI components
        initializeUIComponents();

        //Listening the button action
        btnConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Get UI components text
                String identification = etIdentification.getText().toString();
                String address = etAddress.getText().toString();
                Integer port = Integer.parseInt(etPort.getText().toString());

                launchChatActivity(identification, address, port);
            }
        });
    }

    /**
     * Get UI components references
     */
    public void initializeUIComponents() {
        etIdentification = (EditText) findViewById(R.id.etIdentification);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etPort = (EditText) findViewById(R.id.etPort);
        btnConnect = (Button) findViewById(R.id.btnConnect);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        } catch (Exception e){
            Log.e("phys timer", e.getMessage());
        }
    }

    /**
     * Start chat activity with parameters
     */
    public void launchChatActivity(String identification, String address, Integer port) {
        Intent connectIntent = new Intent(this, ChatActivity.class);
        connectIntent.putExtra("identification", identification);
        connectIntent.putExtra("address", address);
        connectIntent.putExtra("port", port);

        startActivityForResult(connectIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == ErrorConstants.getConnectionError()){
            Toast.makeText(this, getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getIntentValues() {}
}
