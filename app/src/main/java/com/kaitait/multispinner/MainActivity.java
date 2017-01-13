package com.kaitait.multispinner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitialiseSpinners();
    }

    private void InitialiseSpinners()
    {
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        InitialiseDelayMultiSpinner(
                getApplicationContext(),
                spinner1,
                spinner2,
                spinner3,
                1567);
    }

    public void InitialiseDelayMultiSpinner(Context context, Spinner spinner1, Spinner
            spinner2, Spinner spinner3, int initial_value_in_minutes)
    {
        List<String> minutes = new ArrayList<>();
        List<String> hours = new ArrayList<>();
        List<String> days = new ArrayList<>();
        for (int i = 0; i < 60; i++)
        {
            minutes.add(i + " minutes");
        }
        for (int i = 0; i < 24; i++)
        {
            hours.add(i + " hours");
        }
        for (int i = 0; i < 7; i++)
        {
            days.add(i + " days");
        }

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, minutes);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, hours);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, days);

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter1);
        spinner2.setAdapter(dataAdapter2);
        spinner3.setAdapter(dataAdapter3);

        HashMap<String, Integer>
                delay_spinner_values = GetDelaySpinnerValues(initial_value_in_minutes);
        spinner1.setSelection(delay_spinner_values.get("minutes"));
        spinner2.setSelection(delay_spinner_values.get("hours"));
        spinner3.setSelection(delay_spinner_values.get("days"));
    }

    private static HashMap<String, Integer> GetDelaySpinnerValues(int delay_in_minutes)
    {
        HashMap<String, Integer> delay_spinner_values = new HashMap<>();

        // less than an hour
        if (delay_in_minutes < 60)
        {
            // set minutes
            delay_spinner_values.put("minutes", delay_in_minutes);
            delay_spinner_values.put("hours", 0);
            delay_spinner_values.put("days", 0);

        }

        //less than a day
        else if (delay_in_minutes < 1440)
        {
            // it's less than day
            if (delay_in_minutes % 60 == 0)
            {
                //set hours
                delay_spinner_values.put("minutes", 0);
                delay_spinner_values.put("hours", delay_in_minutes / 60);
                delay_spinner_values.put("days", 0);
            }
            else
            {
                // set hours & minutes
                delay_spinner_values.put("minutes", delay_in_minutes % 60);
                delay_spinner_values.put("hours", delay_in_minutes / 60);
                delay_spinner_values.put("days", 0);
            }
        }

        // more than a day
        else
        {
            //its more than a day
            if (delay_in_minutes % 1440 == 0)
            {
                // set days & minutes
                delay_spinner_values.put("minutes", 0);
                delay_spinner_values.put("hours", 0);
                delay_spinner_values.put("days", delay_in_minutes / 1440);
            }
            else if (delay_in_minutes % 60 == 0)
            {
                // set days & hours
                delay_spinner_values.put("minutes", 0);
                delay_spinner_values.put("hours", delay_in_minutes % 1440 / 60  );
                delay_spinner_values.put("days", delay_in_minutes / 1440);
            }
            else
            {
                // set days minutes & hours
                delay_spinner_values.put("minutes", delay_in_minutes % 1440 % 60);
                delay_spinner_values.put("hours", delay_in_minutes % 1440 / 60);
                delay_spinner_values.put("days", delay_in_minutes / 1440);
            }
        }
        System.out.println("____" + delay_spinner_values);
        return delay_spinner_values;
    }
}
