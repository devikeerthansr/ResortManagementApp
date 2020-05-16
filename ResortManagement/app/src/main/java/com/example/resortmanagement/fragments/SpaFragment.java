package com.example.resortmanagement.fragments;

import android.app.Fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.resortmanagement.R;
import com.example.resortmanagement.ReserveMenu;
import com.example.resortmanagement.constant.SQLCommand;
import com.example.resortmanagement.util.DBOperator;
import com.example.resortmanagement.util.UserData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SpaFragment extends Fragment implements OnClickListener {
    private Button submit_spa;
    DatePicker serviceDate;
    EditText comment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.spa_page, container, false);
        submit_spa=(Button)rootView.findViewById(R.id.submit_spa);
        submit_spa.setOnClickListener(this);
        serviceDate=rootView.findViewById(R.id.datePicker1);
        comment=rootView.findViewById(R.id.comment);
        return rootView;
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==R.id.submit_spa){
            String[] args = new String[1];
            int bookingId=0;
            args[0] = Integer.toString(UserData.getCustId());
            Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_BOOKING_ID,args);
            if(cursor !=null && cursor.getCount()>0) {
                while (cursor.moveToNext()) {
                    String bookId = cursor.getString(0);
                    bookingId = Integer.parseInt(bookId);
                }
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = dateFormat.format(date);

                int year=serviceDate.getYear();
                int month=serviceDate.getMonth();
                int day=serviceDate.getDayOfMonth();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                //format the date
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String checkinDate = dateFormat1.format(calendar.getTime());

                ContentValues insertValues = new ContentValues();
                insertValues.put("orderDate", strDate);
                insertValues.put("serviceDate", checkinDate);
                insertValues.put("comment", comment.getText().toString());
                insertValues.put("bookID", bookingId);
                insertValues.put("amID", "A1");
                DBOperator.getInstance().insert(insertValues,"OrderAmn");

                Toast.makeText(getActivity(), "Spa appointment booked", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        }

    }
}
