package com.github.jmsoft.socketclient;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

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
    private RecyclerView recyclerView;

    public ConnectionTask(InetAddress ia, int mPort, Context context, RecyclerView recyclerView){
        this.ia = ia;
        this.mPort = mPort;
        this.context = context;
        this.recyclerView = recyclerView;
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
        ((MyAdapter)(recyclerView.getAdapter())).add(values[0]);
    }

    public Socket getsSocket() {
        return sSocket;
    }
}
