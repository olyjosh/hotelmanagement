package hotels.util;

/**
 *
 * @author Olyjosh
 */
public class Storage {
    
    public static String auth_token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1N2RlY2E1ZDM1ZmI5YTQ4N2JkZWI3MGYiLCJlbWFpbCI6Im9seWpvc2hvbmVAZ21haWwuY29tIiwibmFtZSI6eyJ1c2VybmFtZSI6ImFkbWluIiwiZmlyc3ROYW1lIjoiQWRtaW4iLCJsYXN0TmFtZSI6IkFkbWluIn0sImV4cCI6MTQ3NjU3ODUzNywiaWF0IjoxNDc1OTczNzM3fQ.NlKE2vATgU7lmXBxLOHMl2oNVjtLhWHI0su-FhGECik";
    private static String id = "57deca5d35fb9a487bdeb70f";
    
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
