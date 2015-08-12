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

        //Try to connect to server. Set executor to run parallel execution with Send Message task
        try {
            ia = getInetAddress();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connectionTask = new ConnectionTask(ia, mPort, this, tvText);
        connectionTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        //set button listener
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (!etMessage.getText().toString().equals("")) {

                tvText.setText(tvText.getText().toString() + '\n' + mIdentification + ": "
                        + etMessage.getText().toString());

                //Start Send Task
                messageTask = new SendMessageTask(connectionTask.getsSocket(), etMessage, mIdentification, getApplicationContext());
                messageTask.execute(etMessage.getText().toString());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Close socket if it is still open
        if (connectionTask.getsSocket() != null) {
            try {
                connectionTask.getsSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return InatAddress object from host address
     */
    public InetAddress getInetAddress() throws InterruptedException {
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

    public void setmIdentification(String mIdentification) {
        this.mIdentification = mIdentification;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public void setmPort(int mPort) {
        this.mPort = mPort;
    }

    public ConnectionTask getConnectionTask() {
        return connectionTask;
    }

    public InetAddress getIa() {
        return ia;
    }

    public SendMessageTask getMessageTask() {
        return messageTask;
    }

    public void setIa(InetAddress ia) {
        this.ia = ia;
    }
}
