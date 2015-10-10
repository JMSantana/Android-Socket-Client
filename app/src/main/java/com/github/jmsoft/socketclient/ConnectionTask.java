package com.github.jmsoft.socketclient;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.DataInputStream;
import java.net.InetAddress;
import java.net.Socket;

import socketclient.lg.com.socketclient.R;

/**
 * Async task to connect to server
 */
public class ConnectionTask extends AsyncTask<Void, String, Void> {

    public ConnectionTask(){
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
    }
}
