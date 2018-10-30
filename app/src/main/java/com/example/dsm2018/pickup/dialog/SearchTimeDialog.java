package com.example.dsm2018.pickup.dialog;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.dsm2018.pickup.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SearchTimeDialog extends AppCompatActivity {

    NumberPicker timeZonePicker, hourPicker, minutePicker;
    Calendar calendar;
    int timeZone, hour, minute;
    Button cancelButton, selectionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search_time);
        calendar = new GregorianCalendar(Locale.KOREA);

        timeZonePicker = findViewById(R.id.timeZonePicker);
        hourPicker = findViewById(R.id.hourPicker);
        minutePicker = findViewById(R.id.minutePicker);
        cancelButton = findViewById(R.id.cancelButton);
        selectionButton = findViewById(R.id.selectionButton);

        initNumberPicker();

        cancelButton.setOnClickListener(v-> finish());

        selectionButton.setOnClickListener(v-> {
            Intent intent = new Intent();

            int hour = hourPicker.getValue();
            if(timeZonePicker.getValue() == 1)
                hour += 12;

            intent.putExtra("party_hour", String.valueOf(hour));
            intent.putExtra("party_minute", String.valueOf(minutePicker.getValue()));

            setResult(103, intent);
            finish();
        });
    }

    public void initNumberPicker(){
        timeZone = calendar.get(Calendar.AM_PM);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);

        String[] hourDisplay = new String[12];
        String[] minuteDisplay = new String[59];

        for(int i = 1; i < 13; i++){
            hourDisplay[i - 1] = i + "시";
        }

        for(int i = 1; i < 60; i++){
            minuteDisplay[i - 1] = i + "분";
        }

        setDividerColor(timeZonePicker, Color.TRANSPARENT);
        setDividerColor(hourPicker, Color.TRANSPARENT);
        setDividerColor(minutePicker, Color.TRANSPARENT);

        timeZonePicker.setMinValue(0);
        timeZonePicker.setMaxValue(1);
        timeZonePicker.setValue(timeZone);
        timeZonePicker.setDisplayedValues(new String[]{"AM", "PM"});

        hourPicker.setMinValue(1);
        hourPicker.setMaxValue(12);
        hourPicker.setValue(hour);
        hourPicker.setDisplayedValues(hourDisplay);

        minutePicker.setMinValue(1);
        minutePicker.setMaxValue(59);
        minutePicker.setValue(minute);
        minutePicker.setDisplayedValues(minuteDisplay);
    }

    private void setDividerColor(NumberPicker picker, int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
