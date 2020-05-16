package com.example.resortmanagement.constant;

public abstract class SQLCommand
{

    public static String QUERY_LOGIN = "SELECT custID,custFName,custLName \n" +
            "FROM Customer\n" +
            "WHERE custUsername  = ? AND custPassword  = ?" ;
    public static String QUERY_BOOKING_HISTORY = "SELECT Booking.bookID,Booking.roomNum,Booking.bookCheckIn,Booking.bookCheckOut\n" +
            "FROM Customer,Booking \n" +
            "WHERE Customer.custID = Booking.custID AND Customer.custID = ?\n"+
            "ORDER BY Booking.bookCheckIn DESC";

    public static String QUERY_AMENITY_HISTORY = "SELECT OrderAmn.orderID ,OrderAmn.orderDate ,OrderAmn.serviceDate ,Amenity.amType\n" +
            "FROM OrderAmn,Amenity,Customer,Booking \n" +
            "WHERE Customer.custID = Booking.custID AND Customer.custID = ? AND OrderAmn.bookID = Booking.bookID AND OrderAmn.amID =Amenity.amID \n"+
            "ORDER BY OrderAmn.orderDate DESC";

    public static String QUERY_ROOM_PRICE = "SELECT typePrice \n" +
            "FROM RoomType\n" +
            "WHERE typeID = ?\n";

    public static String QUERY_ROOM_NUM = "SELECT roomNum \n" +
            "FROM Room\n" +
            "WHERE typeID = ?\n";

    public static String QUERY_BOOKING_ID = "SELECT Booking.bookID\n" +
            "FROM Customer,Booking \n" +
            "WHERE Customer.custID = Booking.custID AND Customer.custID = ?";

public static String QUERY_DELETE_ORDER= "DELETE FROM OrderAmn\n"+
            "WHERE orderID = ?";
public static String QUERY_DELETE_BOOKING= "DELETE FROM Booking\n"+
            "WHERE bookID= ?";



}
