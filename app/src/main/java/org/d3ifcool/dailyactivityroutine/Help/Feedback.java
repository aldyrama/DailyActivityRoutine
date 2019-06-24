package org.d3ifcool.dailyactivityroutine.Help;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import org.d3ifcool.dailyactivityroutine.R;
import org.d3ifcool.dailyactivityroutine.Settings.Constant;

public class Feedback extends AppCompatActivity {

    private EditText mEditTextTo;
    private EditText mEditTextSubject;
    private EditText mEditTextFeedback;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar_feedback );
        toolbar.setTitle( "Kritik & Saran" );


        mEditTextTo = findViewById(R.id.email_input);
        mEditTextSubject = findViewById(R.id.Subject_input);
        mEditTextFeedback = findViewById(R.id.feedback_input);

        FloatingActionButton buttonSend = findViewById(R.id.button_send);
        buttonSend.setBackgroundTintList( ColorStateList.valueOf( Constant.color ));
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();

            }
        });
    }

    private void sendMail() {
        String recipientList = mEditTextTo.getText().toString();
        String[] recipients = recipientList.split(",");

        String subject = mEditTextSubject.getText().toString();
        String message = mEditTextFeedback.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        //intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"aldy.ramadhan012@gmail.com"});
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        //startActivity(Intent.createChooser(intent, "Choose an email client"));
        startActivity(Intent.createChooser(intent, "Send mail using..."));
    }
}