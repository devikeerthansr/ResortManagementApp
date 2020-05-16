package com.example.resortmanagement.fragments;

import android.app.Fragment;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resortmanagement.DashboardActivity;
import com.example.resortmanagement.R;
import com.example.resortmanagement.constant.Constants;
import com.example.resortmanagement.constant.SQLCommand;
import com.example.resortmanagement.util.DBOperator;
import com.example.resortmanagement.util.UserData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookingFragment extends Fragment  implements View.OnClickListener {
    DatePicker checkIn,checkOut;
    TextView priceTxt;
    Spinner typeSpinner,roomSpinner;
    Button book;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.booking_page, container, false);
        book=rootView.findViewById(R.id.book_rooms);
        checkIn=rootView.findViewById(R.id.datePicker1);
        checkOut=rootView.findViewById(R.id.datePicker2);
        priceTxt=rootView.findViewById(R.id.price);
        typeSpinner=rootView.findViewById(R.id.type_spinner);
        roomSpinner=rootView.findViewById(R.id.room_spinner);
        book.setOnClickListener(this);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String type="";
                switch(position){
                    case 0:
                        type = "T1";
                        break;
                    case 1:
                        type = "T2";
                        break;
                    case 2:
                        type = "T3";
                        break;
                    case 3:
                        type = "T4";
                        break;
                    case 4:
                        type = "T5";
                        break;
                }
                String[] args = new String[1];
                args[0] = type;
                Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_ROOM_PRICE,args);
                if(cursor !=null && cursor.getCount()>0) {
                    while (cursor.moveToNext()) {
                        String price = cursor.getString(0);
                        priceTxt.setText("$"+price);
                    }
                }
                String[] args1 = new String[1];
                args1[0] = type;
                Cursor cursor1 = DBOperator.getInstance().execQuery(SQLCommand.QUERY_ROOM_NUM,args1);
                List<String> roomNums = new ArrayList<>();
                if(cursor1 !=null && cursor1.getCount()>0) {
                    while (cursor1.moveToNext()) {
                        String room = cursor1.getString(0);
                        roomNums.add(room);
                    }
                    final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                            getActivity(),R.layout.spinner_item,roomNums);

                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                    roomSpinner.setAdapter(spinnerArrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        return rootView;
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id== R.id.book_rooms){
            int year=checkIn.getYear();
            int month=checkIn.getMonth();
            int day=checkIn.getDayOfMonth();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            //format the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String checkinDate = dateFormat.format(calendar.getTime());

            int year1=checkOut.getYear();
            int month1=checkOut.getMonth();
            int day1=checkOut.getDayOfMonth();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(year1, month1, day1);
            //format the date
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            String checkoutDate = dateFormat1.format(calendar1.getTime());

            ContentValues insertValues = new ContentValues();
            insertValues.put("bookCheckIn", checkinDate);
            insertValues.put("bookCheckOut", checkoutDate);
            insertValues.put("CustID", UserData.getCustId());
            insertValues.put("roomNum", roomSpinner.getSelectedItem().toString());
            DBOperator.getInstance().insert(insertValues,"Booking");

            Toast.makeText(getActivity(), "Room booked", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }
    }
    }
