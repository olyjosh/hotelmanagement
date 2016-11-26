package hotels.util;

/**
 *
 * @author Olyjosh
 */
public class Storage {
    
    public static String auth_token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ODFhZjU0MmRlNTNhNzAxZjE2MWNkNzgiLCJlbWFpbCI6IjFAMS5jb20iLCJuYW1lIjp7InVzZXJuYW1lIjoiMTIzNDUiLCJmaXJzdE5hbWUiOiJCYWJhIiwibGFzdE5hbWUiOiJNYW1hIn0sImV4cCI6MTQ3ODc2NjUzMiwiaWF0IjoxNDc4MTYxNzMyfQ.l_URJZilJGrO5hd3B7xiDzdbDB2ljQailKKfuLqwPkM";
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
