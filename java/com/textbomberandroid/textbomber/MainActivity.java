package com.textbomberandroid.textbomber;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public EditText messageTextBox;
    public EditText numberTextBox;
    public EditText countTextBox;
    public EditText delayTextBox;
    public CheckBox includeCounterCheckBox;
    public ScrollView outputLogLayout;
    public TextView infoBoxTextView;
    public Button sendButton;
    private TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberTextBox = (EditText)findViewById(R.id.editTextPhoneNumber);
        countTextBox = (EditText)findViewById(R.id.editTextCount);
        delayTextBox = (EditText)findViewById(R.id.editTextDelay);
        messageTextBox = (EditText)findViewById(R.id.editTextMessage);
        includeCounterCheckBox = (CheckBox)findViewById(R.id.checkBoxIncludeCounter);
        outputLogLayout = (ScrollView)findViewById(R.id.outputLogLayout);
        sendButton = (Button)findViewById(R.id.btnSend);
        label = (TextView)findViewById(R.id.label);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context appContext = getApplicationContext();
                try{
                    (new Thread(new SendMessage(label, numberTextBox, countTextBox, delayTextBox, messageTextBox, includeCounterCheckBox, outputLogLayout, infoBoxTextView, sendButton, appContext))).start();
                }
                catch (Exception e){
                    Toast.makeText(appContext, "ERROR: " + e, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}