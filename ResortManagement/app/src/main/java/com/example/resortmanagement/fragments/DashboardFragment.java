package com.example.resortmanagement.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import android.app.Fragment;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resortmanagement.DashboardActivity;
import com.example.resortmanagement.LoginActivity;
import com.example.resortmanagement.R;
import com.example.resortmanagement.adapters.SimpleBaseAdapter;
import com.example.resortmanagement.constant.Constants;
import com.example.resortmanagement.constant.SQLCommand;
import com.example.resortmanagement.model.ListModel;
import com.example.resortmanagement.util.DBOperator;
import com.example.resortmanagement.util.UserData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment  implements View.OnClickListener {
    Button bookRooms;
    Button amenities;
    TextView welcome;
    ListView amenityList,bookingList;
    SimpleBaseAdapter amenityAdapter,bookingAdapter;
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id== R.id.book_rooms){
            Fragment rFragment = new BookingFragment();
            ((DashboardActivity)getActivity()).goToNewFragment(rFragment, Constants.BOOKING);
        } else if (id== R.id.amenities){
            Fragment rFragment = new AmenityFragment();
            ((DashboardActivity)getActivity()).goToNewFragment(rFragment,Constants.AMENITY);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        welcome=rootView.findViewById(R.id.welcome_txt);
        String welcomeUser = "Welcome "+UserData.getFirstName()+" "+UserData.getLastName();
        welcome.setText(welcomeUser);
        bookRooms=rootView.findViewById(R.id.book_rooms);
        bookRooms.setOnClickListener(this);
        amenities=rootView.findViewById(R.id.amenities);
        amenities.setOnClickListener(this);
        amenityList=rootView.findViewById(R.id.amenities_list);
        bookingList=rootView.findViewById(R.id.booking_list);
        bookingAdapter = new SimpleBaseAdapter(getActivity(),null);
        amenityAdapter = new SimpleBaseAdapter(getActivity(),null);
        amenityList.setAdapter(amenityAdapter);
        bookingList.setAdapter(bookingAdapter);

        amenityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Button button = view.findViewById(R.id.cancel);
                if(button.isEnabled()){
                    TextView textVw = view.findViewById(R.id.textView1);
                    String[] args = new String[1];
                    args[0] = textVw.getText().toString();
                    DBOperator.getInstance().delete("OrderAmn","orderID = ?",args);
                    Toast.makeText(getActivity(),"Order deleted", Toast.LENGTH_LONG).show();
                    String[] args1 = getArgs();
                    getAmenityHistory(args1);
                }
            }

        });

        bookingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Button button = view.findViewById(R.id.cancel);
                if(button.isEnabled()){
                    TextView textVw = view.findViewById(R.id.textView1);
                    String[] args = new String[1];
                    args[0] = textVw.getText().toString();
                    DBOperator.getInstance().delete("Booking","bookID= ?",args);
                    Toast.makeText(getActivity(),"Booking deleted", Toast.LENGTH_LONG).show();
                    String[] args1 = getArgs();
                    getBookingHistory(args1);
                }
            }

        });

        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        String[] args = getArgs();
        getBookingHistory(args);
        getAmenityHistory(args);

    }
    private void getAmenityHistory(String[] args){
        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_AMENITY_HISTORY,args);

        if((cursor != null) && (cursor.getCount() > 0)) {
            List<ListModel> values = new ArrayList<>();
            while (cursor.moveToNext())
            {
                String ordId = cursor.getString(0);
                String ordDate = cursor.getString(1);
                String servDate = cursor.getString(2);
                String desc = cursor.getString(3);
                Date serviceDate = null;
                try {
                    serviceDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US).parse(servDate);
                } catch (ParseException e) {
                    Log.e("Dashboard",""+e,e);
                }
                Date currentDate = new Date();
                String type="";
                switch (desc){
                    case "S":
                        type = "Spa";
                        break;
                    case "R":
                        type = "Restaurant";
                        break;
                    case "E":
                        type = "Excursion";
                        break;
                }
                boolean cancellable = serviceDate != null && serviceDate.after(currentDate);
                values.add(new ListModel(ordId,ordDate,servDate,type,cancellable));
            }
            amenityAdapter.setData(values);
            amenityList.invalidate();
            amenityAdapter.notifyDataSetChanged();
        }
    }
    private void getBookingHistory(String[] args){
        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.QUERY_BOOKING_HISTORY,args);

        if((cursor != null) && (cursor.getCount() > 0)) {
            List<ListModel> values = new ArrayList<>();
            while (cursor.moveToNext())
            {
                String bookId = cursor.getString(0);
                String roomNum = cursor.getString(1);
                String checkIn = cursor.getString(2);
                String checkOut = cursor.getString(3);
                Date checkinDate = null;
                try {
                    checkinDate=new SimpleDateFormat("yyyy-MM-dd",Locale.US).parse(checkIn);
                } catch (ParseException e) {
                    Log.e("Dashboard",""+e,e);
                }
                Date currentDate = new Date();
                boolean cancellable = checkinDate != null && checkinDate.after(currentDate);
                values.add(new ListModel(bookId,roomNum,checkIn,checkOut,cancellable));
            }
            bookingAdapter.setData(values);
            bookingList.invalidate();
            bookingAdapter.notifyDataSetChanged();
        }
    }
    private String[] getArgs(){
        String[] args = new String[1];
        args[0] = Integer.toString(UserData.getCustId());
        return args;
    }
}
