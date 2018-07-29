package com.example.dsm2018.pickup.fragment;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.example.dsm2018.pickup.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SearchDateDialogFragment extends DialogFragment{

    NumberPicker yearPicker, monthPicker, dayPicker;
    int year, month, day;
    Calendar calendar;

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_height);
        getDialog().getWindow().setLayout(width, height);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_search_date, container, false);

        calendar = new GregorianCalendar(Locale.KOREA);

        yearPicker = view.findViewById(R.id.yearPicker);
        monthPicker = view.findViewById(R.id.monthPicker);
        dayPicker = view.findViewById(R.id.dayPicker);

        numberPickerInit();

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                year = newVal;
                setDayPicker();
            }
        });
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                month = newVal;
                setDayPicker();
            }
        });
        dayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                day = newVal;
                setDayPicker();
            }
        });

        return view;
    }

    public void numberPickerInit(){
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);

        String[] displayYear = new String[11];
        String[] displayMonth = new String[12];
        String[] displayDay = new String[31];

        for(int i = 0, j = -5; i < 11; i++, j++){
                displayYear[i] = year + j + "년";
        }

        for(int i = 1; i < 13; i++){
            displayMonth[i - 1] = i + "월";
        }

        for(int i = 1; i < 32; i++){
            displayDay[i - 1] = i + "일";
        }

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

    public void setDayPicker(){
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
                dayPicker.setMaxValue(31);
                break;
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
