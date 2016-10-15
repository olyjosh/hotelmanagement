package hotels.util;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import eu.hansolo.enzo.notification.Notification;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.Notifications;

/**
 *
 * @author OLYJOSH
 */

public class Util
{
    
    public static void formatDatePicker(DatePicker datePicker) {
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
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

    }
    
    private static HashMap<String, Image> imageCache = new HashMap<String, Image>();
    public static Image getImage(String imgId) {
        boolean backload = true; //background downloads
        Image image = imageCache.get(imgId);
        if (image == null) {
            System.out.println(Navigator2.getIMG_URL() + imgId);
            image = new Image(Navigator2.getIMG_URL() + imgId, backload);

            imageCache.put(imgId, image);
        }
        return image;
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
    
    public static String random(){
        String rand = Long.toHexString(Double.doubleToLongBits(Math.random()));
        return rand;
    }
}
