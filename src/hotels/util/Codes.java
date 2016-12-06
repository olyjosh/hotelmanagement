/*
 * This class contains color codes as used in the fxml 
 */

package hotels.util;

import hotels.Hotels;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

/**
 *
 * @author Olyjosh
 */
public class Codes {
    
    private static Hotels app;
    
    public Codes(){
        
        Navigator nav  = new Navigator(app.getMain());
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("username", "admin"));
        param.add(new BasicNameValuePair("password", "12345"));
        
        JSONObject login = nav.login(param);
        System.out.println(login);
    }
    
    public static void main(String[] args) {
       
        new Codes();
    }
    
    
}
