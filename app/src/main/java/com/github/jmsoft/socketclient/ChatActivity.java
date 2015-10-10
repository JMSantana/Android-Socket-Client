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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

    }

    /**
     * Get UI components references
     */
    public void initializeUIComponents() {
    }

    /**
     * Retrieve values passed through intent
     */
    public void getIntentValues() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
