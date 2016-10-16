package hotels.util;

/**
 *
 * @author Olyjosh
 */
public class Storage {
    
    public static String auth_token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ODAzNzhhN2E3YTZiOTZjNjVhNjg1MjQiLCJlbWFpbCI6ImhqaGpnamciLCJuYW1lIjp7InVzZXJuYW1lIjoiYWJ1IiwiZmlyc3ROYW1lIjoiZ2hqZ2hqZ2hqaiIsImxhc3ROYW1lIjoiamdqZ2pmamZnaiJ9LCJleHAiOjE0NzcyMjczMDMsImlhdCI6MTQ3NjYyMjUwNH0.sAXTszvmAmUNqGPBpWWRl_wnHQWfTaP3iP2Bl1FyQTg";
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
