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

    @Before
    public void setup() throws UnknownHostException {
        activity = Robolectric.buildActivity(ChatActivity.class)
                .create().get();

        activity.setmIdentification("test");
        activity.setmAddress("192.168.1.106");
        activity.setmPort(1234);

        activity.setIa(InetAddress.getByName("192.168.1.106"));

        tvText = (TextView) activity.findViewById(R.id.tvText);
        btnSend = (Button) activity.findViewById(R.id.btnSend);
        etMessage = (EditText) activity.findViewById(R.id.etMessage);
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveLocalizedStringsOnUIComponents() throws Exception {
        assertThat(btnSend.getText().toString(), equalTo("Send"));
        assertThat(tvText.getText().toString(), equalTo(""));
    }

    @Test
    public void shouldCallSetUpMethodsWhenActivityIsCreated() throws InterruptedException {
        //given
        ChatActivity spy = Mockito.spy(activity);

        //when
        spy.onCreate(null);

        //then
        Mockito.verify(spy, Mockito.times(1)).initializeUIComponents();
        Mockito.verify(spy, Mockito.times(1)).getIntentValues();
        Mockito.verify(spy, Mockito.times(1)).getInetAddress();

        //Connection task should have been started
        assertTrue(spy.getIa() != null);
        assertTrue(spy.getConnectionTask() != null);
    }
}
