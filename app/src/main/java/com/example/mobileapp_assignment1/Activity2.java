package com.example.mobileapp_assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Activity2 extends AppCompatActivity {

    TextView receiver_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);


        Intent intent = getIntent();//creating the object for get intent

        //getting the values by using getDoubleExtra method that will match the ket that was in putExtra sent by mainactivity and set a default value for the variable to 0
        Double mortgage = intent.getDoubleExtra("message_key",0);
        Double tenure = intent.getDoubleExtra("message_key2",0);
        Double interest = intent.getDoubleExtra("message_key3",0);
        interest=interest/100;//converting percentage to decimal
        interest=interest/12;//getting the interest for each month
        tenure=tenure*12;//getting the total number of monthly payments

        //using the formula to find EMI
        //EMI=[P x R x (1+R)^N]/[(1+R)^N-1]   p=mortgage, r=interest, n=tenure
        double calc = Math.pow(1+interest, tenure);//calculating the exponent part first
        calc=(mortgage*interest*calc)/(calc-1);

        //formatting the decimals for the calc variable to 2 decimal places
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedcalc = df.format(calc);
        calc = Double.parseDouble(formattedcalc);

        //getting original values back to print
        tenure=tenure/12;
        interest= interest*12;
        interest=interest*100;

        //displaying text to the given textview from the id
        receiver_msg = findViewById(R.id.output);
        receiver_msg.setText("Your Mortgage amount is:"+mortgage.toString()+"\n"+"Your Tenure is:"+tenure.toString()+"\n"+"Your interest rate is: %"+interest.toString()+"\n"+"Your EMI is $" +calc);




    }
}