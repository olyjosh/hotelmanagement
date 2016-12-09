package hotels.util;

/**
 *
 * @author Olyjosh
 */
public class Storage {
    
    public static String auth_token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ODQwZGRlNzdhZTEwMGQ3YWQ1NzY4ZjMiLCJlbWFpbCI6ImhhY2tjdHJsc2hpZnRkZWxAZ21haWwuY29tIiwibmFtZSI6eyJ1c2VybmFtZSI6ImFkbWluIiwiZmlyc3ROYW1lIjoiSm9zaHVhIiwibGFzdE5hbWUiOiJBcm9rZSJ9LCJleHAiOjE0ODE4NTU3MjEsImlhdCI6MTQ4MTI1MDkyMX0.qzeGFzv56xG_UQ1Y2Js8be-oCtCofFo8uPNd21H0WBU";
    private static String id = "5840c390b6a578be16b5446a";
    
    private static int tax = 5;
    
    
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

    public static void setTax(int tax) {
        Storage.tax = tax;
    }

    public static int getTax() {
        return tax;
    }
    
    
    
    
    
    
}
