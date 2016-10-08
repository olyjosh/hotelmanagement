/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.util;

/**
 *
 * @author NOVA
 */
public class State {
    
    //========Front Desk Constants===========
    
    public static String RM_VACANT = "vacant";
    public static String RM_BOOKED = "Booking";
    public static String RM_OCCUPIED = "Occupied";
    public static String RM_RESERVED = "Reservation";
    public static String RM_DUEOUT = "due";
    public static String RM_DIRTY = "dirty";
    public static String RM_DISORDER = "disOrd";
    public static String RM_GOOD = "ok";
    public static String channel_ONLINE = "online";
    public static String channel_Mobile = "mobile";
    public static String channel_FRONT = "front";
    
    //=============Notification Constants=======
    
    public static String NOTIFY_SUCCESS = "Operation Successful";
    public static String NOTIFY_ERROR = "Error in Operation, process Aborted";
    public static String NOTIFY_WARNING = "Processing.....Continue";
    public static String NOTIFY_BOOKING = "Booking Notification";
    public static String NOTIFY_CHECKIN = "Checkin Notification";
    public static String NOTIFY_RESERVE = "Reservation Notification";
    
    //============User Role Constants=================
    
    public static int USER_SUPER_ADMIN = 0;
    public static int USER_ADMIN = 1;
    public static int USER_ADMIN_2 = 2;
    public static int USER_FRONT = 3;
    public static int USER_HOUSEKEEP = 4;
    public static int USER_LAUNDRY = 5;
    public static int USER_KITCHEN = 6;
    public static int USER_MINIBAR = 7;
    public static int USER_MAINTENANCE = 8;
    
    //============Status Constants=====================
    
    public static String STATUS_P = "Pending";
    public static String STATUS_C = "Completed";
    public static String STATUS_R = "Returned";
    public static String STATUS_N = "Not yet Returned";
    
}
