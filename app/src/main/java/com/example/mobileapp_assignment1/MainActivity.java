package com.example.mobileapp_assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText mortgageAmount, tenure, intereset;//variable for edittext fields
    TextView text;
    double mortgage, tenureAmount, interestNum;//variable to store value and send to nexxt activity
    private Button calculateButton;//variable for 'calculate' button on main activity
    boolean validation = false;
    String spinnerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner spinnerInterst = findViewById(R.id.intrestDropdown);//initialize spiiner
        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.interestType, android.R.layout.simple_spinner_item);//initialize adapter for spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);//drop down view resource is being set for the adapter
        spinnerInterst.setAdapter(adapter);//setting adapter
        spinnerInterst.setOnItemSelectedListener(this);//listens to the adapter (clicklistner)

        //intializing
        mortgageAmount = (EditText)findViewById(R.id.mortgage);
        tenure = (EditText)findViewById(R.id.tenure);
        intereset = (EditText)findViewById(R.id.hiddenInterest);
        calculateButton = (Button) findViewById(R.id.calculate);

        calculateButton.setOnClickListener(new View.OnClickListener() {

            //when 'calculate' button is pressed it will validate the inputs and send the inputs to the function
            @Override
            public void onClick(View view) {
                //converting the string into a double
                mortgage = Double.parseDouble(mortgageAmount.getText().toString());
                tenureAmount = Double.parseDouble(tenure.getText().toString());

                //checks to see of the hidden fieldtect is visible and if it is then that means user inputted thier own interest
                //takes user input and converts it to a double
                if(text.getVisibility()==View.VISIBLE){
                    interestNum=Double.parseDouble(intereset.getText().toString());
                }
                else {
                    interestNum = Double.parseDouble(spinnerText);
                }

                //validates the user inputs and sends a toast message to let the user know
                 if (mortgage<20000||mortgage>9000000) {
                    Toast.makeText(getBaseContext(),"Enter Mortgage amount greater than $20,000 and less than $9,000,000",Toast.LENGTH_LONG).show();
                } else if (tenureAmount<0.5) {
                    Toast.makeText(getBaseContext(),"Please ensure Tenure is greater than 0.5 years",Toast.LENGTH_LONG).show();

                } else if (((text.getVisibility()==View.VISIBLE )&&interestNum==0)) {
                     Toast.makeText(getBaseContext(),"Please ensure Interest is greater than 0",Toast.LENGTH_LONG).show();

                 }  else{

                    openActivity2(mortgage, tenureAmount,interestNum);}//calls function
            }

        });

    }

    //method is used to determine what value is being selected from the dropdown list
    //taking the interest value from the dropdown selection
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        String dropdownValue = parent.getItemAtPosition(position).toString();//gets the current selection and turns it to a string

        //checks to see if 'add own rate' is selected
        //if it is selected then the EditText view will then appear for the user to enter the interest rate otherwise it stays invisible
        if(dropdownValue.matches("Add my own rate")){
            text=(TextView)findViewById(R.id.hiddenInterest);
            if(text.getVisibility()==View.INVISIBLE)
                text.setVisibility(View.VISIBLE);

        }
        //since 'add own interest' is not selected, it will then set the hidden editetxt view to invisible
        //it will get the current selected value in the dropdown and turn it to a string
        else{
            text=(TextView)findViewById(R.id.hiddenInterest);
            text.setVisibility(View.INVISIBLE);
            spinnerText = parent.getItemAtPosition(position).toString();
            String segments[] = spinnerText.split(":");//splitting the string from ':' to get the value
            spinnerText = segments[segments.length-1];//takes the percentage number
            spinnerText.trim();//deletes any whitespace before and after
            String segments2[] = spinnerText.split("%");//splits string again from '%'
            spinnerText = segments2[0];//takes just the number itself for interest
            spinnerText.trim();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    //sending the parameters to the next activity
    //takes mortgage, tenure and interest as inputs
    public void openActivity2(double m, double t, double i){
        Intent intent = new Intent(this, Activity2.class);//create new intent that will open Mainactivity2 class

        //putExtra meethod is used to send the mortgage, tenure and interest values to the next activity
        //the method will take a string that acts as a key which will be used to recieve the values in the next activity it is being sent to
        intent.putExtra("message_key",m);
        intent.putExtra("message_key2",t);
        intent.putExtra("message_key3",i);
        startActivity(intent);//starts activity
    }


}