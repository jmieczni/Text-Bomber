package com.textbomberandroid.textbomber;

import android.content.Context;
import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class SendMessage extends MainActivity implements Runnable  {

    private EditText numberTextBox;
    private EditText countTextBox;
    private EditText delayTextBox;
    private EditText messageTextBox;
    private CheckBox includeCounterCheckBox;
    private Button sendButton;
    private ScrollView outputLogLayout;
    private TextView infoBoxTextView;
    private Context appContext;
    private TextView label;

    public SendMessage(TextView label, EditText numberTextBox, EditText countTextBox, EditText delayTextBox, EditText messageTextBox, CheckBox includeCounterCheckBox, ScrollView outputLogLayout, TextView infoBoxTextView, Button sendButton, Context appContext){
        this.numberTextBox = numberTextBox;
        this.countTextBox = countTextBox;
        this.delayTextBox = delayTextBox;
        this.messageTextBox = messageTextBox;
        this.includeCounterCheckBox = includeCounterCheckBox;
        this.sendButton = sendButton;
        this.outputLogLayout = outputLogLayout;
        this.infoBoxTextView = infoBoxTextView;
        this.appContext = appContext;

        this.label = label;
        this.label.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void run() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    String phoneNumber = numberTextBox.getText().toString();
                    String message = messageTextBox.getText().toString();
                    int numberOfTexts = Integer.parseInt(countTextBox.getText().toString());
                    int delay  = (Integer.parseInt(delayTextBox.getText().toString()) * 1000); //in seconds
                    int count = 0;
                    label.setText("");

                    while (count < numberOfTexts) {
                        if (includeCounterCheckBox.isChecked()){
                            smsManager.sendTextMessage(phoneNumber, null, message + " " + (count + 1), null, null);
                        } else {
                            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                        }

                        //Increase count
                        label.append(++count + " away!\n");

                        //Sleep for the specified time
                        Thread.sleep(delay);
                    }

                    Toast.makeText(appContext, "Just sent all " + numberOfTexts + " of those messages!", Toast.LENGTH_LONG).show();
                } catch (Exception e){
                    System.out.println(e);
                    Toast.makeText(appContext, "ERROR: " + e, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}