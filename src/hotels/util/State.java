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
    
    
    public final static String NOTIFY_SUCCESS = "Operation Successful";
    public final static String NOTIFY_ERROR = "Error in Operation, process Aborted";
    public final static String NOTIFY_WARNING = "Processing.....Continue";
    public final static String NOTIFY_BOOKING = "Booking Notification";
    public final static String NOTIFY_CHECKIN = "Checkin Notification";
    public final static String NOTIFY_RESERVE = "Reservation Notification";
    
    
    //USERS PRIVILEDGES
    public final static int USER_SUPER_ADMIN = 0;
    public final static int USER_ADMIN = 1;
    public final static int USER_ADMIN_2 = 2;
    public final static int USER_FRONT = 3;
    public final static int USER_HOUSEKEEP = 4;
    public final static int USER_LAUNDRY = 5;
    public final static int USER_KITCHEN = 6;
    public final static int USER_MINIBAR = 7;
    public final static int USER_MAINTENANCE = 8;
    public final static int USER_MAID = 9;
    public final static int USER_BARTENDER = 10;
}
    


    
