package com.github.jmsoft.socketclient;

import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.w3c.dom.Text;

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

    TextView tvText;
    Button btnSend;

    @Before
    public void setup()  {
        activity = Robolectric.buildActivity(ChatActivity.class)
                .create().get();
        tvText = (TextView) activity.findViewById(R.id.tvText);
        btnSend = (Button) activity.findViewById(R.id.btnSend);
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
}
