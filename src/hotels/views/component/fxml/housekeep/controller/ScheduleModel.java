/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hotels.views.component.fxml.housekeep.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Olyjosh
 */
    
public class ScheduleModel {
    private final SimpleStringProperty id = new SimpleStringProperty("");
   private final SimpleStringProperty descC = new SimpleStringProperty("");
   private final SimpleStringProperty suiteC = new SimpleStringProperty("");
   private final SimpleStringProperty dateC = new SimpleStringProperty("");
   private final SimpleStringProperty endDateC = new SimpleStringProperty("");
   private final SimpleStringProperty intC = new SimpleStringProperty("");
   private final SimpleStringProperty remC = new SimpleStringProperty("");

    public ScheduleModel() {

    }

      public String getEndDateC() {
        return endDateC.get();
    }

    public String getId() {
        return id.get();
    }
    
    public String getDescC() {
        return descC.get();
    }

    public String getSuiteC() {
        return suiteC.get();
    }

    public String getDateC() {
        return dateC.get();
    }

    public String getIntC() {
        return intC.get();
    }

    public String getRemC() {
        return remC.get();
    }
   
     public void setId(String x) {
        id.set(x);
        
    }
   
    public void setEndDateC(String x) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("MMM dd, yyyy HH:mma");
            Date d = sdf.parse(x);
            String formattedTime = output.format(d);
            endDateC.set(formattedTime);
        } catch (ParseException ex) {
            Logger.getLogger(ScheduleModel.class.getName()).log(Level.SEVERE, null, ex);
        }
//        endDateC.set(x);
    }
 
    
    public void setDescC(String x) {
        descC.set(x);
    }

    public void setSuiteC(String x) {
        suiteC.set(x);
    }

    public void setDateC(String x) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("MMM dd, yyyy HH:mma");
            Date d = sdf.parse(x);
            String formattedTime = output.format(d);
            dateC.set(formattedTime);
        } catch (ParseException ex) {
            Logger.getLogger(ScheduleModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setIntC(String x) {
        intC.set(x);
    }

    public void setRemC(String x) {
        remC.set(x);
    }
   
   
}

