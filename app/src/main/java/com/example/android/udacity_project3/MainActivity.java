package com.example.android.udacity_project3;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.button;
import static android.R.attr.country;
import static android.R.attr.value;
import static com.example.android.udacity_project3.R.id.your_email_edittext;
import static com.example.android.udacity_project3.R.id.your_name_edittext;

public class MainActivity extends AppCompatActivity {

    boolean iCheckName = false;
    boolean iCheckEmail = false;
    boolean iSendMail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * Validate fullName, Min 5 Char, Max 25 Char
     */
    private String checkName(String fullName) {
        // Show participant name
        EditText editName = (EditText) findViewById(your_name_edittext);
        String pName = editName.toString();

        int sizeName = fullName.length();
        if (sizeName == 0) { // Check length, must be >= 1
            Toast.makeText(this, "Error : Full Name cannot be empty", Toast.LENGTH_SHORT).show();
            editName.requestFocus();

        } else if (sizeName > 25) { // Check length, must be >= 5
            Toast.makeText(this, "Error : Full Name Max lenght is 25 char", Toast.LENGTH_SHORT).show();
            editName.requestFocus();
        }
        iCheckName = true;
        return fullName;
    }

    /*
    * Validate idEmail, Min 5 Char, Max 25 Char
    */
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    /**
     * Summary quiz result
     *
     * @param fullName --> Name of participant
     * @param idEmail  --> Email of participant
     */
    private String resultSummary(String fullName, String idEmail, boolean hasFrance, boolean hasPorto, boolean hasInter, boolean hasArgentina, boolean hasCr7) {
        String quizResult = "Quiz result ";
        quizResult += "\n---------------- ";
        quizResult += "\nName : " + fullName;
        quizResult += "\nEmail : " + idEmail;
        quizResult += "\nQuestion 1 : " + hasFrance + "  -- Correct answer is : France";
        quizResult += "\nQuestion 2 : " + hasPorto + " & " + hasInter + "  -- Correct answer is : Porto & Inter Milan";
        quizResult += "\nQuestion 3 : " + hasArgentina + "  -- Correct answer is : Argentina";
        quizResult += "\nQuestion 4 : " + hasCr7 + "  -- Correct answer is : 7";
        quizResult += "\nThank You";
        return quizResult;
    }


    /*
    * Submit quiz result handler
    */
    public void submitQuiz(View view) {

        //Show participant full name
        EditText textName = (EditText) findViewById(your_name_edittext);
        String pName = textName.getText().toString();
        String checkName = checkName(pName);

        // Show participant email
        EditText textEmail = (EditText) findViewById(your_email_edittext);
        String pEmail = textEmail.getText().toString();
        if (!isValidEmail(pEmail)) {
            Toast.makeText(this, "Error : Invalid email address ", Toast.LENGTH_SHORT).show();
            textEmail.requestFocus();
        } else {
            iCheckEmail = true;
        }

        /*
         * Checking Send result to email or not
         */
        CheckBox sendEmail = (CheckBox) findViewById(R.id.send_email_checkbox);
        if (sendEmail.isChecked() == true) {
            iSendMail = true;
        } else {
            iSendMail = false;
        }


        /*
        * show answer from question 1
        * This is correct answer for Question 1
        */
        RadioButton pakaiFrance = (RadioButton) findViewById(R.id.radio_fr);
        boolean iFrance = pakaiFrance.isChecked();

        /*
        * show answer from question 2
        * These are correct answer for Question 2 Porto & Inter Milan
        */
        CheckBox pakaiPorto = (CheckBox) findViewById(R.id.q2_porto);
        boolean iPorto = pakaiPorto.isChecked();

        CheckBox pakaiInter = (CheckBox) findViewById(R.id.q2_inter);
        boolean iInter = pakaiInter.isChecked();

        /*
        * Question 3
         */
        String pArgentina = new String("argentina");

        EditText textArgentina = (EditText) findViewById(R.id.messi_argentina);
        String pArg = textArgentina.getText().toString();
        boolean iArgentina = pArgentina.equalsIgnoreCase(pArg);


        /*
        * Question 4
         */
        String pCr7 = new String("7");

        EditText textCr7 = (EditText) findViewById(R.id.cr7_realMadrid);
        String pCristianoRonaldo = textCr7.getText().toString();
        boolean iCr7 = pCr7.equalsIgnoreCase(pCristianoRonaldo);

        /*
        * Final check before submission
         */
        if (iCheckName == true && iCheckEmail == true) {
            String displayResult = resultSummary(checkName, pEmail, iFrance, iPorto, iInter, iArgentina, iCr7);
            if (iSendMail == true) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+pEmail)); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "Result Quiz ");
                intent.putExtra(Intent.EXTRA_TEXT, displayResult);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            } else {
                displayMessage(displayResult);
            }
        } else {
            return;
        }
    }

    /**
     * Method displays the message on the screen
     */
    private void displayMessage(String message) {
        TextView quizResultTextView = (TextView) findViewById(R.id.id_quiz_result_textview);
        quizResultTextView.setText(message);
    }
}
