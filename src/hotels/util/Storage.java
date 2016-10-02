package hotels.util;

/**
 *
 * @author Olyjosh
 */
public class Storage {
    
    public static String auth_token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1N2RlY2E1ZDM1ZmI5YTQ4N2JkZWI3MGYiLCJlbWFpbCI6Im9seWpvc2hvbmVAZ21haWwuY29tIiwibmFtZSI6eyJ1c2VybmFtZSI6ImFkbWluIiwiZmlyc3ROYW1lIjoiQWRtaW4iLCJsYXN0TmFtZSI6IkFkbWluIn0sImV4cCI6MTQ3NTk2MjY3MywiaWF0IjoxNDc1MzU3ODczfQ.yOmyHQwDjSvES7OY0DsjCPWjL0zIBVtU7NZvpFX53V0";
    private static String id = "";
    
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
