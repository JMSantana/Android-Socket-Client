package com.github.jmsoft.socketclient;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import socketclient.lg.com.socketclient.R;

/**
 * Async task to connect to server
 */
public class ConnectionTask extends AsyncTask<Void, String, Void> {

    //Socket for connecting the client to server
    private Socket sSocket;
    private InetAddress ia;
    private int mPort;
    private Context context;
    private TextView tvText;

    public ConnectionTask(InetAddress ia, int mPort, Context context, TextView tvText){
        this.ia = ia;
        this.mPort = mPort;
        this.context = context;
        this.tvText = tvText;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
    }
}
