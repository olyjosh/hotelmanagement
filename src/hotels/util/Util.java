package hotels.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

/**
 *
 * @author OLYJOSH
 */

public class Util
{
    
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


}
