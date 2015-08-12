package com.github.jmsoft.socketclient;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jmsoft.socketclient.error.ErrorConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

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

    //UI components
    EditText etIdentification;
    EditText etAddress;
    EditText etPort;
    Button btnConnect;
    TextView tvIdentification;
    TextView tvAddress;
    TextView tvPort;

    @Before
    public void setup()  {
//        activity = Robolectric.buildActivity(MainActivity.class)
//                .create().get();
        activity = Robolectric.setupActivity(MainActivity.class);
        etIdentification = (EditText) activity.findViewById(R.id.etIdentification);
        etAddress = (EditText) activity.findViewById(R.id.etAddress);
        etPort = (EditText) activity.findViewById(R.id.etPort);
        btnConnect = (Button) activity.findViewById(R.id.btnConnect);
        tvIdentification = (TextView) activity.findViewById(R.id.tvIdentification);
        tvAddress = (TextView) activity.findViewById(R.id.tvAdrdess);
        tvPort = (TextView) activity.findViewById(R.id.tvPort);
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void shouldCallInitializeUIComponentsWhenActivityIsCreated(){
        //given
        MainActivity spy = Mockito.spy(activity);

        //when
        spy.onCreate(null);

        //then
        Mockito.verify(spy, Mockito.times(1)).initializeUIComponents();
    }

    @Test
    public void shouldHaveLocalizedStringsOnUIComponents() throws Exception {
        //expect
        assertThat(btnConnect.getText().toString(), equalTo("Connect"));
        assertThat(tvIdentification.getText().toString(), equalTo("Identification"));
        assertThat(tvAddress.getText().toString(), equalTo("Address"));
        assertThat(tvPort.getText().toString(), equalTo("Port"));
    }

    @Test
    public void shouldStartChatActivity() throws Exception
    {
        //given
        etIdentification.setText("testId");
        etAddress.setText("localhost");
        etPort.setText("1234");

        //when
        btnConnect.performClick();

        //then
        ShadowActivity.IntentForResult intent = Shadows.shadowOf(activity).peekNextStartedActivityForResult();

        //and
        assertEquals(intent.requestCode, 0);
        assertEquals(ChatActivity.class.getCanonicalName(), intent.intent.getComponent().getClassName());
        assertEquals(intent.intent.getStringExtra("identification"), "testId");
        assertEquals(intent.intent.getStringExtra("address"), "localhost");
        assertEquals(intent.intent.getIntExtra("port", 0), 1234);
    }

    @Test
    public void shouldTriggerToastWhenChatActivityReturnsError() throws Exception {
        //given
        etIdentification.setText("testId");
        etAddress.setText("localhost");
        etPort.setText("1234");

        //when
        btnConnect.performClick();

        //then
        ShadowActivity.IntentForResult intent = Shadows.shadowOf(activity).peekNextStartedActivityForResult();

        //and
        Shadows.shadowOf(activity).receiveResult(
                intent.intent,
                ErrorConstants.getConnectionError(),
                null);

        //then
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("Error connecting to the server!"));
    }
}
