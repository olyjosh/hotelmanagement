package hotels.util;

/**
 *
 * @author Olyjosh
 */
public class Storage {
    
    public static String auth_token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1N2ZhM2Q5NGZkZjEzYjFiMTQzZDJiOGMiLCJlbWFpbCI6ImZkZ2ZkZ2RmIiwibmFtZSI6eyJ1c2VybmFtZSI6ImhhbGVlbWFoIiwiZmlyc3ROYW1lIjoiaGFsZWVtYWgiLCJsYXN0TmFtZSI6Im11c3RhcGhhIn0sImV4cCI6MTQ3NjYyMjM1NiwiaWF0IjoxNDc2MDE3NTU2fQ.Ztr1noCiho05sHqf33O3jRJieCzxW-W0NtlIUBaEpU4";
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
