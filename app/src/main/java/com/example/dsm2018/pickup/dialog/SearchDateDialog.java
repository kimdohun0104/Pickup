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

public class SearchDateDialog extends AppCompatActivity {

    NumberPicker yearPicker, monthPicker, dayPicker;
    Calendar calendar;
    Button cancelButton, selectionButton;
    int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search_date);

        calendar = new GregorianCalendar(Locale.KOREA);

        yearPicker = findViewById(R.id.yearPicker);
        monthPicker = findViewById(R.id.monthPicker);
        dayPicker = findViewById(R.id.dayPicker);
        cancelButton = findViewById(R.id.cancelButton);
        selectionButton = findViewById(R.id.selectionButton);

        cancelButton.setOnClickListener(v-> finish());

        selectionButton.setOnClickListener(v-> {
            Intent intent = new Intent();

            intent.putExtra("party_year", String.valueOf(yearPicker.getValue()));
            intent.putExtra("party_month", String.valueOf(monthPicker.getValue()));
            intent.putExtra("party_day", String.valueOf(dayPicker.getValue()));

            setResult(102, intent);
            finish();
        });

        numberPickerInit();

        yearPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            year = newVal;
            setDayPicker();
        });
        monthPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            month = newVal;
            setDayPicker();
        });
        dayPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            day = newVal;
            setDayPicker();
        });
    }

    public void numberPickerInit(){
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);

        String[] displayYear = new String[11];
        String[] displayMonth = new String[12];
        String[] displayDay = new String[31];

        for(int i = 0, j = -5; i < 11; i++, j++)
            displayYear[i] = year + j + "년";

        for(int i = 1; i < 13; i++)
            displayMonth[i - 1] = i + "월";


        for(int i = 1; i < 32; i++)
            displayDay[i - 1] = i + "일";


        setDividerColor(yearPicker, Color.TRANSPARENT);
        setDividerColor(monthPicker, Color.TRANSPARENT);
        setDividerColor(dayPicker, Color.TRANSPARENT);

        yearPicker.setMinValue(year - 5);
        yearPicker.setMaxValue(year + 5);
        yearPicker.setValue(year);
        yearPicker.setDisplayedValues(displayYear);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(month);
        monthPicker.setDisplayedValues(displayMonth);

        dayPicker.setMinValue(1);
        setDayPicker();
        dayPicker.setValue(day);
        dayPicker.setDisplayedValues(displayDay);
    }

    private void setDayPicker(){
        if(month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                dayPicker.setMaxValue(29);
                return;
            } else {
                dayPicker.setMaxValue(28);
                return;
            }
        }
        switch (month){
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                dayPicker.setMaxValue(31); break;
            default:
                dayPicker.setMaxValue(30);
        }
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
