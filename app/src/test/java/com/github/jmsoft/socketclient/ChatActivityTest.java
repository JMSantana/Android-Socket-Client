package com.github.jmsoft.socketclient;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import socketclient.lg.com.socketclient.BuildConfig;
import socketclient.lg.com.socketclient.R;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class ChatActivityTest {

    // Activity of the Target application
    ChatActivity activity;

    //AsyncTasks mock
    ConnectionTask connectionTaskMock = Mockito.mock(ConnectionTask.class);
    SendMessageTask messageTaskMock = Mockito.mock(SendMessageTask.class);

    TextView tvText;
    EditText etMessage;
    Button btnSend;

}
