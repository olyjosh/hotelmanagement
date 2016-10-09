package hotels.util;

import eu.hansolo.enzo.notification.Notification;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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

    public static void notify(Stage stage, Pos pos, String title, String message, int h, int w ){
        
        Notification.Notifier.setPopupLocation(stage, pos);
        Notification.Notifier.setWidth(w);
        Notification.Notifier.setHeight(h);
        Notification.Notifier.INSTANCE.notifySuccess(title, message);
    }
    
    public static String random(){
        String rand = Long.toHexString(Double.doubleToLongBits(Math.random()));
        return rand;
    }
}
