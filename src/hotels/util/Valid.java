package hotels.util;

import javafx.scene.control.TextField;

/**
 *
 * @author olyjosh
 */
public class Valid {

//    static boolean passwordValidator(String pa1, String pa2, Label out, ProgressBar p) {
//        if (pa1.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
//            p.setProgress(0.9);
//            p.setStyle("-fx-accent: green;");
//        } else if (pa1.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$")) {
//            p.setStyle("-fx-accent: yellow;");
//            p.setProgress(0.5);
//        } else {
//            p.setStyle("-fx-accent: red;");
//            p.setProgress(0.3);
//        }
//        if (pa1.equals(pa2)) {
//            return true;
//        } else {
//            out.setText("password does not match");
//            return false;
//        }
//
//    }
    
    public static boolean isEmail(String s) {
        return s.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
    }

    public static boolean isPhone(String s) {
        return s.matches("\\d{11}");
    }

    public static boolean isWord(String s) {
        return s.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
    }
    
    public static boolean isNumber(String s){
        return s.matches("/[0-9]+/");
    }
    
    public String errorStyle = "-fx-border-color: red ;";
    public String defaultFieldStyle;
    public boolean checkTextField(TextField ...e){
        boolean s=true;
        defaultFieldStyle=e[0].getStyle();
        for (int i = 0; i < e.length; i++) {
            TextField t = e[i];
            if (t.getText().trim().equals("")) {
                t.setStyle(errorStyle);s = false;
            }
        }
        return s;
    }
    
}
