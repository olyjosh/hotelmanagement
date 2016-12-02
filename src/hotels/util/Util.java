package hotels.util;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import hotels.Hotels;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.Notifications;

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
    
    public static void formatDatePicker(DatePicker datePicker) {
        String pattern = "yyyy-MM-dd";
        String pattern2 = "MMM dd, yyyy";
        
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern),
                    dateFormatter2 = DateTimeFormatter.ofPattern(pattern);
            

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
    
    
        
    public static void formatDatePicker2(DatePicker datePicker) {
        String pattern = "yyyy-MM-dd";
        String pattern2 = "MMM dd, yyyy";
        
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
                    return LocalDate.parse(string, dateFormatter2);
                } else {
                    return null;
                }
            }
        });

    }
    
    public static String formatDate(String date){
        String pattern = "MMM dd, yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateFormatter.format(LocalDate.parse(date.split("T")[0]));
    
    }

    
    
    private static HashMap<String, Image> imageCache = new HashMap<String, Image>();
    
    public static Image getImage(String imgId) {
        boolean backload = true; //background downloads
        Image image = imageCache.get(imgId);
        if (image == null) {
            System.out.println(Navigator2.getIMG_URL() + imgId);
            image = new Image(Navigator2.getIMG_URL() + imgId, backload);
            return image;
        }
        return null;
    }
//    
//    public static void notify(Stage stage, Pos pos, String title, String message, int h, int w ){
//        
//        Notification.Notifier.setPopupLocation(stage, pos);
//        Notification.Notifier.setWidth(w);
//        Notification.Notifier.setHeight(h);
//        Notification.Notifier.INSTANCE.notifySuccess(title, message);
//    }
//    
    public static void notify(String title, String msg, Pos pos){
        Text createIcon = GlyphsDude.createIcon(FontAwesomeIcons.INFO_CIRCLE, "35px");
        createIcon.setFill(Paint.valueOf("#ffffff"));
        Notifications.create()
             .title(title)
             .text(msg)
             .graphic(createIcon)
             .position(pos)
             .darkStyle()
             .show();
   }
    
    public static void notify_SUCCESS(String title, String msg, Pos pos) {
        Text createIcon = GlyphsDude.createIcon(FontAwesomeIcons.CHECK_CIRCLE, "35px");
        createIcon.setFill(Paint.valueOf("#00aa00"));
        Notifications.create()
                .title(title)
                .text(msg)
                .graphic(createIcon)
                .position(pos)
                .darkStyle()
                .show();
    }

    public static void notify_ERROR(String title, String msg, Pos pos) {
        Text createIcon = GlyphsDude.createIcon(FontAwesomeIcons.INFO_CIRCLE, "35px");
        createIcon.setFill(Paint.valueOf(State.COLOR_OUT_ORDER));   //"#aa0000"
        Notifications.create()
                .title(title)
                .text(msg)
                .graphic(createIcon)
                .position(pos)
                .darkStyle()
                .show();
    }
    
    
    public static void notify_NETWORK() {
        Text createIcon = GlyphsDude.createIcon(FontAwesomeIcons.SIGNAL, "35px");
        createIcon.setFill(Paint.valueOf(State.COLOR_OUT_ORDER));   //"#aa0000"
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                 Notifications.create()
                .title("Network")
                .text("This is network connection problem. Check your network configurations")
                .graphic(createIcon)
                .position(Pos.CENTER)
                .darkStyle()
                .show();
            }
        });
    }
    
    
    
    public static String random(){
        String rand = Long.toHexString(Double.doubleToLongBits(Math.random()));
        return rand;
    }
    
//    public static void notify(String title, String msg, Pos pos){
//         Notifications.create()
//              .title(title)
//              .text(msg)
//              .graphic(GlyphsDude.createIcon(FontAwesomeIcons.INFO_CIRCLE, "40px"))
//              .position(pos)
//              .darkStyle()
//              .show();
// 
//    }
    
    public static String [] splitter(String text, char regex){
        
        String [] test = text.split(String.valueOf(regex));
        return test;
    }
         
    public static String stripDate(String rawdate){
        String [] date = rawdate.split("T");
        return date[0];
    }
    
    public static MaskerPane mp(){
        MaskerPane mp = new MaskerPane();
        mp.setPrefHeight(100);
        mp.setPrefWidth(400);
        return mp;
    }
    
    
    
    

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//abcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String randomString( int len ){
       StringBuilder sb = new StringBuilder( len );
       for( int i = 0; i < len; i++ ) 
          sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
       return sb.toString();
    }

    
    private String normalizePhone(String x ){
        if(x.startsWith("+234") || x.startsWith("234")){
            int a = x.indexOf(4);
            x="0".concat(x.substring(a));
        }
        return x;
    }

}
