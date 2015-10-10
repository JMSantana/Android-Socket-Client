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

}
