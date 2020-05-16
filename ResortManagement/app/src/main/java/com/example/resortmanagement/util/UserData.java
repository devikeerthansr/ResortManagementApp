package com.example.resortmanagement.util;

import android.database.sqlite.SQLiteDatabase;

import com.example.resortmanagement.constant.DBConstant;

public class UserData
{
    private static  int custId;
    private static String firstName;
    private static String lastName;


    public static int getCustId() {
        return custId;
    }

    public static void setCustId(int custId) {
        UserData.custId = custId;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        UserData.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        UserData.lastName = lastName;
    }
}