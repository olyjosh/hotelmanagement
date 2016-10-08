package hotels.util;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import hotels.Hotels;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.Notifications;
import org.controlsfx.glyphfont.FontAwesome;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author OLYJOSH
 */

public class Util
{
    private Hotels app;
    private static Navigator nav;
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public Util(Hotels app) {
        this.app =app;
        nav = new Navigator(getApp().getMain());
    }
    
    private static void formatDatePicker(DatePicker datePicker) {
        String pattern = "dd-MM-yyyy";
        String pattern2 = "yyyy-MM-dd";
        
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern),
                    dateFormatter2 = DateTimeFormatter.ofPattern(pattern2);
            

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter2.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

    }

    public static JSONArray getUserList(){
                
        try {
            JSONObject user = nav.fetchUsers();
            JSONArray userArray = user.getJSONArray("message");
            System.out.println("Printing User array : "+ userArray);
            
            return userArray;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static String random(){
        String rand = Long.toHexString(Double.doubleToLongBits(Math.random()));
        return rand;
    }
    
    public static void notify(String title, String msg, Pos pos){
         Notifications.create()
              .title(title)
              .text(msg)
              .graphic(GlyphsDude.createIcon(FontAwesomeIcons.INFO_CIRCLE, "40px"))
              .position(pos)
              .darkStyle()
              .show();
 
    }
    
    public static String [] splitter(String text, char regex){
        
        String [] test = text.split(String.valueOf(regex));
        return test;
    }
         
}
