package com.github.jmsoft.socketclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.jmsoft.socketclient.error.ErrorConstants;
import com.github.jmsoft.socketclient.interfaces.ActivityGenericsInterface;

import socketclient.lg.com.socketclient.R;

/**
 * Main Activity
 */
public class MainActivity extends Activity implements ActivityGenericsInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Get UI components references
     */
    public void initializeUIComponents() {
    }

    @Override
    public void getIntentValues() {}
}
