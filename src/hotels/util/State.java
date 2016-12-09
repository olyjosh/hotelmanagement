
package hotels.util;

/**
 *
 * @author NOVA
 */
public class State {
    
    
    public static final String COLOR_ALL = "#d3d1d1";
    public static final String COLOR_VACANT = "#6ed06d";
    public static final String COLOR_OCCUPIED = "#4a4545";
    public static final String COLOR_DIRTY = "#999a2c";
    public static final String COLOR_OUT_ORDER = "#f23838";
    public static final String COLOR_RESERVED = "#386fdd";
    public static final String COLOR_DUE_OUT = "#c900b5";
    
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
    public final static String channel_POS = "pos";
    public final static String channel_BANK_TRANSFER = "bank"; 
    
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
    
    //ORder status for bar and restaurant
    public final static int ORDER_PENDING = 0;
    public final static int ORDER_CANCEL = 1;
    public final static int ORDER_PREPARING = 2;
    public final static int ORDER_DONE = 3;
    
    //STATUS 
    public final static String STATUS_C = "Complete";
    public final static String STATUS_P = "Pending";
    public final static String STATUS_R = "Returned";
    public final static String STATUS_N = "Not Completed";
    
    //Payment purpose
    public final static int payment_BOOK = 0;
    public final static int payment_LAUNDRY = 1;
    public final static int payment_FOOD = 2;
    public final static int payment_DRINK = 3;
    public final static int payment_MISC_SALES = 4;
    public final static int payment_CLEAR_BILL = 5;

    //Department
    public final static int DEPT_FRONT = 0;
    public final static int DEPT_RESTAURANT = 1;
    public final static int DEPT_BAR = 2;
    public final static int DEPT_LAUNDRY = 3;
    public final static int DEPT_OTHER = 4;
    
    public static String department(int x ){
        switch(x){
            case  DEPT_FRONT : return "Front Office - Booking and Room sales";
            case  DEPT_RESTAURANT : return "Restaurant";
            case  DEPT_BAR : return "Bar";
            case  DEPT_LAUNDRY : return "Laundry";
            case  DEPT_OTHER : return "Others - Misc. Sales";
        }
        return null;
    }

    //Pay roll 
    public final static String ADDITION = "ADDITION";
    public final static String DEDUCTION = "DEDUCTION";
    
    public final int NIGERIA= 164;
    
}
    


    
