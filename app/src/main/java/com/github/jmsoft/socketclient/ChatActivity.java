package com.github.jmsoft.socketclient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jmsoft.socketclient.error.ErrorConstants;
import com.github.jmsoft.socketclient.interfaces.ActivityGenericsInterface;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import socketclient.lg.com.socketclient.R;

/**
 * Chat Activity to send and receive messages
 */
public class ChatActivity extends Activity implements ActivityGenericsInterface {

    private EditText etMessage;
    private TextView tvText;
    private Button btnSend;

    private String mIdentification;
    private String mAddress;
    private int mPort;

    private InetAddress ia;

    //Async tasks
    private ConnectionTask connectionTask;
    private SendMessageTask messageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Get the reference to the UI components
        initializeUIComponents();

        //Get values coming from MainActivity
        getIntentValues();

    }

    /**
     * Get UI components references
     */
    public void initializeUIComponents() {
        etMessage = (EditText) findViewById(R.id.etMessage);
        tvText = (TextView) findViewById(R.id.tvText);
        btnSend = (Button) findViewById(R.id.btnSend);
    }

    /**
     * Retrieve values passed through intent
     */
    public void getIntentValues() {
        mIdentification = getIntent().getStringExtra("identification");
        mAddress = getIntent().getStringExtra("address");
        mPort = getIntent().getIntExtra("port", 1234);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
