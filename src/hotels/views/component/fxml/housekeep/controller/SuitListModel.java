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
public class SuitListModel {

    private final SimpleStringProperty id = new SimpleStringProperty("");
    private final SimpleStringProperty type = new SimpleStringProperty("");
    private final SimpleStringProperty guestName = new SimpleStringProperty("");
    private final SimpleStringProperty arrivalDate = new SimpleStringProperty("");
    private final SimpleStringProperty depDate = new SimpleStringProperty("");
    private final SimpleStringProperty maid = new SimpleStringProperty("");
    private final SimpleStringProperty pax = new SimpleStringProperty("");
    private final SimpleStringProperty foCom = new SimpleStringProperty("");
    private final SimpleStringProperty hkCom = new SimpleStringProperty("");
    private final SimpleStringProperty lastVisited = new SimpleStringProperty("");
    private final SimpleStringProperty lastMaids = new SimpleStringProperty("");
    private final SimpleStringProperty lastServiceRequest = new SimpleStringProperty("");
    private final SimpleStringProperty serviceStatus = new SimpleStringProperty("");
    private final SimpleStringProperty nextOn = new SimpleStringProperty("");
    private final SimpleStringProperty outOfOrderReason = new SimpleStringProperty("");
    private final SimpleStringProperty nextReservation = new SimpleStringProperty("");
    private final SimpleStringProperty guestRemarks = new SimpleStringProperty("");

    public SuitListModel() {

    }

    public String getEndDateC() {
        return depDate.get();
    }

    public String getId() {
        return id.get();
    }

    public String getDescC() {
        return type.get();
    }

    public String getSuiteC() {
        return guestName.get();
    }

    public String getDateC() {
        return arrivalDate.get();
    }

    public String getIntC() {
        return maid.get();
    }

    public String getRemC() {
        return pax.get();
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
            depDate.set(formattedTime);
        } catch (ParseException ex) {
            Logger.getLogger(SuitListModel.class.getName()).log(Level.SEVERE, null, ex);
        }
//        endDateC.set(x);
//        endDateC.set(x);
    }

    public void setDescC(String x) {
        type.set(x);
    }

    public void setSuiteC(String x) {
        guestName.set(x);
    }

    public void setDateC(String x) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("MMM dd, yyyy HH:mma");
            Date d = sdf.parse(x);
            String formattedTime = output.format(d);
            arrivalDate.set(formattedTime);
        } catch (ParseException ex) {
            Logger.getLogger(SuitListModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setIntC(String x) {
        maid.set(x);
    }

    public void setRemC(String x) {
        pax.set(x);
    }

}
