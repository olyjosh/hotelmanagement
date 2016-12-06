package hotels.util;

/**
 *
 * @author Olyjosh
 */
public class Storage {
    
    public static String auth_token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ODQwYmNlYzg2NmMxNTA1YmM4ZjUyMDkiLCJlbWFpbCI6ImRzZmRzZmRzIiwibmFtZSI6eyJ1c2VybmFtZSI6InZzZHNkIiwiZmlyc3ROYW1lIjoic2Rmc2ZzIiwibGFzdE5hbWUiOiJkc2Zkc2YifSwiZXhwIjoxNDgxMjQyNDgzLCJpYXQiOjE0ODA2Mzc2ODN9.ztYp1sq6O7TR2TMlKqFFE9EWdTArxYn_h1ZT_KJcZM0";
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
