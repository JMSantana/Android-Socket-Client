package com.github.jmsoft.socketclient;

import android.content.Intent;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import socketclient.lg.com.socketclient.BuildConfig;
import socketclient.lg.com.socketclient.R;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class MainActivityTest {

    // Activity of the Target application
    MainActivity activity;

    @Before
    public void setup()  {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveHappySmiles() throws Exception {
        Button button = (Button) activity.findViewById(R.id.btnConnect);
        assertThat(button.getText().toString(), equalTo("Conectar"));
    }

    @Test
    public void buttonConnectClickShouldStartChatActivity() throws Exception
    {
        Button button = (Button) activity.findViewById(R.id.btnConnect);
        button.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(ChatActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }
}
