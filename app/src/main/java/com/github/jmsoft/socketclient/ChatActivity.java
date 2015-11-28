package com.github.jmsoft.socketclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.jmsoft.socketclient.interfaces.ActivityGenericsInterface;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import socketclient.lg.com.socketclient.R;

/**
 * Chat Activity to send and receive messages
 */
public class ChatActivity extends AppCompatActivity implements ActivityGenericsInterface {

    private EditText etMessage;
    private RecyclerView recyclerView;
    private Button btnSend;
    private LinearLayoutManager mLayoutManager;

    private String mIdentification;
    private String mAddress;
    private int mPort;

    private InetAddress ia = null;

    //Async tasks
    private ConnectionTask connectionTask;
    private SendMessageTask messageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Get the reference to the UI components
        initializeUIComponents();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("chat", "send: " + etMessage.getText().toString());
                if (!etMessage.getText().toString().equals("")) {
                    ((MyAdapter)(recyclerView.getAdapter())).add(mIdentification + ':' +
                            etMessage.getText().toString());
                    messageTask = new SendMessageTask(connectionTask.getsSocket(), etMessage,
                            mIdentification, ChatActivity.this);
                    messageTask.execute(etMessage.getText().toString());
                }
            }
        });

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        MyAdapter mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);

        //Get values coming from MainActivity
        getIntentValues();

        try {
            ia = getInetAddress();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        connectionTask = new ConnectionTask(ia, mPort, this, recyclerView);
        connectionTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        btnSend = (Button) findViewById(R.id.btnSend);
        try {
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setHomeButtonEnabled(true);
            }
        } catch(Exception e) {
            Log.e("chat", e.getMessage());
        }
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
