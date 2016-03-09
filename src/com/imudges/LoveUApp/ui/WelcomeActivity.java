package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.UserService;

public class WelcomeActivity extends Activity {
    private UserService userService = new UserService();
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
