package com.github.jmsoft.socketclient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        try {
            ia = getInetAddress();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        connectionTask = new ConnectionTask(ia, mPort, this, tvText);
        connectionTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etMessage.getText().toString().equals("")) {
                    tvText.setText(tvText.getText().toString() + '\n' + mIdentification + ':' +
                        etMessage.getText().toString());
                    messageTask = new SendMessageTask(connectionTask.getsSocket(), etMessage,
                            mIdentification, ChatActivity.this);
                    messageTask.execute(etMessage.getText().toString());
                }
            }
        });
    }

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
        if (connectionTask.getsSocket() != null) {
            try {
                connectionTask.getsSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
