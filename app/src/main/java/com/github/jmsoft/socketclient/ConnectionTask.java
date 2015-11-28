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
        try {
            sSocket = new Socket(ia, mPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        publishProgress(context.getString(R.string.connected_to_server));

        while (true) {
            try {
                DataInputStream dis = new DataInputStream(sSocket.getInputStream());
                final String string = dis.readUTF();
                publishProgress(string);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        tvText.setText(tvText.getText().toString() + '\n' + values[0]);
    }

    public Socket getsSocket() {
        return sSocket;
    }
}
