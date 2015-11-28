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

    private Socket sSocket;
    private Context context;
    private EditText etMessage;
    private String mIdentification;

    public SendMessageTask(Socket sSocket, EditText etMessage, String mIdentification, Context context){
        this.sSocket = sSocket;
        this.etMessage = etMessage;
        this.mIdentification = mIdentification;
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... arg) {
        try {
            final DataOutputStream dos = new DataOutputStream(sSocket.getOutputStream());
            dos.writeUTF(mIdentification + ' ' + context.getString(R.string.says) + ' ' + arg[0]);
            publishProgress(arg[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        etMessage.setText("");
    }

}
