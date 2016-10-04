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
    
    public final static String RM_VACANT = "vacant";

    public final static String RM_BOOKED = "Booking";

    public final static String RM_OCCUPIED = "Occupied";

    public final static String RM_RESERVED = "Reservation";

    public final static String RM_DUEOUT = "due";

    public final static String RM_DIRTY = "dirty";

    public final static String RM_DISORDER = "disOrd";

    public final static String RM_GOOD = "ok";
    
    public final static String channel_ONLINE = "online";

    public final static String channel_Mobile = "mobile";

    public final static String channel_FRONT = "front";
    
    //=============Notification Constants=======
    
    public final static String NOTIFY_SUCCESS = "Operation Successful";
    public final static String NOTIFY_ERROR = "Error in Operation, process Aborted";
    public final static String NOTIFY_WARNING = "Processing.....Continue";
    public final static String NOTIFY_BOOKING = "Booking Notification";
    public final static String NOTIFY_CHECKIN = "Checkin Notification";
    public final static String NOTIFY_RESERVE = "Reservation Notification";
    
}
