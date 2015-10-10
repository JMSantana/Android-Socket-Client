package com.github.jmsoft.socketclient;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import socketclient.lg.com.socketclient.R;

/**
 * Async task to send messages to server
 */
public class SendMessageTask extends AsyncTask<String, String, Void> {

    public SendMessageTask(){
    }

    @Override
    protected Void doInBackground(String... arg) {
        return null;
    }

}
