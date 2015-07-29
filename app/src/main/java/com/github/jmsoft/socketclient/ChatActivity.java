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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
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

    //Socket for connecting the client to server
    private static Socket sSocket;

    private InetAddress ia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Get the reference to the UI components
        initializeUIComponents();

        //Get values coming from MainActivity
        getIntentValues();

        //Try to connect to server. Set executor to run parallel execution with Send Message task
        new ConnectToServerTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        //set button listener
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etMessage.getText().toString().equals("")) {

                    tvText.setText(tvText.getText().toString() + '\n' + mIdentification + ": "
                            + etMessage.getText().toString());

                    //Start Send Task
                    new SendMessageTask().execute(etMessage.getText().toString());

                }
            }
        });
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

    /**
     * Async task to send messages to server
     */
    private class SendMessageTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... arg) {
            try {
                final DataOutputStream dos = new DataOutputStream(
                        sSocket.getOutputStream());

                //Try to write on socket output stream
                try {
                    dos.writeUTF(mIdentification + " " + getResources().getString(R.string.says) + " " + arg[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                    finishWithError();
                }

            } catch (Exception e1) {
                e1.printStackTrace();
                finishWithError();
            }

            //Update UI
            publishProgress(arg[0]);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            etMessage.setText("");
        }
    }

    /**
     * Async task to connect to server
     */
    private class ConnectToServerTask extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                sSocket = new Socket(getInetAddress(), mPort);

                //Add connected to server message to UI
                publishProgress(getResources().getString(R.string.connected_to_server));

                //Listen to messages
                while (true) {
                    DataInputStream dis = new DataInputStream(
                            sSocket.getInputStream());
                    final String string = dis.readUTF();

                    publishProgress(string);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                finishWithError();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            tvText.setText(tvText.getText().toString() + '\n'
                    + values[0]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Close socket if it is still open
        if (sSocket != null) {
            try {
                sSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return InatAddress object from host address
     */
    private InetAddress getInetAddress() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ia = InetAddress.getByName(mAddress);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    ia = null;
                }
            }
        });
        t.start();
        t.join();
        return ia;
    }

    /**
     * Configure error result and finish activity
     */
    public void finishWithError() {
        setResult(ErrorConstants.getConnectionError());
        finish();
    }
}
