package com.example.dsm2018.pickup.dialog;

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
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.dsm2018.pickup.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SearchTimeDialog extends DialogFragment {

    NumberPicker timeZonePicker, hourPicker, minutePicker;
    Calendar calendar;
    int timeZone, hour, minute;
    Button cancelButton;

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
        View view = inflater.inflate(R.layout.dialog_search_time, container, false);

        calendar = new GregorianCalendar(Locale.KOREA);

        timeZonePicker = (NumberPicker)view.findViewById(R.id.timeZonePicker);
        hourPicker = (NumberPicker)view.findViewById(R.id.hourPicker);
        minutePicker = (NumberPicker)view.findViewById(R.id.minutePicker);
        cancelButton = (Button)view.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        initNumberPicker();

        return view;
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
