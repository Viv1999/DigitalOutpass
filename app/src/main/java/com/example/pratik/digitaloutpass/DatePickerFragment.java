package com.example.pratik.digitaloutpass;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText etLeaveDate;
    Calendar calendar;

    static DatePickerFragment newInstance(EditText etLeaveDate, Calendar calendar){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.etLeaveDate = etLeaveDate;
        datePickerFragment.calendar = calendar;
        return datePickerFragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        etLeaveDate.setText(dayOfMonth+ "/"+month+"/"+ year);
        calendar.set(year, month-1, dayOfMonth);
    }
}
