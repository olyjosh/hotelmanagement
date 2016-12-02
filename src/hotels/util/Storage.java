package hotels.util;

/**
 *
 * @author Olyjosh
 */
public class Storage {
    
    public static String auth_token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ODQwYzM5MGI2YTU3OGJlMTZiNTQ0NmEiLCJlbWFpbCI6Im9seWpvc2hvbmVAZ21haWwuY29tIiwibmFtZSI6eyJ1c2VybmFtZSI6Im9seWpvc2giLCJmaXJzdE5hbWUiOiJKb3NodWEiLCJsYXN0TmFtZSI6IkFyb2tlIn0sImV4cCI6MTQ4MTI0OTAzNCwiaWF0IjoxNDgwNjQ0MjM0fQ.74N5-8pSZ5uX1Acqlv0CohjHD9lsyLgizclJUgqt1jg";
    private static String id = "5840c390b6a578be16b5446a";
    
    public static String getAuth_token() {
        return auth_token;
    }

    public static void setAuth_token(String auth_token) {
        Storage.auth_token = auth_token;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        Storage.id = id;
    }
           
    public static String floors(){
        return null;
    }
    
    
    
}
